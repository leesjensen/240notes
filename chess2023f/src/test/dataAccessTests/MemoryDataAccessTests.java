package dataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.MemoryDataAccess;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class MemoryDataAccessTests {

    @Test
    public void writeReadUser() throws DataAccessException {
        var db = new MemoryDataAccess();
        var user = new UserData("juan", "too many secrets", "juan@byu.edu");

        Assertions.assertEquals(user, db.writeUser(user));
        Assertions.assertEquals(user, db.readUser(user.username()));
    }

    @Test
    public void writeReadAuth() {
        var db = new MemoryDataAccess();
        var user = new UserData("juan", "too many secrets", "juan@byu.edu");

        var authData = db.writeAuth(user.username());
        Assertions.assertEquals(user.username(), authData.username());
        Assertions.assertFalse(authData.authToken().isEmpty());

        var returnedAuthData = db.readAuth(authData.authToken());
        Assertions.assertEquals(user.username(), returnedAuthData.username());
        Assertions.assertEquals(authData.authToken(), returnedAuthData.authToken());

        var secondAuthData = db.writeAuth(user.username());
        Assertions.assertEquals(user.username(), secondAuthData.username());
        Assertions.assertNotEquals(authData.authToken(), secondAuthData.authToken());
    }


    @Test
    public void writeReadGame() {
        var db = new MemoryDataAccess();

        var game = db.newGame("blitz");
        var updatedGame = game.setBlack("joe");
        db.updateGame(updatedGame);

        var retrievedGame = db.readGame(game.gameID());
        Assertions.assertEquals(retrievedGame, updatedGame);
    }


    @Test
    public void listGame() {
        var db = new MemoryDataAccess();

        var games = List.of(db.newGame("blitz"), db.newGame("fisher"), db.newGame("lightning"));
        var returnedGames = db.listGames();
        Assertions.assertIterableEquals(games, returnedGames);
    }
}
