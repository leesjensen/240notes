package service;


import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.AuthData;
import model.UserData;
import spark.Request;
import spark.Response;

/**
 * Provides endpoints for authorizing access.
 * <p>[POST] /session - Create session
 * <p>[DELETE] /session - Delete session
 */
public class AuthService extends Service {

    private final DataAccess dataAccess;

    public AuthService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public Object createSession(Request req, Response ignore) throws CodedException {
        var user = getBody(req, UserData.class);
        var authData = createSession(user);
        return send("username", user.username(), "authToken", authData.authToken());
    }

    /**
     * Create a session for a user. If the user already has a session then
     * the previous session is invalidated.
     *
     * @param user to create a session for.
     * @return the authToken for the session.
     */
    public AuthData createSession(UserData user) throws CodedException {
        try {
            UserData loggedInUser = dataAccess.readUser(user.username());
            if (loggedInUser != null && loggedInUser.password().equals(user.password())) {
                return dataAccess.writeAuth(loggedInUser);
            }
            throw new CodedException(401, "Invalid username or password");
        } catch (DataAccessException ex) {
            throw new CodedException(500, "Internal server error");
        }
    }

    /**
     * Deletes a user's session. If the token is not valid then no error is generated.
     *
     * @param authToken that currently represents a user.
     */
    public void deleteSession(String authToken) {
    }
}
