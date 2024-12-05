package websocket;

import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import spark.Spark;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebSocket
public class ServerExample {
    public static void main(String[] args) {
        Spark.port(8080);
        Spark.webSocket("/ws", ServerExample.class);
        Spark.get("/echo/:msg", (req, res) -> "HTTP response: " + req.params(":msg"));

        System.out.println("listening on port 8080");
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        session.getRemote().sendString("SERVER: " + message);

    }

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @OnWebSocketConnect
    public void onConnect(Session session) {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                session.getRemote().sendString("SERVER: just saying hello");
            } catch (IOException ignore) {
            }
        }, 0, 10, TimeUnit.SECONDS);
    }
}
