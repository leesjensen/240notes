package service;


import dataAccess.DataAccess;
import spark.Request;
import spark.Response;

/**
 * Provides endpoints for administrating the application.
 * <p>[DELETE] /db - Clear application data.
 */
public class AdminService {

    private final DataAccess dataAccess;

    public AdminService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public Object clearApplication(Request ignoreReq, Response ignoreRes) {
        clearApplication();
        return null;
    }

    /**
     * Clears the database. Removes all users, games, and authTokens. This is only
     * useful for testing purposes. In production this endpoint should never be
     * called.
     */
    public void clearApplication() {

    }
}
