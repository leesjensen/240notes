package service;

import com.google.gson.Gson;

public class CreateGameResponse {
    public int gameID;
    public boolean success;


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
