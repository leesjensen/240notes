package chess;

import com.google.gson.Gson;
import model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SerializationTests {
    @Test
    void chessGameSerialization() {
        var game = new GameImpl();
        game.board.resetBoard();

        var serializer = GameImpl.serializer();

        var text = serializer.toJson(game);
        var actual = serializer.fromJson(text, ChessGame.class);
        Assertions.assertEquals(game, actual);
    }


    @Test
    void gameDataSerialization() {
        var game = new GameImpl();
        game.board.resetBoard();
        var gameData = new GameData(3, null, "joe", "test", game, GameData.State.WHITE);
        var x = new Gson().toJson(gameData);

        var serializer = GameImpl.serializer();
        var deserialized = serializer.fromJson(x, GameData.class);
        System.out.println(deserialized);
    }
}