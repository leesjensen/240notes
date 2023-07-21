package service;


import chess.ChessGame;
import com.google.gson.Gson;

public class JoinRequest {
    public ChessGame.TeamColor playerColor;
    public int gameID;

    public JoinRequest(int gameID, ChessGame.TeamColor playerColor) {
        this.gameID = gameID;
        this.playerColor = playerColor;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}