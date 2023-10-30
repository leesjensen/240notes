package ui;

import dataAccess.MemoryDataAccess;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import server.Server;

class ServerFacadeTest {

    static ServerFacade facade;
    static Server server;

    @BeforeAll
    static void startServer() {
        server = new Server(new MemoryDataAccess());
        var port = server.run(0);
        facade = new ServerFacade(String.format("http://localhost:%d", port));
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @Test
    void register() throws Exception {
        var response = facade.register("joe", "password", "joe@email.com");

        assertTrue(response.authToken().length() > 10);
    }
}