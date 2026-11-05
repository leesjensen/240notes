package websocket;

import jakarta.websocket.ContainerProvider;
import jakarta.websocket.Endpoint;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;

import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

public class ClientExample extends Endpoint {
    public Session session;

    public static void main(String[] args) throws Exception {
        ClientExample client = new ClientExample();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a message you want to echo:");
        while(true) {
            client.send(scanner.nextLine());
        }
    }

    public ClientExample() throws Exception {
        URI uri = new URI("ws://localhost:8080/ws");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();

        // This call connects to the server and triggers onOpen
        this.session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
                System.out.println(message);
                System.out.println("\nEnter another message you want to echo:");
            }
        });
    }

    public void send(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    // This method is called when the connection is established
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
}