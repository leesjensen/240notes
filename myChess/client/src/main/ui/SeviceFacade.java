package ui;

import chess.ChessGame;
import com.google.gson.Gson;
import service.*;

import java.io.*;
import java.util.*;
import java.net.*;

public class SeviceFacade {

    private String serverUrl;

    public SeviceFacade(String url) {
        serverUrl = url;
    }


    public void clear() throws Exception {
        var r = this.makeRequest("POST", "/clear", null, null, Map.class);
    }

    public LoginResponse register(String username, String password, String email) throws Exception {
        var request = Map.of("username", username, "password", password, "email", email);
        return this.makeRequest("POST", "/user/register", request, null, LoginResponse.class);
    }

    public LoginResponse login(String username, String password) throws Exception {
        var request = Map.of("username", username, "password", password);
        return this.makeRequest("POST", "/user/login", request, null, LoginResponse.class);
    }

    public LogoutResponse logout(String authToken) throws Exception {
        return this.makeRequest("POST", "/user/logout", null, authToken, LogoutResponse.class);
    }

    public CreateGameResponse createGame(String authToken, String gameName) throws Exception {
        var request = Map.of("gameName", gameName);
        return this.makeRequest("POST", "/games/create", request, authToken, CreateGameResponse.class);
    }

    public ListGamesResponse listGames(String authToken) throws Exception {
        return this.makeRequest("GET", "/games/list", null, authToken, ListGamesResponse.class);
    }

    public GameJoinResponse joinGame(String authToken, int gameID, ChessGame.TeamColor color) throws Exception {
        var request = new JoinRequest(gameID, color);
        return this.makeRequest("POST", "/games/join", request, authToken, GameJoinResponse.class);
    }

    private <T> T makeRequest(String method, String path, Object request, String authToken, Class<T> clazz) throws Exception {
        URL url = new URL(serverUrl + path);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod(method);
        http.setDoOutput(true);

        if (authToken != null) {
            http.addRequestProperty("Authorization", authToken);
        }

        if (request != null) {
            http.addRequestProperty("Accept", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
        http.connect();
        var statusCode = http.getResponseCode();
        System.out.println(String.format(("Status %d returned from [%s] %s"), statusCode, method, path));
        try (InputStream respBody = http.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            return new Gson().fromJson(inputStreamReader, clazz);
        }
    }

}
