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
    private final int userID;

    public User(String name, String username, String password, String email) {
        this.username = name;
        this.password = password;
        this.email = email;
        this.userID = UUID.randomUUID().hashCode();
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserID() {
        return userID;
    }
}
