package dataAccess;

import chess.GameImpl;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySqlDataAccess implements DataAccess {


    private Database db;


    public MySqlDataAccess() throws DataAccessException {
        configureDatabase();
    }

    public void clear() throws DataAccessException {
        executeCommand("DELETE FROM `authentication`");
        executeCommand("DELETE FROM `user`");
        executeCommand("DELETE FROM `game`");
    }

    public UserData writeUser(UserData user) throws DataAccessException {
        if (user.username() != null) {
            var u = new UserData(user.username(), user.password(), user.email());
            executeUpdate("INSERT INTO `user` (username, password, email) VALUES (?, ?, ?)", u.username(), u.password(), u.email());
            return user;
        }

        return null;
    }

    public UserData readUser(String username) throws DataAccessException {
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("SELECT password, email from `user` WHERE username=?")) {
            preparedStatement.setString(1, username);
            try (var rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    var password = rs.getString("password");
                    var email = rs.getString("email");
                    return new UserData(username, password, email);
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        } finally {
            db.returnConnection(conn);
        }

        return null;
    }

    public AuthData writeAuth(String username) throws DataAccessException {
        var a = new AuthData(AuthData.generateToken(), username);
        executeUpdate("INSERT INTO `authentication` (authToken, username) VALUES (?, ?)", a.authToken(), a.username());

        return a;
    }

    public AuthData readAuth(String authToken) throws DataAccessException {
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("SELECT username from `authentication` WHERE authToken=?")) {
            preparedStatement.setString(1, authToken);
            try (var rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return new AuthData(authToken, rs.getString("username"));
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        } finally {
            db.returnConnection(conn);
        }

        return null;
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        executeUpdate("DELETE from `authentication` WHERE authToken=?", authToken);
    }

    public GameData newGame(String gameName) throws DataAccessException {
        var game = new GameImpl();
        var state = GameData.State.UNDECIDED;
        var ID = executeUpdate("INSERT INTO `game` (gameName, whitePlayerName, blackPlayerName, game, state) VALUES (?, ?, ?, ?, ?)",
                gameName,
                null,
                null,
                game.toString(),
                state.toString());
        if (ID != 0) {
            return new GameData(ID, "", "", gameName, game, state);
        }

        return null;
    }

    public void updateGame(GameData gameData) throws DataAccessException {
        executeUpdate("UPDATE `game` set gameName=?, whitePlayerName=?, blackPlayerName=?, game=?, state=? WHERE gameID=?",
                gameData.gameName(),
                gameData.whiteUsername(),
                gameData.blackUsername(),
                gameData.game().toString(),
                gameData.state().toString(),
                gameData.gameID());
    }

    public GameData readGame(int gameID) throws DataAccessException {
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("SELECT gameID, gameName, whitePlayerName, blackPlayerName, game, state FROM `game` WHERE gameID=?")) {
            preparedStatement.setInt(1, gameID);
            try (var rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return readGameData(rs);
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        } finally {
            db.returnConnection(conn);
        }

        return null;
    }

    public Collection<GameData> listGames() throws DataAccessException {
        var result = new ArrayList<GameData>();
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("SELECT gameID, gameName, whitePlayerName, blackPlayerName, game, state FROM `game`")) {
            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    var gameData = readGameData(rs);
                    result.add(gameData);
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        } finally {
            db.returnConnection(conn);
        }

        return result;
    }


    private GameData readGameData(ResultSet rs) throws SQLException {
        var gs = rs.getString("game");
        var gameID = rs.getInt("gameID");
        var gameName = rs.getString("gameName");
        var whitePlayerName = rs.getString("whitePlayerName");
        var blackPlayerName = rs.getString("blackPlayerName");
        var game = chess.GameImpl.Create(gs);
        var state = GameData.State.valueOf(rs.getString("state"));

        return new GameData(gameID, whitePlayerName, blackPlayerName, gameName, game, state);
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS `authentication` (
              `authToken` varchar(100) NOT NULL,
              `username` varchar(100) NOT NULL,
              PRIMARY KEY (`authToken`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """,
            """
            CREATE TABLE IF NOT EXISTS  `game` (
              `gameID` int NOT NULL AUTO_INCREMENT,
              `gameName` varchar(45) DEFAULT NULL,
              `whitePlayerName` varchar(100) DEFAULT NULL,
              `blackPlayerName` varchar(100) DEFAULT NULL,
              `game` longtext NOT NULL,
              `state` varchar(45) DEFAULT NULL,
              PRIMARY KEY (`gameID`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """,
            """
            CREATE TABLE IF NOT EXISTS `user` (
              `username` varchar(45) NOT NULL,
              `password` varchar(45) NOT NULL,
              `email` varchar(45) NOT NULL,
              PRIMARY KEY (`username`),
              UNIQUE KEY `username_UNIQUE` (`username`)
            ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """};


    private void configureDatabase() throws DataAccessException {
        db = new Database();
        try {
            try (var conn = db.getConnection(null)) {
                createDatabase(conn);

                for (var statement : createStatements) {
                    try (var preparedStatement = conn.prepareStatement(statement)) {
                        preparedStatement.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(String.format("Unable to configure database: %s", e.getMessage()));
        }
    }

    private void createDatabase(Connection conn) throws SQLException {
        try (var createStmt = conn.createStatement()) {
            createStmt.execute("CREATE DATABASE IF NOT EXISTS `" + Database.DB_NAME + "`");
        }

        conn.setCatalog(Database.DB_NAME);
    }


    private void executeCommand(String statement) throws DataAccessException {
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(String.format("Failed to execute command: %s", e.getMessage()));
        } finally {
            db.returnConnection(conn);
        }
    }

    private int executeUpdate(String statement, Object... params) throws DataAccessException {
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
            for (var i = 0; i < params.length; i++) {
                var param = params[i];
                switch (param) {
                    case String s -> preparedStatement.setString(i + 1, s);
                    case Integer x -> preparedStatement.setInt(i + 1, x);
                    case null -> preparedStatement.setNull(i + 1, NULL);
                    default -> {
                    }
                }
            }
            preparedStatement.executeUpdate();

            var rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

            return 0;
        } catch (SQLException e) {
            throw new DataAccessException(String.format("executeUpdate error: %s, %s", statement, e.getMessage()));
        } finally {
            db.returnConnection(conn);
        }
    }
}
