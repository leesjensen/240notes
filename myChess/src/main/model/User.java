package model;

import java.util.UUID;

/**
 * The user of the application. This may be a player, observer, or admin.
 */
public class User {
    /**
     * Non-unique textual name of the user.
     */
    private String username;
    private String password;
    private String email;

    /**
     * ID associated with the user.
     */
    private int userID;

    public User(int userID) {
        this.userID = userID;
    }

    public User(User copy) {
        this.username = copy.username;
        this.password = copy.password;
        this.email = copy.email;
        if (copy.userID == 0) {
            this.userID = Math.abs(UUID.randomUUID().hashCode());
        }
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getUserID() {
        return userID;
    }
}
