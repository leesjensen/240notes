package service;

import com.google.gson.Gson;
import model.*;

public class GameListResponse {
    public boolean success;
    public GameResponse[] games;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}

