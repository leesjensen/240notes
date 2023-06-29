package service;

import com.google.gson.Gson;
import model.*;

public class GameResponse {
    public int gameID;
    public String whiteUsername;
    public String blackUsername;
    public String gameName;

    public GameResponse(Game game) {
        this.gameID = game.getGameID();
        this.gameName = game.getGameName();
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
