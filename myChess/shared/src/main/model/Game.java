package model;

import chess.ChessGame;


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


    public Game() {
        game = new chess.Game();
    }


    /**
     * Create a game. This will generate a unique ID for the game.
     */
    public Game(Game copy) {
        this.gameID = copy.gameID;
        this.gameName = copy.gameName;
        this.blackPlayerID = copy.blackPlayerID;
        this.whitePlayerID = copy.whitePlayerID;
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

    public Game setGameID(int gameID) {
        this.gameID = gameID;
        return this;
    }

    public int getBlackPlayerID() {
        return blackPlayerID;
    }

    public Game setBlackPlayerID(int blackPlayerID) {
        this.blackPlayerID = blackPlayerID;
        return this;
    }

    public int getWhitePlayerID() {
        return whitePlayerID;
    }

    public Game setWhitePlayerID(int whitePlayerID) {
        this.whitePlayerID = whitePlayerID;
        return this;
    }

    public ChessGame getGame() {
        return game;
    }

    public Game setGame(ChessGame game) {
        this.game = game;
        return this;
    }
}
