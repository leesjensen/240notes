package dataAccess;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Class responsible for creating connections to the database
 */
public class Database {

    public static final String DB_NAME = "leeChess";

    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "monkeypie";

    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306";

    private final LinkedList<Connection> connections = new LinkedList<>();

    synchronized public Connection getConnection() throws DataAccessException {
        try {
            Connection connection;
            if (connections.isEmpty()) {
                connection = DriverManager.getConnection(CONNECTION_URL, DB_USERNAME, DB_PASSWORD);
                connection.setCatalog(DB_NAME);
            } else {
                connection = connections.removeFirst();
            }
            return connection;
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    synchronized public void returnConnection(Connection connection) {
        connections.add(connection);
    }

    /**
     * Returns a connection that is not associated with the pool and doesn't have a catalog set.
     * You must close the connection when done.
     */
    public Connection openConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_URL, DB_USERNAME, DB_PASSWORD);
    }
}

