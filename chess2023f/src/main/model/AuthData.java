package model;

import java.util.UUID;

/**
 * Associates a user and a unique token to demonstrate the user's authorization state.
 */
public record AuthData(String authToken, String userName) {
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
