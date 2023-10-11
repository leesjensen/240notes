package serviceTests;

import dataAccess.DataAccessException;
import dataAccess.MemoryDataAccess;
import dataAccessTests.MemoryDataAccessTests;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.CodedException;
import service.UserService;

public class UserServiceTests {

    @Test
    public void registerUser() throws DataAccessException {
        var service = new UserService(new MemoryDataAccess());
        var user = new UserData("juan", "too many secrets", "juan@byu.edu");

        Assertions.assertDoesNotThrow(() -> service.registerUser(user));
    }


    @Test
    public void registerUserDuplicate() throws DataAccessException {
        var service = new UserService(new MemoryDataAccess());
        var user = new UserData("juan", "too many secrets", "juan@byu.edu");

        Assertions.assertDoesNotThrow(() -> service.registerUser(user));
        Assertions.assertThrows(CodedException.class, () -> service.registerUser(user));
    }
}
