package org.example.DAO;

import junit.framework.Assert;
import org.example.DAO.Exception.AbsenceOfRecordsException;
import org.example.model.Student;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentDAOTest {

    @Test
    @Order(1)
    void testIsAddingStudentWorks() {
        DBConnection dbConnection = new DBConnection();
        StudentDAO studentDAO = new StudentDAO(dbConnection);
        UUID userDetailsUUID = UUID.fromString("cc38bce6-fa60-4360-99df-634147cc0084");
        UUID studentIDUUID = UUID.fromString("065074cb-605f-4da0-88d6-37e7ae770388");
        UUID roleUUID = UUID.fromString("40d46809-4398-4d69-8247-3f381fc4c504");
        Student testingStudent = new Student(userDetailsUUID, "bla", "blabla", "blip", "blipblop",roleUUID,true,"432","student",studentIDUUID, 532 );
        int expected = studentDAO.getAll().size()+1;
        studentDAO.add(testingStudent);
        Assert.assertEquals(expected,studentDAO.getAll().size());
    }

    @Test
    @Order(2)
    void testIsGetStudentWorks() throws AbsenceOfRecordsException {
        DBConnection dbConnection = new DBConnection();
        StudentDAO studentDAO = new StudentDAO(dbConnection);
        UUID userDetailsUUID = UUID.fromString("cc38bce6-fa60-4360-99df-634147cc0084");
        UUID studentIDUUID = UUID.fromString("065074cb-605f-4da0-88d6-37e7ae770388");
        UUID roleUUID = UUID.fromString("40d46809-4398-4d69-8247-3f381fc4c504");
        Student testingStudent = new Student(userDetailsUUID, "bla", "blabla", "blip", "blipblop",roleUUID,true,"432","student",studentIDUUID, 532 );
        Assert.assertEquals(testingStudent.getAll(), studentDAO.get(userDetailsUUID).getAll());
    }

    @Test
    @Order(3)
    void testIsEditStudentWorks() throws AbsenceOfRecordsException {
        DBConnection dbConnection = new DBConnection();
        StudentDAO studentDAO = new StudentDAO(dbConnection);
        UUID userDetailsUUID = UUID.fromString("cc38bce6-fa60-4360-99df-634147cc0084");
        UUID studentIDUUID = UUID.fromString("065074cb-605f-4da0-88d6-37e7ae770388");
        UUID roleUUID = UUID.fromString("40d46809-4398-4d69-8247-3f381fc4c504");
        Student testingStudent = new Student(userDetailsUUID, "test", "testest", "lolo", "papa",roleUUID,true,"432","student",studentIDUUID, 333 );
        studentDAO.edit(testingStudent);
        Assert.assertEquals(testingStudent.getAll(),studentDAO.get(userDetailsUUID).getAll());
    }

    @Test
    @Order(4)
    void testIsRemovingStudentWorks() {
        DBConnection dbConnection = new DBConnection();
        StudentDAO studentDAO = new StudentDAO(dbConnection);
        UUID userDetailsUUID = UUID.fromString("cc38bce6-fa60-4360-99df-634147cc0084");
        UUID studentIDUUID = UUID.fromString("065074cb-605f-4da0-88d6-37e7ae770388");
        UUID roleUUID = UUID.fromString("40d46809-4398-4d69-8247-3f381fc4c504");
        Student testingStudent = new Student(userDetailsUUID, "bla", "blabla", "blip", "blipblop",roleUUID,true,"432","student",studentIDUUID, 532 );
        int expected = studentDAO.getAll().size()-1;
        studentDAO.remove(testingStudent);
        Assert.assertEquals(expected,studentDAO.getAll().size());
    }

}