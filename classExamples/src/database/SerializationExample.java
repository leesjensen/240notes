package database;

import com.google.gson.*;

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
 * then deserialize it when we read it back out of the database.
 */
public class SerializationExample {


    public static void main(String[] args) throws Exception {
        var db = new SerializationExample();
        db.configureDatabase();

        try (var conn = db.getConnection()) {
            conn.setCatalog("pet_store");

            var fluffy = new Pet("Fluffy", "bird", new String[]{});
            var puddles = new Pet("Puddles", "cat", new String[]{"Harry"});
            var harry = new Pet("Harry", "cat", new String[]{"Puddles"});

            db.insertPet(conn, fluffy);
            db.insertPet(conn, puddles);
            db.insertPet(conn, harry);

            for (var pet : db.listPets(conn)) {
                System.out.println(pet);
            }
        }
    }


    record Pet(String name, String type, String[] friends) {}

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
                    var friends = new Gson().fromJson(json, String[].class);

                    pets.add(new Pet(name, type, friends));
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
