package model;

import chess.ChessGame;

import java.util.UUID;


/**
 * Represents the players and current state of a Chess game.
 */
public class GamePlay {

    /**
     * Descriptive name of the game.
     */
    private String name;

    /**
     * Unique identifier of the game.
     */
    private String id;

    /**
     * The black player user ID. NUll if not set.
     */
    private String blackId;

    /**
     * The black player user ID. Null if not set.
     */
    private String whiteId;

    /**
     * The moves of the game. When serialized, this outputs the board positions.
     */
    private ChessGame game;

    /**
     * Create a game. This will generate a unique ID for the game.
     */
    public GamePlay() {
        this.id = UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getBlackId() {
        return blackId;
    }

    public void setBlackId(String blackId) {
        this.blackId = blackId;
    }

    public String getWhiteId() {
        return whiteId;
    }

    public void setWhiteId(String whiteId) {
        this.whiteId = whiteId;
    }

    public ChessGame getGame() {
        return game;
    }

    public void setGame(ChessGame game) {
        this.game = game;
    }
}
