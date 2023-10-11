package server;

import dataAccess.MemoryDataAccess;
import service.AdminService;
import service.AuthService;
import service.GameService;
import service.UserService;

public class Server {
    public static void main(String[] args) {
        new Server().run();
    }

    private void run() {
        try {
            var dataAccess = new MemoryDataAccess();
            var userService = new UserService(dataAccess);
            var gameService = new GameService(dataAccess);
            var adminService = new AdminService(dataAccess);
            var authService = new AuthService(dataAccess);

        } catch (Exception ex) {
            System.out.printf("Unable to start server: %s", ex.getMessage());
            System.exit(1);
        }
    }
}
