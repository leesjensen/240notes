package database;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This demonstrates how you can serialize data into and out of a field in a database.
 * <p>
 * The example uses a pet that can have a friend list. The friend list can be variable length, and so we can't just
 * stick it into a database field. Instead, we serialize it to JSON and put it in a database field and
 * then deserialize it when we read it back out of the field.
 * <p>
 * This also demonstrates how do deserialize an object that has interface properties into the proper
 * concrete implementation of the interface.
 */
public class AdapterSerializationExample {


    public static void main(String[] args) throws Exception {
        var db = new AdapterSerializationExample();
        db.configureDatabase();

        try (var conn = db.getConnection()) {
            conn.setCatalog("pet_store");

            var fluffy = new Pet(0, "Fluffy", "bird", new PetFriendArrayList());
            var puddles = new Pet(0, "Puddles", "cat", new PetFriendArrayList());
            var harry = new Pet(0, "Harry", "cat", new PetFriendArrayList());

            puddles.friends.add(harry.name);
            harry.friends.add(fluffy.name);

            db.insertPet(conn, fluffy);
            db.insertPet(conn, puddles);
            db.insertPet(conn, harry);

            for (var pet : db.queryPets(conn)) {
                System.out.println(pet);
            }
        }
    }

    interface PetFriendList {
        void add(String friend);
    }

    static class PetFriendArrayList implements PetFriendList {
        ArrayList<String> list = new ArrayList<>();

        public void add(String friend) {
            list.add(friend);
        }

        /**
         * Serialization of the list just uses the basic Gson functionality
         * to create an array.
         * <p>
         * Deserialization also works because there are no interface properties.
         */
        public String toString() {
            return new Gson().toJson(this);
        }
    }


    /**
     * Because the friend is represented by the interface, Gson doesn't know
     * how to deserialize it back into a concrete implementation of the interface.
     * We get around that by introducing an adapter that provides the concrete
     * class when the interface is observed during the deserialization.
     */
    record Pet(int id, String name, String type, PetFriendList friends) {

        /**
         * Serializing the object works great because you have a concrete object
         * that you can generate the Gson from.
         */
        public String toString() {
            return new Gson().toJson(this);
        }

        /**
         * BAD - Simple deserialization
         * This doesn't work because Gson doesn't know how to deserialize the PetFriendList
         * interface into a concrete class.
         */
        static Pet fromJson(int id, String name, String type, String friendJson) {
            var friends = new Gson().fromJson(friendJson, PetFriendList.class);

            return new Pet(id, name, type, friends);
        }

        /**
         * GOOD - Deserialization with adapter
         * This works because we tell Gson to create a PetFriendArrayList whenever
         * it sees a PetFriendList.
         */
        static Pet fromJsonWithAdapter(int id, String name, String type, String friendJson) {
            var builder = new GsonBuilder();

            // Register with Gson what class to use when the interface is provided on deserialization.
            builder.registerTypeAdapter(PetFriendList.class, new PetFriendDeserializer());

            // You can also use a lambda function for the adapter if your deserialization is simple.
            // builder.registerTypeAdapter(PetFriendList.class,
            //   (JsonDeserializer<PetFriendList>) (el, t, ctx) -> new Gson().fromJson(el, PetFriendArrayList.class));

            var friends = builder.create().fromJson(friendJson, PetFriendList.class);

            return new Pet(id, name, type, friends);
        }

        /**
         * Create a deserializer that can take request for the interface and return
         * a concrete object.
         */
        static class PetFriendDeserializer implements JsonDeserializer<PetFriendList> {
            public PetFriendList deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) {
                return new Gson().fromJson(jsonElement, PetFriendArrayList.class);
            }
        }
    }


    void insertPet(Connection conn, Pet pet) throws SQLException {
        try (var preparedStatement = conn.prepareStatement("INSERT INTO pet (name, type, friends) VALUES(?, ?, ?)")) {
            preparedStatement.setString(1, pet.name);
            preparedStatement.setString(2, pet.type);

            // We store a serialized object in the database as a string
            preparedStatement.setString(3, pet.friends.toString());

            preparedStatement.executeUpdate();
        }
    }

    Collection<Pet> queryPets(Connection conn) throws SQLException {
        var pets = new ArrayList<Pet>();
        try (var preparedStatement = conn.prepareStatement("SELECT id, name, type, friends FROM pet")) {
            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    var id = rs.getInt("id");
                    var name = rs.getString("name");
                    var type = rs.getString("type");

                    // We read the serialized string out of the database
                    var friendsText = rs.getString("friends");

                    // Now we need to recreate the pet from the serialization and database fields.

                    // This will fail because Gson doesn't know how to handle the deserialization of an interface.
                    // pets.add(Pet.fromJson(id, name, type, friendsText));

                    // This works because it creates an adapter that knows how to create a concrete class when an interface is requests.
                    pets.add(Pet.fromJsonWithAdapter(id, name, type, friendsText));
                }
            }
        }
        return pets;
    }


    Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "monkeypie");
    }

    void configureDatabase() throws SQLException {
        try (var conn = getConnection()) {
            var createDbStatement = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS pet_store");
            createDbStatement.executeUpdate();

            conn.setCatalog("pet_store");

            var createPetTable = """
                    CREATE TABLE  IF NOT EXISTS pet (
                        id INT NOT NULL AUTO_INCREMENT,
                        name VARCHAR(255) DEFAULT NULL,
                        type VARCHAR(255) DEFAULT NULL,
                        friends longtext NOT NULL,                        
                        PRIMARY KEY (id)
                    )""";


            try (var createTableStatement = conn.prepareStatement(createPetTable)) {
                createTableStatement.executeUpdate();
            }
        }
    }

}
