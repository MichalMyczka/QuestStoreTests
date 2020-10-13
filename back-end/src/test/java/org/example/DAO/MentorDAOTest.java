package org.example.DAO;

import org.example.model.Mentor;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MentorDAOTest {

    @Test
    void add() {
        UUID roleUUID = UUID.fromString("745792a7-681b-4efe-abdd-ca027678b397");
        Mentor testingMentor = new Mentor(UUID.randomUUID(), "testest", "test", "blabla", "bla", roleUUID,
                true, "1234", "mentor", UUID.randomUUID());
        DBConnection dbConnection = new DBConnection();
        MentorDAO mentorDAO = new MentorDAO(dbConnection);
        int expected = mentorDAO.getAll().size()+1;
        mentorDAO.add(testingMentor);
        Assert.assertEquals(expected,mentorDAO.getAll().size());
    }

    @Test
    void remove() {
    }

    @Test
    void edit() {
    }

    @Test
    void getAll() {
    }

    @Test
    void get() {
    }
}