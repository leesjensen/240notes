package webapi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SimpleClientExample {

    // Run ServerExample first

    public static void main(String[] args) throws Exception {
        try (HttpClient client = HttpClient.newHttpClient()) {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200) {
                System.out.println(response.body());
            } else {
                System.out.println("Error: received status code " + response.statusCode());
            }

        }
    }
}
