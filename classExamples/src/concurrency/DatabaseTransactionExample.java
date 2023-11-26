package concurrency;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class DatabaseTransactionExample {

    public static void main(String[] args) throws Exception {
        var username = args[0];
        var email = args[1];
        configureDatabase();

        try (var conn = getConnection()) {
            conn.setAutoCommit(false);

            try {
                if (!userExists(conn, username)) {
                    insertUser(conn, username, email);
                    conn.commit();
                }
            } catch (SQLException e) {
                conn.rollback();
            }
        }
    }

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "monkeypie");
    }

    static void insertUser(Connection conn, String username, String email) throws SQLException {
        try (var preparedStatement = conn.prepareStatement("INSERT INTO pet_store.user (username, email) VALUES(?, ?)")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
        }
    }

    static boolean userExists(Connection conn, String username) throws SQLException {
        try (var preparedStatement = conn.prepareStatement("SELECT username FROM pet_store.user WHERE username=?")) {
            preparedStatement.setString(1, username);
            try (var rs = preparedStatement.executeQuery()) {
                return rs.next();
            }
        }
    }

    static void configureDatabase() throws SQLException {
        try (var conn = getConnection()) {
            try (var createDbStatement = conn.prepareStatement(
                    "CREATE DATABASE IF NOT EXISTS pet_store")) {
                createDbStatement.executeUpdate();
            }
            try (var createTableStatement = conn.prepareStatement("""
                    CREATE TABLE  IF NOT EXISTS pet_store.user (
                        username VARCHAR(255) DEFAULT NULL,
                        email VARCHAR(255) DEFAULT NULL
                    )""")) {
                createTableStatement.executeUpdate();
            }
        }
    }
}
