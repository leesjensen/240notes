package webSocketMessages.userCommands;

import chess.Move;

import static webSocketMessages.userCommands.GameCommand.CommandType.JOIN_PLAYER;
import static webSocketMessages.userCommands.GameCommand.CommandType.MAKE_MOVE;

public class MoveCommand extends GameCommand {
    public final Move move;

    public MoveCommand(String authToken, Integer gameID, Move move) {
        super(MAKE_MOVE, gameID, authToken);
        this.move = move;
    }
}
