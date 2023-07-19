package webSocketMessages.userCommands;

import chess.ChessGame;

import static webSocketMessages.userCommands.GameCommand.CommandType.JOIN_PLAYER;

public class JoinPlayerCommand extends GameCommand {
    public final ChessGame.TeamColor playerColor;

    public JoinPlayerCommand(String authToken, Integer gameID, ChessGame.TeamColor playerColor) {
        super(JOIN_PLAYER, gameID, authToken);
        this.playerColor = playerColor;
    }
}
