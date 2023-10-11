package dataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.MemoryDataAccess;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MemoryDataAccessTests {

    @Test
    public void writeReadUser() throws DataAccessException {
        var db = new MemoryDataAccess();
        var user = new UserData("juan", "too many secrets", "juan@byu.edu");

        Assertions.assertEquals(user, db.writeUser(user));
        Assertions.assertEquals(user, db.readUser(user.username()));
    }

}
