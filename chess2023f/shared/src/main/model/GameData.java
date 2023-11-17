package model;

import chess.ChessGame;
import chess.GameImpl;
import com.google.gson.Gson;

/**
 * Represents the serialization of a game. This includes who the players are, and the game itself.
 */
public record GameData(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game,
                       State state) {
    public enum State {
        WHITE,
        BLACK,
        DRAW,
        UNDECIDED
    }

    public static GameData create(String serializedGame) {
        return GameImpl.serializer().fromJson(serializedGame, GameData.class);
    }


    public boolean isGameOver() {
        return state != State.UNDECIDED;
    }

    public GameData setWhite(String userName) {
        return new GameData(this.gameID, userName, this.blackUsername, this.gameName, this.game, this.state);
    }

    public GameData setBlack(String userName) {
        return new GameData(this.gameID, this.whiteUsername, userName, this.gameName, this.game, this.state);
    }

    public GameData clearBoard() {
        return new GameData(this.gameID, this.whiteUsername, this.blackUsername, this.gameName, null, this.state);
    }

    public GameData resign(ChessGame.TeamColor color) {
        var state = color == ChessGame.TeamColor.WHITE ? State.BLACK : State.WHITE;
        return new GameData(this.gameID, this.whiteUsername, this.blackUsername, this.gameName, this.game, state);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
