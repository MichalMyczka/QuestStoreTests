package org.example.DAO;

import junit.framework.TestCase;
import org.example.model.Mentor;
import org.junit.Assert;

import java.util.UUID;

import static org.mockito.Mockito.mock;

public class MentorDAOTest extends TestCase {

    public void testAdd() {
        UUID roleUUID = UUID.fromString("745792a7-681b-4efe-abdd-ca027678b397");
        Mentor testingMentor = new Mentor(UUID.randomUUID(), "testest", "test", "blabla", "bla", roleUUID,
        true, "1234", "mentor", UUID.randomUUID());
        DBConnection dbConnection = new DBConnection();
        MentorDAO mentorDAO = new MentorDAO(dbConnection);
        int expected = mentorDAO.getAll().size()+1;
        mentorDAO.add(testingMentor);
        Assert.assertEquals(expected,mentorDAO.getAll().size());
    }

    public void testRemove() {
    }

    public void testEdit() {
    }

    public void testGetAll() {
    }

    public void testGet() {
    }
}