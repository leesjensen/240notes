package service;

import com.google.gson.Gson;


/**
 * A service request.
 */
public class Request {
    private Gson body;

    /**
     * Gets the body of the service requests.
     *
     * @param clazz The class to convert the request body to.
     * @return The body as the requested object.
     */
    public <T> T getBody(Class<T> clazz) {
        final String json = "{}";
        return new Gson().fromJson(json, clazz);
    }
}
