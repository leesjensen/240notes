package service;


/**
 * Provides endpoints for administrating the application.
 * <p>
 * <p>[DELETE] /db - Clear application data.
 */
public class AdminService {
    /**
     * Clears the database. Removes all users, games, and authTokens. This is only
     * useful for testing purposes. In production this endpoint should never be
     * called.
     */
    public void clearApplication() {
    }
}
