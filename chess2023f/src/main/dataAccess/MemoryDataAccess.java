package dataAccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * In memory representation of @DataAccess.
 */
public class MemoryDataAccess implements DataAccess {

    final private Map<String, UserData> users = new HashMap<>();
    final private Map<String, AuthData> auths = new HashMap<>();
    final private Map<Integer, GameData> games = new HashMap<>();

    public MemoryDataAccess() {
    }

    public void clear() throws DataAccessException {
        users.clear();
    }

    public UserData writeUser(UserData user) throws DataAccessException {
        users.put(user.username(), user);
        return user;
    }

    public UserData readUser(String userName) throws DataAccessException {
        return users.get(userName);
    }

    public AuthData writeAuth(UserData user) throws DataAccessException {
        var auth = new AuthData(AuthData.generateToken(), user.username());
        auths.put(auth.authToken(), auth);
        return auth;
    }

    public AuthData readAuth(String authToken) throws DataAccessException {
        return auths.get(authToken);
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        auths.remove(authToken);
    }

    public GameData newGame(GameData game) throws DataAccessException {
        games.put(game.gameID(), game);
        return game;
    }

    public GameData updateGame(GameData game) throws DataAccessException {
        games.put(game.gameID(), game);
        return game;
    }

    public GameData readGame(int gameID) throws DataAccessException {
        return games.get(gameID);
    }

    public Collection<GameData> listGames() throws DataAccessException {
        return games.values();
    }
}

