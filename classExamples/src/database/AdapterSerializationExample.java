package database;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdapterSerializationExample {

    public static void main(String[] args) throws Exception {
        var db = new AdapterSerializationExample();
        db.configureDatabase();

        try (var conn = db.getConnection()) {
            conn.setCatalog("pet_store");

            var puddlesFriends = new ArrayFriendList().add("Harry");
            var harryFriends = new ArrayFriendList().add("Pudddles").add("Fluffy");

            var fluffy = new Pet("Fluffy", "bird", null);
            var puddles = new Pet("Puddles", "cat", puddlesFriends);
            var harry = new Pet("Harry", "cat", harryFriends);

            db.insertPet(conn, fluffy);
            db.insertPet(conn, puddles);
            db.insertPet(conn, harry);

            for (var pet : db.listPets(conn)) {
                System.out.println(pet);
            }
        }
    }

    interface FriendList {
        FriendList add(String friend);
    }

    static class ArrayFriendList implements FriendList {
        private ArrayList<String> list = new ArrayList<>();

        public FriendList add(String friend) {
            list.add(friend);
            return this;
        }
    }

    record Pet(String name, String type, FriendList friends) {
        public String toString() {
            return new Gson().toJson(this);
        }
    }

    void insertPet(Connection conn, Pet pet) throws SQLException {
        try (var preparedStatement = conn.prepareStatement("INSERT INTO pet (name, type, friends) VALUES(?, ?, ?)")) {
            preparedStatement.setString(1, pet.name);
            preparedStatement.setString(2, pet.type);

            // Serialize and store the friend JSON.
            var json = new Gson().toJson(pet.friends);
            preparedStatement.setString(3, json);

            preparedStatement.executeUpdate();
        }
    }

    Collection<Pet> listPets(Connection conn) throws SQLException {
        var pets = new ArrayList<Pet>();
        try (var preparedStatement = conn.prepareStatement("SELECT name, type, friends FROM pet")) {
            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    var name = rs.getString("name");
                    var type = rs.getString("type");

                    // Read and deserialize the friend JSON.
                    var json = rs.getString("friends");

//                    var friends = new Gson().fromJson(json, FriendList.class);

                    var builder = new GsonBuilder();
                    builder.registerTypeAdapter(FriendList.class, new ListAdapter());
                    var friends = builder.create().fromJson(json, FriendList.class);

                    pets.add(new Pet(name, type, friends));
                }
            }
        }
        return pets;
    }

    static class ListAdapter implements JsonDeserializer<FriendList> {
        public FriendList deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
            return new Gson().fromJson(el, ArrayFriendList.class);
        }
    }

    Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306", "admin", "monkeypie");
    }

    void configureDatabase() throws SQLException {
        try (var conn = getConnection()) {
            var createDbStatement = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS pet_store");
            createDbStatement.executeUpdate();

            conn.setCatalog("pet_store");

            var createPetTable = """
                    CREATE TABLE  IF NOT EXISTS pet (
                        name VARCHAR(255) DEFAULT NULL,
                        type VARCHAR(255) DEFAULT NULL,
                        friends text NOT NULL
                    )""";


            try (var createTableStatement = conn.prepareStatement(createPetTable)) {
                createTableStatement.executeUpdate();
            }
        }
    }

}
