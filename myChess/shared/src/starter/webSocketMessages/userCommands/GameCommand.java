package webSocketMessages.userCommands;

import java.util.Objects;

/**
 * Represents a command a user can send the server over a websocket
 */
public class GameCommand {

    public GameCommand(CommandType commandType, int gameID, String authToken) {
        this.authToken = authToken;
        this.commandType = commandType;
        this.gameID = gameID;
    }

    public enum CommandType {
        JOIN_PLAYER,
        JOIN_OBSERVER,
        MAKE_MOVE,
        LEAVE,
        RESIGN
    }

    public final CommandType commandType;

    public final String authToken;
    public final int gameID;
}
