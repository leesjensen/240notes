package serviceTests;

import dataAccess.MemoryDataAccess;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.CodedException;
import service.UserService;

public class AdminServiceTests {

    @Test
    public void registerUser() {
        var service = new UserService(new MemoryDataAccess());
        var user = new UserData("juan", "too many secrets", "juan@byu.edu");

        Assertions.assertDoesNotThrow(() -> service.registerUser(user));
    }


    @Test
    public void registerUserDuplicate() {
        var service = new UserService(new MemoryDataAccess());
        var user = new UserData("juan", "too many secrets", "juan@byu.edu");

        Assertions.assertDoesNotThrow(() -> service.registerUser(user));
        Assertions.assertThrows(CodedException.class, () -> service.registerUser(user));
    }
}
