import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import service.GameListResponse;
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

    @Test
    public void helpTest() throws Exception {
        assertEquals(
                "  \u001B[38;5;12mregister <USERNAME> <PASSWORD> <EMAIL>\u001B[38;5;0m - \u001B[38;5;5mto create an account\u001B[38;5;0m\n" +
                        "  \u001B[38;5;12mlogin <USERNAME> <PASSWORD>\u001B[38;5;0m - \u001B[38;5;5mto play chess\u001B[38;5;0m\n" +
                        "  \u001B[38;5;12mquit\u001B[38;5;0m - \u001B[38;5;5mplaying chess\u001B[38;5;0m\n" +
                        "  \u001B[38;5;12mhelp\u001B[38;5;0m - \u001B[38;5;5mwith possible commands\u001B[38;5;0m\n",
                client.eval("HELP")
        );

        client.eval("register joe jackson joe@mail.com");

        assertEquals("  \u001B[38;5;12mcreate <NAME>\u001B[38;5;0m - \u001B[38;5;5ma game\u001B[38;5;0m\n" +
                        "  \u001B[38;5;12mlist\u001B[38;5;0m - \u001B[38;5;5mgames\u001B[38;5;0m\n" +
                        "  \u001B[38;5;12mjoin <ID>\u001B[38;5;0m - \u001B[38;5;5ma game\u001B[38;5;0m\n" +
                        "  \u001B[38;5;12mobserve <ID>\u001B[38;5;0m - \u001B[38;5;5ma game\u001B[38;5;0m\n" +
                        "  \u001B[38;5;12mlogout\u001B[38;5;0m - \u001B[38;5;5mwhen you are done\u001B[38;5;0m\n" +
                        "  \u001B[38;5;12mquit\u001B[38;5;0m - \u001B[38;5;5mplaying chess\u001B[38;5;0m\n" +
                        "  \u001B[38;5;12mhelp\u001B[38;5;0m - \u001B[38;5;5mwith possible commands\u001B[38;5;0m\n",
                client.eval("HELP")
        );

    }

    @Test
    public void registerTest() throws Exception {
        assertEquals("Success", client.eval("register joe password c@mail.com"));
    }


    @Test
    public void listGamesTest() throws Exception {
        assertEquals("Success", client.eval("register joe password c@mail.com"));

        assertEquals("Success", client.eval("create game1"));
        assertEquals("Success", client.eval("create game2"));

        var gameListText = client.eval("list");
        var gameList = new Gson().fromJson(gameListText, GameListResponse.class);
        assertTrue(gameList.success);
        assertEquals(2, gameList.games.length);
    }
}

