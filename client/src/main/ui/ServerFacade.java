package ui;

import chess.ChessGame;
import com.google.gson.Gson;
import model.AuthData;
import model.GameData;
import server.JoinRequest;
import server.ListGamesResponse;
import util.ResponseException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Map;

public class ServerFacade {

    private final String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }


    public void clear() throws ResponseException {
        var r = this.makeRequest("POST", "/clear", null, null, Map.class);
    }

    public AuthData register(String username, String password, String email) throws ResponseException {
        var request = Map.of("username", username, "password", password, "email", email);
        return this.makeRequest("POST", "/user", request, null, AuthData.class);
    }

    public AuthData login(String username, String password) throws ResponseException {
        var request = Map.of("username", username, "password", password);
        return this.makeRequest("POST", "/user/login", request, null, AuthData.class);
    }

    public void logout(String authToken) throws ResponseException {
        this.makeRequest("POST", "/user/logout", null, authToken, null);
    }

    public GameData createGame(String authToken, String gameName) throws ResponseException {
        var request = Map.of("gameName", gameName);
        return this.makeRequest("POST", "/games/create", request, authToken, GameData.class);
    }

    public ListGamesResponse listGames(String authToken) throws ResponseException {
        return this.makeRequest("GET", "/games/list", null, authToken, ListGamesResponse.class);
    }

    public void joinGame(String authToken, int gameID, ChessGame.TeamColor color) throws ResponseException {
        var request = new JoinRequest(color, gameID);
        this.makeRequest("POST", "/games/join", request, authToken, null);
    }

    private <T> T makeRequest(String method, String path, Object request, String authToken, Class<T> clazz) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
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
//        System.out.println(String.format(("Status %d returned from [%s] %s"), http.getResponseCode(), method, path));

            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (http.getResponseCode() == 200) {
                    if (clazz != null) {
                        return new Gson().fromJson(reader, clazz);
                    }
                    return null;
                }

                throw new ResponseException(http.getResponseCode(), reader);
            }
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

}
