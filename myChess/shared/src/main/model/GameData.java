package model;

import chess.ChessGame;
import chess.Game;
import chess.InvalidMoveException;
import chess.Move;


/**
 * Represents the players and current state of a Chess game.
 */
public class GameData {

    enum GameState {
        WHITE,
        BLACK,
        DRAW,
        UNDECIDED
    }

    /**
     * Descriptive name of the game.
     */
    private String gameName;

    /**
     * Unique identifier of the game.
     */
    private int gameID;

    /**
     * The black player user ID.
     */
    private int blackPlayerID;

    /**
     * The white player user ID.
     */
    private int whitePlayerID;

    /**
     * The moves of the game. When serialized, this outputs the board positions.
     */
    private Game game;

    public boolean isGameOver() {
        return state != GameState.UNDECIDED;
    }

    public void makeMove(Move move) throws InvalidMoveException {
        game.makeMove(move);
        var nextTurn = game.getTeamTurn();

        if (game.isInCheckmate(nextTurn)) {
            resign(nextTurn);
        } else if (game.isInStalemate(nextTurn)) {
            state = GameState.DRAW;
        }
    }

    public void resign(ChessGame.TeamColor color) {
        state = color == ChessGame.TeamColor.WHITE ? GameState.BLACK : GameState.WHITE;
    }

    private GameState state;

    public GameData() {
        game = new chess.Game();
        state = GameState.UNDECIDED;
    }


    public GameData(GameData copy) {
        this.gameID = copy.gameID;
        this.gameName = copy.gameName;
        this.blackPlayerID = copy.blackPlayerID;
        this.whitePlayerID = copy.whitePlayerID;
        this.state = copy.state;
        this.game = new chess.Game(copy.getGame());
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getGameID() {
        return gameID;
    }

    public GameData setGameID(int gameID) {
        this.gameID = gameID;
        return this;
    }

    public int getBlackPlayerID() {
        return blackPlayerID;
    }

    public void setBlackPlayerID(int blackPlayerID) {
        this.blackPlayerID = blackPlayerID;
    }

    public int getWhitePlayerID() {
        return whitePlayerID;
    }

    public void setWhitePlayerID(int whitePlayerID) {
        this.whitePlayerID = whitePlayerID;
    }

    public Game getGame() {
        return game;
    }

    public GameData setGame(Game game) {
        this.game = game;
        return this;
    }
}
