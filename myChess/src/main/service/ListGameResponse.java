package service;

import dataAccess.*;
import model.*;

import java.util.*;

class ListGamesResponse {
    public int gameID;
    public String whiteUsername;
    public String blackUsername;
    public String gameName;

    public ListGamesResponse(Game game) {
        this.gameID = game.getGameID();
        this.gameName = game.getGameName();
    }


    public static List<ListGamesResponse> toList(Collection<Game> games, DataAccess dataAccess) throws DataAccessException {
        ArrayList<ListGamesResponse> list = new ArrayList<>();
        for (var game : games) {
            var gameResponse = new ListGamesResponse(game);
            gameResponse.blackUsername = readUsername(game.getBlackPlayerID(), dataAccess);
            gameResponse.whiteUsername = readUsername(game.getWhitePlayerID(), dataAccess);
            list.add(gameResponse);
        }
        return list;
    }

    private static String readUsername(int userID, DataAccess dataAccess) throws DataAccessException {
        if (userID != 0) {
            var user = dataAccess.readUser(new User(userID));
            if (user != null) {
                return user.getUsername();
            }
        }
        return null;
    }
}
