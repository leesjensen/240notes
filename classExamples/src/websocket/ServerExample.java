package websocket;

import io.javalin.Javalin;
import io.javalin.websocket.WsContext;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerExample {
    public static void main(String[] args) {
        Javalin.create()
                .get("/echo/{msg}", ctx -> ctx.result("HTTP response: " + ctx.pathParam("msg")))
                .ws("/ws", ws -> {
                    ws.onConnect(ctx -> {
                        ctx.enableAutomaticPings();
                        sendPeriodicMessages(ctx);
                        System.out.println("Websocket connected");
                    });
                    ws.onMessage(ctx -> ctx.send("WebSocket response:" + ctx.message()));
                    ws.onClose(ctx -> System.out.println("Websocket closed"));
                })
                .start(8080);
        System.out.println("listening on port 8080");
    }

    static public void sendPeriodicMessages(WsContext ctx) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {
            try {
                if (ctx.session.isOpen()) {
                    ctx.send("SERVER: just saying hello");
                }
            } catch (Exception ignore) {
            }
        }, 0, 10, TimeUnit.SECONDS);
    }
}
