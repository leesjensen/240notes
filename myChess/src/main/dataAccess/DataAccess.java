package dataAccess;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

/**
 * Represents all operations that may be performed on the database.
 */
public class DataAccess {

    private Database db;


    public DataAccess() throws DataAccessException {
        configureDatabase();
    }

    /**
     * Clears out all the data in the database.
     *
     * @throws DataAccessException for database or sql query violations (e.g. no error for not found).
     */
    public void clear() throws DataAccessException {
        executeCommand("DELETE FROM `authentication`");
        executeCommand("DELETE FROM `user`");
        executeCommand("DELETE FROM `game`");
    }

    /**
     * Persist a user
     *
     * @param user with both username and ID provided.
     * @throws DataAccessException for database or sql query violations.
     */
    public UserData writeUser(UserData user) throws DataAccessException {
        if (user.getUsername() != null) {
            var u = new UserData(user);
            var ID = executeUpdate("INSERT INTO `user` (username, password, email) VALUES (?, ?, ?)", u.getUsername(), u.getPassword(), u.getEmail());
            if (ID != 0) {
                u.setUserID(ID);
                return u;
            }
        }

        return null;
    }

    /**
     * Read a previously persisted user.
     *
     * @param user with either ID or username provided.
     * @return The requested @User
     * @throws DataAccessException for database or sql query violations (e.g. no error for not found).
     */
    public UserData readUser(UserData user) throws DataAccessException {
        var conn = db.getConnection(true);
        try (var preparedStatement = conn.prepareStatement("SELECT userID, username, password, email from `user` WHERE userID=? OR username=?")) {
            preparedStatement.setInt(1, user.getUserID());
            preparedStatement.setString(2, user.getUsername());
            try (var rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    var u = new UserData(rs.getInt("userID"));
                    u.setUsername(rs.getString("username"));
                    u.setPassword(rs.getString("password"));
                    u.setEmail(rs.getString("email"));

                    return u;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        } finally {
            db.closeConnection(true);
        }

        return null;
    }

    /**
     * Persist the authorization token. If a token already exists in the database it is overwritten.
     *
     * @param user
     * @return The @AuthToken for the user.
     * @throws DataAccessException for database or sql query violations.
     */
    public AuthToken writeAuth(UserData user) throws DataAccessException {
        var a = new AuthToken(user.getUserID());
        executeUpdate("INSERT INTO `authentication` (authToken, userID) VALUES (?, ?)", a.getAuthToken(), a.getUserID());

        return a;
    }

    /**
     * Read a previously persisted authorization token.
     *
     * @param authToken
     * @return The @AuthToken for the user or Null if it doesn't exist.
     * @throws DataAccessException for database or sql query violations (e.g. no error for not found).
     */
    public AuthToken readAuth(String authToken) throws DataAccessException {
        var conn = db.getConnection(true);
        try (var preparedStatement = conn.prepareStatement("SELECT authToken, userID from `authentication` WHERE authToken=?")) {
            preparedStatement.setString(1, authToken);
            try (var rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return new AuthToken(rs.getInt("userID"), rs.getString("authToken"));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        } finally {
            db.closeConnection(true);
        }

        return null;
    }

    /**
     * Clears out an authorization token.
     *
     * @param authToken
     * @throws DataAccessException for database or sql query violations (e.g. no error for not found).
     */
    public void deleteAuth(AuthToken authToken) throws DataAccessException {
        executeUpdate("DELETE from `authentication` WHERE authToken=?", authToken.getAuthToken());
    }

    /**
     * Creates a new game.
     *
     * @param game
     * @throws DataAccessException for database or sql query violations.
     */
    public GameData newGame(GameData game) throws DataAccessException {
        var g = new GameData(game);
        var ID = executeUpdate("INSERT INTO `game` (gameName, whitePlayerID, blackPlayerID, game) VALUES (?, ?, ?, ?)",
                g.getGameName(),
                g.getWhitePlayerID(),
                g.getBlackPlayerID(),
                g.getGame().toString());
        if (ID != 0) {
            return g.setGameID(ID);
        }

        return null;
    }

    /**
     * Update an existing game.
     *
     * @param game
     * @return the @Game if it was updated, and null if there was no game with that ID.
     * @throws DataAccessException for database or sql query violations.
     */
    public void updateGame(GameData game) throws DataAccessException {
        executeUpdate("UPDATE `game` set gameName=?, whitePlayerID=?, blackPlayerID=?, game=? WHERE gameID=?",
                game.getGameName(),
                game.getWhitePlayerID(),
                game.getBlackPlayerID(),
                game.getGame().toString(),
                game.getGameID());
    }

    /**
     * Read a previously persisted Game.
     *
     * @param gameID
     * @return The requested Game or null if not found.
     * @throws DataAccessException for database or sql query violations (e.g. no error for not found).
     */
    public GameData readGame(int gameID) throws DataAccessException {
        var conn = db.getConnection(true);
        try (var preparedStatement = conn.prepareStatement("SELECT gameName, whitePlayerID, blackPlayerID, game FROM `game` WHERE gameID=?")) {
            preparedStatement.setInt(1, gameID);
            try (var rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    var gs = rs.getString("game");
                    var game = new GameData();
                    game.setGameID(gameID);
                    game.setGameName(rs.getString("gameName"));
                    game.setWhitePlayerID(rs.getInt("whitePlayerID"));
                    game.setBlackPlayerID(rs.getInt("blackPlayerID"));
                    game.setGame(chess.Game.Create(gs));

                    return game;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        } finally {
            db.closeConnection(true);
        }

        return null;
    }

    /**
     * The complete list of games. Since we don't delete games this will be the full list unless the clear operation is called.
     *
     * @return the list of @Game objects
     * @throws DataAccessException for database or sql query violations.
     */
    public Collection<GameData> listGames() throws DataAccessException {
        var result = new ArrayList<GameData>();
        var conn = db.getConnection(true);
        try (var preparedStatement = conn.prepareStatement("SELECT gameID, gameName, whitePlayerID, blackPlayerID, game FROM `game`")) {
            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    var game = new GameData();
                    game.setGameID(rs.getInt("gameID"));
                    game.setGameName(rs.getString("gameName"));
                    game.setWhitePlayerID(rs.getInt("whitePlayerID"));
                    game.setBlackPlayerID(rs.getInt("blackPlayerID"));
                    game.setGame(chess.Game.Create(rs.getString("game")));

                    result.add(game);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        } finally {
            db.closeConnection(true);
        }

        return result;
    }


    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS `authentication` (
              `authToken` varchar(100) NOT NULL,
              `userID` int NOT NULL,
              PRIMARY KEY (`authToken`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """,
            """
            CREATE TABLE IF NOT EXISTS  `game` (
              `gameID` int NOT NULL AUTO_INCREMENT,
              `gameName` varchar(45) DEFAULT NULL,
              `whitePlayerID` int DEFAULT NULL,
              `blackPlayerID` int DEFAULT NULL,
              `game` longtext NOT NULL,
              PRIMARY KEY (`gameID`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """,
            """
            CREATE TABLE IF NOT EXISTS `user` (
              `userID` int NOT NULL AUTO_INCREMENT,
              `username` varchar(45) NOT NULL,
              `password` varchar(45) NOT NULL,
              `email` varchar(45) NOT NULL,
              PRIMARY KEY (`userID`),
              UNIQUE KEY `username_UNIQUE` (`username`)
            ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """};


    private void configureDatabase() throws DataAccessException {
        db = new Database();
        try {
            createDatabase();

            var conn = db.getConnection(true);
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(String.format("Unable to configure database: %s", e.getMessage()));
        } finally {
            db.closeConnection(true);
        }
    }

    private void createDatabase() throws SQLException, DataAccessException {
        var conn = db.getConnection(false);
        try (var createStmt = conn.createStatement()) {
            createStmt.execute("CREATE DATABASE IF NOT EXISTS `" + Database.DB_NAME + "`");
        } finally {
            db.closeConnection(true);
        }
    }


    private void executeCommand(String statement) throws DataAccessException {
        var conn = db.getConnection(true);
        try (var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(String.format("Failed to execute command: %s", e.getMessage()));
        } finally {
            db.closeConnection(true);
        }
    }

    private int executeUpdate(String statement, Object... params) throws DataAccessException {
        var conn = db.getConnection(true);
        try (var preparedStatement = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
            for (var i = 0; i < params.length; i++) {
                var param = params[i];
                if (param instanceof String s) {
                    preparedStatement.setString(i + 1, s);
                } else if (param instanceof Integer x) {
                    preparedStatement.setInt(i + 1, x);
                } else if (param == null) {
                    preparedStatement.setNull(i + 1, NULL);
                }
            }
            preparedStatement.executeUpdate();

            var rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

            return 0;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                return 0;
            }
            throw new DataAccessException(String.format("executeUpdate error: %s, %s", statement, e.getMessage()));
        } finally {
            db.closeConnection(true);
        }
    }

}
