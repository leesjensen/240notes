package webapi;

import com.google.gson.Gson;

import java.io.*;
import java.net.*;
import java.util.Map;

public class ClientExample {
    public static void main(String[] args) throws Exception {
        // Specify the desired endpoint
        URI uri = new URI("http://localhost:8080/name");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("GET");

        // Make the request
        http.connect();

        // Output the response body
        try (InputStream respBody = http.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            System.out.println(new Gson().fromJson(inputStreamReader, Map.class));
        }
    }
}
