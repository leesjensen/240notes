package service;

import com.google.gson.Gson;

public class LoginResponse {
    public boolean success;
    public String authToken;
    public String username;


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}