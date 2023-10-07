package model;

/**
 * Associates a user and a unique token to demonstrate the user's authorization state.
 */
public class AuthData {
    /**
     * Unique token representing the authorization.
     */
    private final String authToken;

    /**
     * User ID associated with the token.
     */
    private final int userID;

    /**
     * Creates an immutable authorization.
     *
     * @param userID to associate with token
     */
    public AuthData(int userID, String authToken) {
        this.authToken = authToken;
        this.userID = userID;
    }

    /**
     * The actual token
     *
     * @return the token
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * The user associated with the token.
     *
     * @return the user ID
     */
    public int getUserID() {
        return userID;
    }
}
