package webapi;

import java.net.*;
import java.net.http.*;

public class ClientExample {

    // Run ServerExample first

    public static void main(String[] args) throws Exception {
        try (HttpClient client = HttpClient.newHttpClient()) {
            send(client, HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/name/joe"))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .header("Authorization", "secret1")
                    .build());

            send(client, HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/name"))
                    .PUT(HttpRequest.BodyPublishers.ofString("{\"joe\":\"sue\"}"))
                    .header("Authorization", "secret1")
                    .build());

            send(client, HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/name"))
                    .GET()
                    .build());
        }
    }

    private static void send(HttpClient client, HttpRequest request) throws Exception {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() == 200) {
            System.out.println(response.body());
        } else {
            System.out.println("Error: received status code " + response.statusCode());
        }

    }
}
