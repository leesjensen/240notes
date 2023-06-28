package dataAccess;

import model.*;

import java.util.Collection;
import java.util.HashMap;

/**
 * Represents all operations that may be performed on the database.
 */
public class Database {

    private HashMap<String, User> usersByName = new HashMap<>();
    private HashMap<Integer, User> usersByID = new HashMap<>();
    private HashMap<Integer, AuthToken> authTokensByUserID = new HashMap<>();
    private HashMap<String, AuthToken> authTokensByToken = new HashMap<>();
    private HashMap<Integer, Game> games = new HashMap<>();

    /**
     * Clears out all the data in the database.
     *
     * @throws DataAccessException for database or sql query violations (e.g. no error for not found).
     */
    public void clear() throws DataAccessException {
        usersByID.clear();
        usersByName.clear();
        authTokensByUserID.clear();
        authTokensByToken.clear();
        games.clear();
    }

    /**
     * Persist a user
     *
     * @param user with both username and ID provided.
     * @throws DataAccessException for database or sql query violations.
     */
    public User writeUser(User user) throws DataAccessException {
        if (user.getUsername() != null && !usersByName.containsKey(user.getUsername())) {
            var newUser = new User(user);
            usersByName.put(newUser.getUsername(), user);
            usersByID.put(newUser.getUserID(), user);
            return newUser;
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
    public User readUser(User user) throws DataAccessException {
        if (user.getUserID() != 0) {
            return usersByID.get(user.getUserID());
        }
        if (user.getUsername() != null) {
            return usersByName.get(user.getUsername());
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
    public AuthToken writeAuth(User user) throws DataAccessException {
        var authToken = new AuthToken(user.getUserID());
        authTokensByUserID.put(user.getUserID(), authToken);
        authTokensByToken.put(authToken.getAuthToken(), authToken);
        return authToken;
    }

    /**
     * Read a previously persisted authorization token.
     *
     * @param token
     * @return The @AuthToken for the user or Null if it doesn't exist.
     * @throws DataAccessException for database or sql query violations (e.g. no error for not found).
     */
    public AuthToken readAuth(String token) throws DataAccessException {
        return authTokensByToken.get(token);
    }

    /**
     * Clears out an authorization token.
     *
     * @param authToken
     * @throws DataAccessException for database or sql query violations (e.g. no error for not found).
     */
    public void deleteAuth(AuthToken authToken) throws DataAccessException {
        authTokensByToken.remove(authToken.getAuthToken());
        authTokensByUserID.remove(authToken.getUserID());
    }

    /**
     * Creates a new game.
     *
     * @param game
     * @throws DataAccessException for database or sql query violations.
     */
    public Game newGame(Game game) throws DataAccessException {
        var newGame = new Game(game);
        games.put(newGame.getGameID(), newGame);
        return newGame;
    }

    /**
     * Update an existing game.
     *
     * @param game
     * @return the @Game if it was updated, and null if there was no game with that ID.
     * @throws DataAccessException for database or sql query violations.
     */
    public Game updateGame(Game game) throws DataAccessException {
        return games.replace(game.getGameID(), game);
    }

    /**
     * Read a previously persisted Game.
     *
     * @param gameID
     * @return The requested Game or null if not found.
     * @throws DataAccessException for database or sql query violations (e.g. no error for not found).
     */
    public Game readGame(int gameID) throws DataAccessException {
        return games.get(gameID);
    }

    /**
     * The complete list of games. Since we don't delete games this will be the full list unless the clear operation is called.
     *
     * @return the list of @Game objects
     * @throws DataAccessException for database or sql query violations (e.g. no error for not found).
     */
    public Collection<Game> listGames() throws DataAccessException {
        return games.values();
    }
}
