import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import service.ListGamesResponse;
import ui.ChessClient;

import static org.junit.jupiter.api.Assertions.*;

public class clientTests {

    private ChessClient client = new ChessClient();

    @BeforeEach
    public void setup() throws Exception {
        client.clear();
    }

    @Test
    public void badCmdTest() {
        assertThrows(NoSuchMethodException.class, () -> client.eval("garbage"));
    }

    static final String loggedOutHelp = "  \u001B[38;5;12mregister <USERNAME> <PASSWORD> <EMAIL>\u001B[38;5;0m - \u001B[38;5;5mto create an account\u001B[38;5;0m\n" +
            "  \u001B[38;5;12mlogin <USERNAME> <PASSWORD>\u001B[38;5;0m - \u001B[38;5;5mto play chess\u001B[38;5;0m\n" +
            "  \u001B[38;5;12mquit\u001B[38;5;0m - \u001B[38;5;5mplaying chess\u001B[38;5;0m\n" +
            "  \u001B[38;5;12mhelp\u001B[38;5;0m - \u001B[38;5;5mwith possible commands\u001B[38;5;0m\n";

    static final String loggedInHelp = "  \u001B[38;5;12mcreate <NAME>\u001B[38;5;0m - \u001B[38;5;5ma game\u001B[38;5;0m\n" +
            "  \u001B[38;5;12mlist\u001B[38;5;0m - \u001B[38;5;5mgames\u001B[38;5;0m\n" +
            "  \u001B[38;5;12mjoin <ID>\u001B[38;5;0m - \u001B[38;5;5ma game\u001B[38;5;0m\n" +
            "  \u001B[38;5;12mobserve <ID>\u001B[38;5;0m - \u001B[38;5;5ma game\u001B[38;5;0m\n" +
            "  \u001B[38;5;12mlogout\u001B[38;5;0m - \u001B[38;5;5mwhen you are done\u001B[38;5;0m\n" +
            "  \u001B[38;5;12mquit\u001B[38;5;0m - \u001B[38;5;5mplaying chess\u001B[38;5;0m\n" +
            "  \u001B[38;5;12mhelp\u001B[38;5;0m - \u001B[38;5;5mwith possible commands\u001B[38;5;0m\n";

    @Test
    public void helpTest() throws Exception {
        assertEquals(loggedOutHelp, client.eval("HELP"));
        client.eval("register joe jackson joe@mail.com");
        assertEquals(loggedInHelp, client.eval("HELP"));
        client.eval("logout");
        assertEquals(loggedOutHelp, client.eval("HELP"));
    }

    @Test
    public void loginTest() throws Exception {
        assertEquals("Success", client.eval("register joe password c@mail.com"));
        assertEquals("Success", client.eval("login joe password"));
    }

    @Test
    public void gameTest() throws Exception {
        assertEquals("Success", client.eval("register joe password c@mail.com"));

        assertEquals("Success", client.eval("create game1"));
        assertEquals("Success", client.eval("create game2"));

        var gameList = listGames();
        assertTrue(gameList.success);
        assertEquals(2, gameList.games.length);

        if (gameList.games.length == 2) {
            var gameID = gameList.games[0].gameID;

            assertEquals("Success", client.eval(String.format("join %s white", gameID)));
            // Can't join if already joined
            assertEquals("Failure", client.eval(String.format("join %s white", gameID)));

            client.eval("logout");
            assertEquals("Success", client.eval("login joe password"));
            assertEquals("Success", client.eval(String.format("observe %s", gameID)));
            // Can't join if observing
            assertEquals("Failure", client.eval(String.format("join %s BLACK", gameID)));

            client.eval("logout");
            assertEquals("Success", client.eval("login joe password"));
            assertEquals("Success", client.eval(String.format("join %s BLACK", gameID)));

            gameList = listGames();
            assertEquals("joe", gameList.games[0].blackUsername);
            assertEquals("joe", gameList.games[0].whiteUsername);
        }
    }

    private ListGamesResponse listGames() throws Exception {
        var gameListText = client.eval("list");
        return new Gson().fromJson(gameListText, ListGamesResponse.class);
    }
}

