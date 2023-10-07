package service;

import chess.ChessGame;
import model.GameData;
import model.UserData;

import java.util.Collection;

/**
 * Provides endpoints for manipulating games.
 * <p>
 * <p>[GET] /game - Lists games
 * <p>[POST] / game - Create game
 * <p>[PUT] / - Join game
 */
public class GameService {
    /**
     * List all the games. This includes pending, active and completed games.
     * @return the collection of games.
     */
    public Collection<GameData> listGames() {
        return null;
    }

    /**
     * Creates a new game.
     * @param gameName to create
     * @return the newly created game.
     */
    public GameData createGame(String gameName) {
        return null;
    }

    /**
     * Verifies that the specified game exists, and, if a color is specified,
     * adds the caller as the requested color to the game. If no color is specified
     * the user is joined as an observer. This request is idempotent.
     * @param user joining the game.
     * @param color to join the game as. If null then the user is joined as an observer.
     * @return the updated game.
     */
    public GameData joinGame(UserData user, ChessGame.TeamColor color, String gameID) {
        return null;
    }
}
