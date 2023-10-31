package ui;

import chess.ChessGame;
import dataAccess.MemoryDataAccess;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import server.Server;
import util.ResponseException;

class ServerFacadeTest {

    static ServerFacade facade;
    static Server server;

    @BeforeAll
    static void startServer() {
        server = new Server(new MemoryDataAccess());
        var port = server.run(0);
        facade = new ServerFacade(String.format("http://localhost:%d", port));
    }

    @BeforeEach
    void clearDatabase() throws Exception {
        facade.clear();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @Test
    void register() throws Exception {
        var authData = facade.register("joe", "password", "joe@email.com");

        assertTrue(authData.authToken().length() > 10);
    }


    @Test
    void logoutLogin() throws Exception {
        var registerAuthData = facade.register("joe", "password", "joe@email.com");

        facade.logout(registerAuthData.authToken());
        assertThrows(ResponseException.class, () -> facade.listGames(registerAuthData.authToken()));

        var loginAuthData = facade.login("joe", "password");
        facade.listGames(loginAuthData.authToken());
    }


    @Test
    void listGames() throws Exception {
        var authData = facade.register("joe", "password", "joe@email.com");

        var games = facade.listGames(authData.authToken());
        assertEquals(0, games.length);

        facade.createGame(authData.authToken(), "blitz");

        games = facade.listGames(authData.authToken());
        assertEquals(1, games.length);
        assertEquals("blitz", games[0].gameName());
    }

    @Test
    void joinGames() throws Exception {
        var authData = facade.register("joe", "password", "joe@email.com");

        var game = facade.createGame(authData.authToken(), "blitz");
        facade.joinGame(authData.authToken(), game.gameID(), ChessGame.TeamColor.WHITE);

        var games = facade.listGames(authData.authToken());
        assertEquals(1, games.length);
        assertEquals("blitz", games[0].gameName());
        assertEquals(game.gameID(), games[0].gameID());
        assertEquals(authData.username(), games[0].whiteUsername());
    }

}