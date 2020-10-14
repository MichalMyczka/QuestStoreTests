package org.example.DAO;

import org.example.DAO.Exception.AbsenceOfRecordsException;
import org.example.model.Mentor;
import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MentorDAOTest {

    @Test
    @Order(1)
    void testIsAddingMentorWorks() {
        UUID roleUUID = UUID.fromString("745792a7-681b-4efe-abdd-ca027678b397");
        UUID userDetailsUUID = UUID.fromString("4a7e91bf-76fb-479b-87ed-64bc8a4e93c9");
        UUID mentorIDUUID = UUID.fromString("6c89e48f-6ba9-4294-b53a-a4e893781fb6");
        Mentor testingMentor = new Mentor(userDetailsUUID, "testest", "test", "blabla", "bla", roleUUID,
                true, "1234", "mentor", mentorIDUUID);
        DBConnection dbConnection = new DBConnection();
        MentorDAO mentorDAO = new MentorDAO(dbConnection);
        int expected = mentorDAO.getAll().size()+1;
        mentorDAO.add(testingMentor);
        Assert.assertEquals(expected,mentorDAO.getAll().size());
    }

    @Test
    @Order(4)
    void testIsRemovingMentorWorks() {
        DBConnection dbConnection = new DBConnection();
        UUID roleUUID = UUID.fromString("745792a7-681b-4efe-abdd-ca027678b397");
        UUID userDetailsUUID = UUID.fromString("4a7e91bf-76fb-479b-87ed-64bc8a4e93c9");
        UUID mentorIDUUID = UUID.fromString("6c89e48f-6ba9-4294-b53a-a4e893781fb6");
        MentorDAO mentorDAO = new MentorDAO(dbConnection);
        Mentor testingMentor = new Mentor(userDetailsUUID, "testest", "test", "blabla", "bla", roleUUID,
                true, "1234", "mentor", mentorIDUUID);
        int expected = mentorDAO.getAll().size()-1;
        mentorDAO.remove(testingMentor);
        Assert.assertEquals(expected,mentorDAO.getAll().size());
    }

    @Test
    @Order(3)
    void testIsEditMentorWorks() throws AbsenceOfRecordsException {
        DBConnection dbConnection = new DBConnection();
        UUID roleUUID = UUID.fromString("745792a7-681b-4efe-abdd-ca027678b397");
        UUID userDetailsUUID = UUID.fromString("4a7e91bf-76fb-479b-87ed-64bc8a4e93c9");
        UUID mentorIDUUID = UUID.fromString("6c89e48f-6ba9-4294-b53a-a4e893781fb6");
        MentorDAO mentorDAO = new MentorDAO(dbConnection);
        Mentor testingMentor = new Mentor(userDetailsUUID, "yyyy", "aaaa", "ccc", "dddd", roleUUID,
                true, "1234", "mentor", mentorIDUUID);
        mentorDAO.edit(testingMentor);
        Assert.assertEquals(testingMentor.getAll(), mentorDAO.get(userDetailsUUID).getAll());
    }

    @Test
    @Order(2)
    void testIsGetMentorWorks() throws AbsenceOfRecordsException {
        DBConnection dbConnection = new DBConnection();
        MentorDAO mentorDAO = new MentorDAO(dbConnection);
        UUID roleUUID = UUID.fromString("745792a7-681b-4efe-abdd-ca027678b397");
        UUID userDetailsUUID = UUID.fromString("4a7e91bf-76fb-479b-87ed-64bc8a4e93c9");
        UUID mentorIDUUID = UUID.fromString("6c89e48f-6ba9-4294-b53a-a4e893781fb6");
        Mentor testingMentor = new Mentor(userDetailsUUID, "testest", "test", "blabla", "bla", roleUUID,
                true, "1234", "mentor", mentorIDUUID);
        Assert.assertEquals(testingMentor.getAll(), mentorDAO.get(userDetailsUUID).getAll());
    }
}