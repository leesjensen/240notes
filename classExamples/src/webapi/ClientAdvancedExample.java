package webapi;

import com.google.gson.Gson;

import java.io.*;
import java.net.*;
import java.util.Map;

public class ClientAdvancedExample {
    public static void main(String[] args) throws Exception {
        URI uri = new URI("http://localhost:8080/error");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("GET");

        http.connect();

        // Handle bad HTTP status
        var status = http.getResponseCode();
        if ( status >= 200 && status < 300) {
            try (InputStream in = http.getInputStream()) {
                System.out.println(new Gson().fromJson(new InputStreamReader(in), Map.class));
            }
        } else {
            try (InputStream in = http.getErrorStream()) {
                System.out.println(new Gson().fromJson(new InputStreamReader(in), Map.class));
            }
        }
    }
}
