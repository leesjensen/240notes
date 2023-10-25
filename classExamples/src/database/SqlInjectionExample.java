package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlInjectionExample {

    public static void main(String[] args) throws Exception {
        var db = new SqlInjectionExample();
        db.configureDatabase();

        try (var conn = db.getConnection()) {
            conn.setCatalog("pet_store");

            db.insertPet("chip");
            db.insertPet("fluffy");
            db.insertPet("spot");

            db.simplifiedInsertPet("joe");








            db.simplifiedInsertPet("chomper'); DROP TABLE pet; -- ");
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


    void insertPet(String name) throws SQLException {
        var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pet_store", "root", "monkeypie");

        if (name.matches("[a-zA-Z]+")) {
            var statement = "INSERT INTO pet (name) VALUES(?)";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1, name);
                preparedStatement.executeUpdate();
            }
        }
    }


    void simplifiedInsertPet(String name) throws SQLException {
        var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pet_store?allowMultiQueries=true", "root", "monkeypie");

        var statement = "INSERT INTO pet (name) VALUES('" + name + "')";
        System.out.println(statement);
        try (var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        }
    }
}
