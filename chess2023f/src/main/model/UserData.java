package model;

/**
 * A User represents a player or observer.
 */
public class UserData {
    final private String username;
    final private String password;

    final private String email;

    public UserData(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

}
