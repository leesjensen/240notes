package service;

import com.google.gson.Gson;

public class GameJoinResponse {
    public boolean success;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
