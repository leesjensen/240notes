package ui;

import chess.Game;
import chess.Piece;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import webSocketMessages.serverMessages.*;
import webSocketMessages.userCommands.GameCommand;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

//need to extend Endpoint for websocket to work properly
public class WebSocketFacade extends Endpoint {

    Session session;
    DisplayHandler responseHandler;


    public WebSocketFacade(String url, DisplayHandler responseHandler) throws DeploymentException, IOException, URISyntaxException {
        URI socketURI = new URI(url);
        this.responseHandler = responseHandler;


        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, socketURI);

        //set message handler
        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                var gson = Game.createSerializer();

                try {
                    ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                    if (serverMessage.getServerMessageType() == ServerMessage.ServerMessageType.LOAD_GAME) {
                        LoadMessage loadGameMessage = gson.fromJson(message, LoadMessage.class);
                        responseHandler.updateBoard(loadGameMessage.game); //draw game
                    } else if (serverMessage.getServerMessageType() == ServerMessage.ServerMessageType.NOTIFICATION) {
                        NotificationMessage notificationMessage = gson.fromJson(message, NotificationMessage.class);
                        responseHandler.printMessage(notificationMessage.message);
                    } else if (serverMessage.getServerMessageType() == ServerMessage.ServerMessageType.ERROR) {
                        ErrorMessage errorMessage = gson.fromJson(message, ErrorMessage.class);
                        responseHandler.printMessage(errorMessage.errorMessage);
                    } else {
                        System.out.println("Received invalid message");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error: " + e.getMessage());
                }
            }
        });
    }

    //Endpoint requires this method, but you don't have to do anything
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public void sendCommand(GameCommand command) throws IOException {
        this.session.getBasicRemote().sendText(new Gson().toJson(command));
    }
}

