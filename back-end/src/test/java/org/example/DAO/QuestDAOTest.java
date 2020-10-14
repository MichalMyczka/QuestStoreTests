package org.example.DAO;

import org.example.DAO.Exception.AbsenceOfRecordsException;
import org.example.model.Quest;
import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class QuestDAOTest {

    @Test
    @Order(1)
    void testIsAddQuestWorks() {
        UUID questIDUUID = UUID.fromString("88b8eb42-cd0b-42e3-9da0-1d36f4910aa1");
        Quest testQuest = new Quest(questIDUUID,"testowy","quest",123);
        DBConnection dbConnection = new DBConnection();
        QuestDAO questDAO = new QuestDAO(dbConnection);
        int expected = questDAO.getAll().size()+1;
        questDAO.add(testQuest);
        Assert.assertEquals(expected, questDAO.getAll().size());
    }

    @Test
    @Order(4)
    void testIsRemoveQuestWorks() {
        UUID questIDUUID = UUID.fromString("88b8eb42-cd0b-42e3-9da0-1d36f4910aa1");
        Quest testQuest = new Quest(questIDUUID,"testowy","quest",123);
        DBConnection dbConnection = new DBConnection();
        QuestDAO questDAO = new QuestDAO(dbConnection);
        int expected = questDAO.getAll().size()-1;
        questDAO.remove(testQuest);
        Assert.assertEquals(expected, questDAO.getAll().size());
    }

    @Test
    @Order(3)
    void testIsEditQuestWorks() throws AbsenceOfRecordsException {
        UUID questIDUUID = UUID.fromString("88b8eb42-cd0b-42e3-9da0-1d36f4910aa1");
        Quest testQuest = new Quest(questIDUUID,"zmieniony","papa",123);
        DBConnection dbConnection = new DBConnection();
        QuestDAO questDAO = new QuestDAO(dbConnection);
        questDAO.edit(testQuest);
        Assert.assertEquals(testQuest.getAll(), questDAO.get(questIDUUID).getAll());
    }

    @Test
    @Order(2)
    void testIsGetQuestWorks() throws AbsenceOfRecordsException {
        UUID questIDUUID = UUID.fromString("88b8eb42-cd0b-42e3-9da0-1d36f4910aa1");
        Quest testQuest = new Quest(questIDUUID,"testowy","quest",123);
        DBConnection dbConnection = new DBConnection();
        QuestDAO questDAO = new QuestDAO(dbConnection);
        Assert.assertEquals(testQuest.getAll(), questDAO.get(questIDUUID).getAll());
    }
}