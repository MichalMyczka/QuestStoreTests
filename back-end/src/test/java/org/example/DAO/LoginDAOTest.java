package org.example.DAO;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LoginDAOTest {

    @Test
    void testIsLoginReturnProperUser() throws Exception {
        DBConnection dbConnection = new DBConnection();
        LoginDAO loginDAO = new LoginDAO(dbConnection);
        UUID testID = UUID.fromString("282605db-0ba5-4b42-a593-5f501db1e1ac");
        Assert.assertEquals(testID, loginDAO.login("marta.s@codecool.pl","password").getUserDetailsID());
    }

}