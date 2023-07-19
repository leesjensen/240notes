package webSocketMessages.serverMessages;

public class LoadMessage extends ServerMessage {
    public int game;

    public LoadMessage(int gameID) {
        super(ServerMessageType.LOAD_GAME);
        this.game = gameID;
    }
}
