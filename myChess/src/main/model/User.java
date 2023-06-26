package model;

import java.util.UUID;

/**
 * The user of the application. This may be a player, observer, or admin.
 */
public class User {
    /**
     * Non-unique textual name of the user.
     */
    private String name;

    /**
     * ID associated with the user.
     */
    private final String id;

    public User(String name) {
        this.name = name;
        this.id = UUID.randomUUID().toString();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }
}
