import javax.websocket.*;
import java.net.URI;
import java.util.Scanner;

public class WSClient extends Endpoint {

    public static void main(String[] args) throws Exception {
        var ws = new WSClient();
        Scanner scanner = new Scanner(System.in);

        while (true) ws.session.getBasicRemote().sendText(scanner.nextLine());
    }

    Session session;

    public WSClient() throws Exception {
        URI socketURI = new URI("ws://localhost:8080/connect");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, socketURI);

        this.session.addMessageHandler((MessageHandler.Whole<String>) System.out::println);
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
}