package model;

import chess.ChessGame;

public class GameData {
    final private int gameID;
    final private String whiteUsername;
    final private String blackUsername;
    final private String gameName;
    final private ChessGame gameData;

    public GameData(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame gameData) {
        this.gameID = gameID;
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
        this.gameName = gameName;
        this.gameData = gameData;
    }

    public int getGameID() {
        return gameID;
    }

    public String getWhiteUsername() {
        return whiteUsername;
    }

    public String getBlackUsername() {
        return blackUsername;
    }

    public String getGameName() {
        return gameName;
    }

    public ChessGame getGameData() {
        return gameData;
    }
}
