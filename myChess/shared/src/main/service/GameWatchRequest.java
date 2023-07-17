package service;


import com.google.gson.Gson;

public class GameWatchRequest {
    public int gameID;

    public GameWatchRequest(int gameID) {
        this.gameID = gameID;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}