package server;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;
import webSocketMessages.userCommands.UserGameCommand;

@WebSocket
public class WebSocketHandler {
    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        System.out.println("connection opened");
    }


    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        System.out.println("connection closed");
    }


    @OnWebSocketMessage
    public void onMessage(Session user, String message) throws java.io.IOException {
        System.out.println("message received: " + message);

        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        System.out.println("JSON received: " + new Gson().toJson(command));
        user.getRemote().sendString("hello");
    }
}
