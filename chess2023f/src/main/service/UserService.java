package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.AuthData;
import model.UserData;
import spark.Request;
import spark.Response;

/**
 * Provides endpoints for registering a user.
 * <p>[POST] /user - Register user
 */
public class UserService extends Service {
    final private DataAccess dataAccess;

    public UserService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public Object registerUser(Request req, Response res) throws CodedException {
        var user = getBody(req, UserData.class);
        var authToken = registerUser(user);
        return send("username", user.username(), "authToken", authToken.authToken());
    }


    /**
     * Persistently registerUser.
     *
     * @param user to add.
     * @return the authorization information for the new user.
     * @throws CodedException if a user with the same username already exists
     */
    public AuthData registerUser(UserData user) throws CodedException {
        try {
            user = dataAccess.writeUser(user);
            return dataAccess.writeAuth(user);
        } catch (DataAccessException ex) {
            throw new CodedException(403, "Unable to register user");
        }
    }
}
