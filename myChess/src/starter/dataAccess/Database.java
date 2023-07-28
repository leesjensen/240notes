package dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Class responsible for creating connections to the database. Connections are
 * managed with a simple pool in order to increase performance. To obtain and
 * use connections represented by this class use the following pattern.
 *
 * <pre>
 *  public boolean example(String selectStatement, Database db) throws DataAccessException{
 *    var conn = db.getConnection();
 *    try (var preparedStatement = conn.prepareStatement(selectStatement)) {
 *        return preparedStatement.execute();
 *    } catch (SQLException ex) {
 *        throw new DataAccessException(ex.toString());
 *    } finally {
 *        db.returnConnection(conn);
 *    }
 *  }
 * </pre>
 */
public class Database {

    // FIXME: Change these fields, if necessary, to match your database configuration
    public static final String DB_NAME = "leeChess";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "monkeypie";

    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306";

    private final LinkedList<Connection> connections = new LinkedList<>();

    /**
     * Get a connection to the database. This pulls a connection out of a simple
     * pool implementation. The connection must be returned to the pool after
     * you are done with it by calling {@link #returnConnection(Connection) returnConnection}.
     *
     * @return Connection
     */
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

    /**
     * Return a connection to the pool. Only connections obtained by calling
     *
     * @param connection previous obtained by calling {@link #getConnection() getConnection}.
     */
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

