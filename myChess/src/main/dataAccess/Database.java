package dataAccess;

import model.*;

import java.util.Collection;

/**
 * Represents all operations that may be performed on the database.
 */
public class Database {

    /**
     * Clears out all the data in the database.
     *
     * @throws DataAccessException for database or sql query violations (e.g. no error for not found).
     */
    public void clear() throws DataAccessException {
        throw new UnsupportedOperationException("todo");
    }

    /**
     * Persist a user
     *
     * @param user
     * @throws DataAccessException for database or sql query violations (e.g. no error for not found).
     */
    public void writeUser(User user) throws DataAccessException {
        throw new UnsupportedOperationException("todo");
    }

    /**
     * Read a previously persisted user
     *
     * @param userId
     * @return The requested User
     * @throws DataAccessException for database or sql query violations (e.g. no error for not found).
     */
    User readUser(String userId) throws DataAccessException {
        throw new UnsupportedOperationException("todo");
    }

    /**
     * Persist the authorization token. If a token already exists in the database it is overwritten.
     *
     * @param token
     * @throws DataAccessException for database or sql query violations (e.g. no error for not found).
     */
    public void writeAuth(AuthToken token) throws DataAccessException {
        throw new UnsupportedOperationException("todo");
    }

    /**
     * Read a previously persisted authorization token.
     *
     * @param token
     * @return The authorization token for the user or Null if it doesn't exist.
     * @throws DataAccessException for database or sql query violations (e.g. no error for not found).
     */
    AuthToken readAuth(String token) throws DataAccessException {
        throw new UnsupportedOperationException("todo");
    }

    /**
     * Persists a game.
     *
     * @param game
     * @throws DataAccessException for database or sql query violations (e.g. no error for not found).
     */
    public void writeGame(Game game) throws DataAccessException {
        throw new UnsupportedOperationException("todo");
    }

    /**
     * Read a previously persisted Game.
     *
     * @param gameId
     * @return The requested Game.
     * @throws DataAccessException for database or sql query violations (e.g. no error for not found).
     */
    Game readGame(String gameId) throws DataAccessException {
        throw new UnsupportedOperationException("todo");
    }

    /**
     * The complete list of games. Since we don't delete games this will be the full list unless the clear operation is called.
     *
     * @return the list of games
     * @throws DataAccessException for database or sql query violations (e.g. no error for not found).
     */
    Collection<Game> listGames() throws DataAccessException {
        throw new UnsupportedOperationException("todo");
    }
}
