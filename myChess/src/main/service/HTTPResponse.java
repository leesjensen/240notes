package service;


import com.google.gson.Gson;

/**
 * A service response.
 */
public class HTTPResponse {
    /**
     * Converts the given object and sends it as a response.
     *
     * @param obj
     */
    void send(Object obj) {
        System.out.println(new Gson().toJson(obj));
    }
}
