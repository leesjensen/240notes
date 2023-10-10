package service;

import model.UserData;

/**
 * Provides endpoints for registering a user.
 * <p>[POST] /user - Register user
 */
public class UserService {
    /**
     * Persistently registerUser.
     *
     * @param user to add.
     * @return the newly added user.
     * @throws CodedException if a user with the same username already exists
     */
    public Object registerUser(UserData user) throws CodedException {
        return null;
    }
}
