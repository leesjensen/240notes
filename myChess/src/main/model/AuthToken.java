package model;


import java.util.Objects;

/**
 * Associates a user and a unique token to demonstrate the user's authorization state.
 */
public class AuthToken {
    /**
     * Unique token representing the authorization.
     */
    private final String authToken;

    /**
     * User ID associated with the token.
     */
    private final int userID;

    /**
     * Creates an immutable authorization token.
     *
     * @param authToken to associate with User
     * @param userID    to associate with token
     */
    public AuthToken(String authToken, int userID) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken token = (AuthToken) o;
        return Objects.equals(getAuthToken(), token.getAuthToken()) && Objects.equals(getUserID(), token.getUserID());
    }
}
