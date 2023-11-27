package websocket;

import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import spark.Spark;

@WebSocket
public class ServerExample {
    public static void main(String[] args) {
        Spark.port(8080);
        Spark.webSocket("/connect", ServerExample.class);
        Spark.get("/echo/:msg", (req, res) -> "HTTP response: " + req.params(":msg"));

        System.out.println("listening on port 8080");
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        System.out.printf("Received: %s%n", message);
        session.getRemote().sendString("WebSocket response: " + message);
    }
}
