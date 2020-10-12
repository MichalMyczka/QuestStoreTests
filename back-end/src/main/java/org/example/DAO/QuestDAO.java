package org.example.DAO;

import org.example.DAO.Exception.AbsenceOfRecordsException;
import org.example.DAO.helpers.Service;
import org.example.model.Quest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class QuestDAO implements DAO<Quest> {

    DBConnection dbConnection;

    public QuestDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public void add(Quest quest) {
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(
                    "INSERT INTO (id, name, description, value) VALUES (?, ?, ?, ?);");
            preparedStatement.setObject(1, quest.getId(), Types.OTHER);
            preparedStatement.setString(2, quest.getName());
            preparedStatement.setString(3, quest.getDescription());
            preparedStatement.setInt(4, quest.getValue());
            preparedStatement.executeUpdate();
            Service.closeDBConnection("Quest added successfully.", dbConnection);
        } catch (SQLException e) {
            Service.exceptionHandling(e,"Adding quest failed.");
        }
    }

    @Override
    public void remove(Quest quest) {
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(
                    "DELETE FROM quests WHERE id = ?;");
            preparedStatement.setObject(1, quest.getId(), Types.OTHER);
            preparedStatement.executeUpdate();
            Service.closeDBConnection("Quest removed successfully.", dbConnection);
        } catch (SQLException e) {
            Service.exceptionHandling(e, "Removing quest failed.");
        }
    }

    @Override
    public void edit(Quest quest) {
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(
                    "UPDATE quests SET name = ?, description = ?, value = ? WHERE id = ?;");
            preparedStatement.setString(1, quest.getName());
            preparedStatement.setString(2, quest.getDescription());
            preparedStatement.setInt(3, quest.getValue());
            preparedStatement.setObject(4, quest.getId(), Types.OTHER);
            preparedStatement.executeUpdate();
            Service.closeDBConnection("Quest edited successfully.", dbConnection);
        } catch (SQLException e) {
            Service.exceptionHandling(e,"Editing quest failed.");
        }
    }

    @Override
    public List<Quest> getAll()  {
        List<Quest> quests = new ArrayList<>();
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.connect().prepareStatement(
                    "SELECT * FROM quests ORDER BY name;");
            ResultSet allQuests = preparedStatement.executeQuery();
            while (allQuests.next()) {
                quests.add(prepareQuest(allQuests));
            }
            Service.closeDBConnection("Selected quests from data base successfully.", dbConnection);
        } catch (SQLException e) {
            Service.exceptionHandling(e,"Selecting quests from data base failed.");
        }
        return quests;
    }

    @Override
    public Quest get(UUID id) throws AbsenceOfRecordsException {
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.connect().prepareStatement(
                    "SELECT * FROM quests WHERE id = ?;");
            preparedStatement.setObject(1, id, Types.OTHER);
            ResultSet allQuests = preparedStatement.executeQuery();
            while (allQuests.next()) {
                return prepareQuest(allQuests);
            }
            Service.closeDBConnection("Selected quest from data base successfully.", dbConnection);
        } catch (SQLException e) {
            Service.exceptionHandling(e,"Selecting quest from data base failed.");
        }
        throw new AbsenceOfRecordsException();
    }

    public List<Quest> getAllStudentQuests(UUID id)  {
        List<Quest> quests = new ArrayList<>();
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.connect().prepareStatement(
                    "SELECT name, description, value FROM quests, students " +
                            "WHERE students.user_details_id = ? ORDER BY name;;");
            preparedStatement.setObject(1, id, Types.OTHER);
            ResultSet allQuests = preparedStatement.executeQuery();
            while (allQuests.next()) {
                quests.add(prepareQuest(allQuests));
            }
            Service.closeDBConnection("Selected students quests from data base successfully.", dbConnection);
        } catch (SQLException e) {
            Service.exceptionHandling(e,"Selecting students quests from data base failed.");;
        }
        return quests;
    }

    private Quest prepareQuest(ResultSet allQuests) throws SQLException {
        final UUID questID = UUID.fromString(allQuests.getString("id"));
        final String name = allQuests.getString("name");
        final String description = allQuests.getString("description");
        final int value = allQuests.getInt("value");
        Quest quest = new Quest(questID, name, description, value);
        return quest;
    }

}
