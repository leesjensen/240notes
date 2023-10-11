package model;

/**
 * Associates a user and a unique token to demonstrate the user's authorization state.
 */
public record AuthData(String authToken, int userID) {
}
