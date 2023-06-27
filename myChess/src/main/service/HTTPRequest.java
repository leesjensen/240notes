package service;

import com.google.gson.Gson;
import spark.*;

/**
 * A service request.
 */
public class HTTPRequest {
    private String body;


    public HTTPRequest(Request request) {
        this.body = request.body();
    }

    /**
     * Gets the body of the service requests.
     *
     * @param clazz The class to convert the request body to.
     * @return The body as the requested object.
     */
    public <T> T getBody(Class<T> clazz) {
        return new Gson().fromJson(body, clazz);
    }
}
