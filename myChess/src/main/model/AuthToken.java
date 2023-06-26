package model;


import java.util.Objects;

/**
 * Associates a user and a unique token to demonstrate the user's authorization state.
 */
public class AuthToken {
    /**
     * Unique token representing the authorization.
     */
    private final String token;

    /**
     * User ID associated with the token.
     */
    private final String userId;

    /**
     * Creates an immutable authorization token.
     *
     * @param token  to associate with User
     * @param userId to associate with token
     */
    public AuthToken(String token, String userId) {
        this.token = token;
        this.userId = userId;
    }

    /**
     * The actual token
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * The user associated with the token.
     *
     * @return the user ID
     */
    public String getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken token = (AuthToken) o;
        return Objects.equals(getToken(), token.getToken()) && Objects.equals(getUserId(), token.getUserId());
    }
}
