package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class DatabaseExample {

    public static void main(String[] args) throws Exception {
        var db = new DatabaseExample();
        db.configureDatabase();

        try (var conn = db.getConnection()) {
            conn.setCatalog("pet_store");

            db.insertPet(conn, "Fluffy", "bird");
            db.insertPet(conn, "Puddles", "cat");
            db.insertPet(conn, "Harry", "cat");
            int spotID = db.insertPet(conn, "Spot", "dog");

            db.updatePet(conn, spotID, "Flaky");
            db.deletePet(conn, spotID);

            db.queryPets(conn, "cat");

            db.sqlInjectionFoiled("joe");
            db.sqlInjectionFoiled("joe'); DROP TABLE pet; -- ");

            db.sqlInjection("joe");
            db.sqlInjection("joe'); DROP TABLE pet; -- ");
        }
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
                        PRIMARY KEY (id)
                    )""";


            try (var createTableStatement = conn.prepareStatement(createPetTable)) {
                createTableStatement.executeUpdate();
            }
        }
    }


    int insertPet(Connection conn, String name, String type) throws SQLException {
        try (var preparedStatement = conn.prepareStatement("INSERT INTO pet (name, type) VALUES(?, ?)", RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, type);

            preparedStatement.executeUpdate();

            var resultSet = preparedStatement.getGeneratedKeys();
            var ID = 0;
            if (resultSet.next()) {
                ID = resultSet.getInt(1);
            }

            return ID;
        }
    }

    void updatePet(Connection conn, int petID, String name) throws SQLException {
        try (var preparedStatement = conn.prepareStatement("UPDATE pet SET name=? WHERE id=?")) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, petID);

            preparedStatement.executeUpdate();
        }
    }

    void queryPets(Connection conn, String findType) throws SQLException {
        try (var preparedStatement = conn.prepareStatement("SELECT id, name, type FROM pet WHERE type=?")) {
            preparedStatement.setString(1, findType);
            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    var id = rs.getInt("id");
                    var name = rs.getString("name");
                    var type = rs.getString("type");

                    System.out.printf("id: %d, name: %s, type: %s%n", id, name, type);
                }
            }
        }
    }

    void deletePet(Connection conn, int petID) throws SQLException {
        try (var preparedStatement = conn.prepareStatement("DELETE FROM pet WHERE id=?")) {
            preparedStatement.setInt(1, petID);
            preparedStatement.executeUpdate();
        }
    }

    void sqlInjection(String name) throws SQLException {
        var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pet_store?allowMultiQueries=true", "root", "monkeypie");

        var statement = "INSERT INTO pet (name) VALUES('" + name + "')";
        System.out.println(statement);
        try (var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        }
    }


    void sqlInjectionFoiled(String name) throws SQLException {
        var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pet_store", "root", "monkeypie");

        if (name.matches("[a-zA-Z]+")) {
            var statement = "INSERT INTO pet (name) VALUES(?)";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1, name);
                preparedStatement.executeUpdate();
            }
        }
    }
}
