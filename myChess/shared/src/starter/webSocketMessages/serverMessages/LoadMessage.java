package webSocketMessages.serverMessages;

import chess.Game;

public class LoadMessage extends ServerMessage {
    public Game game;

    public LoadMessage(Game game) {
        super(ServerMessageType.LOAD_GAME);
        this.game = game;
    }
}
