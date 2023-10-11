package service;


import dataAccess.DataAccess;

/**
 * Provides endpoints for administrating the application.
 * <p>[DELETE] /db - Clear application data.
 */
public class AdminService {

    public AdminService(DataAccess dataAccess) {

    }

    /**
     * Clears the database. Removes all users, games, and authTokens. This is only
     * useful for testing purposes. In production this endpoint should never be
     * called.
     */
    public void clearApplication() {
    }
}
