package service;

import model.UserData;

/**
 * Provides endpoints for registering a user.
 * <p>
 * <p>[POST] /user - Register user
 */
public class UserService {
    /**
     * Persistently registerUser.
     * @param user to add.
     * @throws CodedException if a user with the same username already exists
     * @return the newly added user.
     */
    public Object registerUser(UserData user) throws CodedException {
        return null;
    }
}
