package service;


import dataAccess.DataAccess;
import model.UserData;

/**
 * Provides endpoints for authorizing access.
 * <p>[POST] /session - Create session
 * <p>[DELETE] /session - Delete session
 */
public class AuthService {

    public AuthService(DataAccess dataAccess) {

    }

    /**
     * Create a session for a user. If the user already has a session then
     * the previous session is invalidated.
     *
     * @param user to create a session for.
     * @return the authToken for the session.
     */
    public String createSession(UserData user) {
        return null;
    }

    /**
     * Deletes a user's session. If the token is not valid then no error is generated.
     *
     * @param authToken that currently represents a user.
     */
    public void deleteSession(String authToken) {
    }
}
