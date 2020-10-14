package org.example.DAO;

import org.example.DAO.Exception.AbsenceOfRecordsException;
import org.example.model.Class;
import org.example.model.Quest;
import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClassDAOTest {

    @Test
    @Order(1)
    void testIsAddingClassWorks() {
        DBConnection dbConnection = new DBConnection();
        ClassDAO classDAO = new ClassDAO(dbConnection);
        UUID classUUID = UUID.fromString("7847d588-0ddb-4d4f-bde4-8376103b00fa");
        Class testClass = new Class(classUUID, "testclass");
        int expected = classDAO.getAll().size()+1;
        classDAO.add(testClass);
        Assert.assertEquals(expected, classDAO.getAll().size());
    }

    @Test
    @Order(2)
    void testIsGettingClassWorks() throws AbsenceOfRecordsException {
        DBConnection dbConnection = new DBConnection();
        ClassDAO classDAO = new ClassDAO(dbConnection);
        UUID classUUID = UUID.fromString("7847d588-0ddb-4d4f-bde4-8376103b00fa");
        Class testClass = new Class(classUUID, "testclass");
        Assert.assertEquals(testClass.getAll(), classDAO.get(classUUID).getAll());
    }

    @Test
    @Order(3)
    void testIsEditClassWorks() throws AbsenceOfRecordsException {
        DBConnection dbConnection = new DBConnection();
        ClassDAO classDAO = new ClassDAO(dbConnection);
        UUID classUUID = UUID.fromString("7847d588-0ddb-4d4f-bde4-8376103b00fa");
        Class testClass = new Class(classUUID, "po zmianie");
        classDAO.edit(testClass);
        Assert.assertEquals(testClass.getAll(), classDAO.get(classUUID).getAll());
    }

    @Test
    @Order(4)
    void testIsRemovingClassWorks() {
        DBConnection dbConnection = new DBConnection();
        ClassDAO classDAO = new ClassDAO(dbConnection);
        UUID classUUID = UUID.fromString("7847d588-0ddb-4d4f-bde4-8376103b00fa");
        Class testClass = new Class(classUUID, "testclass");
        int expected = classDAO.getAll().size()-1;
        classDAO.remove(testClass);
        Assert.assertEquals(expected, classDAO.getAll().size());
    }

}