package database;

import org.eclipse.jetty.util.StringUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class DatabaseExample {

    public static void main(String[] args) throws Exception {
        var db = new DatabaseExample();
        db.configureDatabase();

        try (var conn = db.getConnection()) {
            conn.setCatalog("pet_store");

            db.insertPet(conn, "Fluffy", "bird");
            db.insertPet(conn, "Puddles", "cat");
            db.insertPet(conn, "Spot", "dog");

            db.sqlInjection(conn, "; DROP TABLE users --", "type=%27");

//            db.sqlInjection(conn, "joe','die');SELECT CONCAT('stinky", "tuna");

//            db.sqlInjection(conn, "joe','cat');INSERT INTO pet (name, type) VALUES('die','frog');INSERT INTO pet (name, type) VALUES('sally", "tuna");
//            db.sqlInjection(conn, "joe','cat');DROP TABLE pet;INSERT INTO pet (name, type) VALUES('sally", "tuna");

            db.queryPets(conn);
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
                        name VARCHAR(255) NOT NULL,
                        type VARCHAR(255) NOT NULL,
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

    void queryPets(Connection conn) throws SQLException {
        try (var preparedStatement = conn.prepareStatement("SELECT id, name, type from pet")) {
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

    void sqlInjection(Connection conn, String name, String type) throws SQLException {
        name = "x', 'y'); DROP TABLE pet; --";
        var statement = "INSERT INTO pet (name, type) VALUES('" + name + "','" + type + "')";
        System.out.println(statement);
        try (var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        }
    }

}
