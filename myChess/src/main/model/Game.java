package model;

import chess.ChessGame;

import java.util.UUID;


/**
 * Represents the players and current state of a Chess game.
 */
public class Game {

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
    private ChessGame game;

    /**
     * Create a game. This will generate a unique ID for the game.
     */
    public Game() {
        this.gameID = UUID.randomUUID().hashCode();
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

    public ChessGame getGame() {
        return game;
    }

    public void setGame(ChessGame game) {
        this.game = game;
    }
}
