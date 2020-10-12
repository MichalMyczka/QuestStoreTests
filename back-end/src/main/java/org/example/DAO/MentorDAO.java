package org.example.DAO;

import org.example.DAO.Exception.AbsenceOfRecordsException;
import org.example.model.Mentor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MentorDAO implements DAO<Mentor> {

    DBConnection dbConnection;

    public MentorDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public void add(Mentor mentor) {
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(
                    "INSERT INTO user_details (id, name, surname, email, password, role_id, is_active, phone_number)" +
                            " VALUES (?, ?, ?, ?, ?, ?, true, ?);");
            preparedStatement.setObject(1, mentor.getUserDetailsID(), Types.OTHER);
            preparedStatement.setString(2, mentor.getName());
            preparedStatement.setString(3, mentor.getSurname());
            preparedStatement.setString(4, mentor.getEmail());
            preparedStatement.setString(5, mentor.getPassword());
            preparedStatement.setObject(6, mentor.getRoleID(), Types.OTHER);
            preparedStatement.setString(7, mentor.getPhoneNumber());
            preparedStatement.executeUpdate();
            System.out.println("Added user successfully.");

            PreparedStatement statement = dbConnection.getConnection().prepareStatement(
                    "INSERT INTO mentors (mentor_id, user_details_id) VALUES (?, ?);");
            statement.setObject(1, mentor.getMentorID(), Types.OTHER);
            statement.setObject(2, mentor.getUserDetailsID(), Types.OTHER);
            statement.executeUpdate();
            System.out.println("Added mentor successfully.");
            dbConnection.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Adding mentor failed.");
        }
    }

    @Override
    public void remove(Mentor mentor) {
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(
                    "DELETE FROM mentors WHERE mentor_id = ?;");
            preparedStatement.setObject(1, mentor.getMentorID(), Types.OTHER);
            preparedStatement.executeUpdate();
            System.out.println("Removed mentor successfully.");

            PreparedStatement statement = dbConnection.getConnection().prepareStatement(
                    "DELETE FROM user_details WHERE id = ?;");
            statement.setObject(1, mentor.getUserDetailsID(), Types.OTHER);
            statement.executeUpdate();
            System.out.println("Removed user successfully.");
            dbConnection.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Removing mentor failed.");
        }
    }

    @Override
    public void edit(Mentor mentor) {
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(
                    "UPDATE user_details SET name = ?, surname = ?, email = ?, password = ?, is_active = ?, " +
                            "phone_number = ? WHERE id = ?;");
            preparedStatement.setString(1, mentor.getName());
            preparedStatement.setString(2, mentor.getSurname());
            preparedStatement.setString(3, mentor.getEmail());
            preparedStatement.setString(4, mentor.getPassword());
            preparedStatement.setBoolean(5, mentor.isActive());
            preparedStatement.setString(6, mentor.getPhoneNumber());
            preparedStatement.setObject(7, mentor.getUserDetailsID(), Types.OTHER);
            System.out.println("Mentors data edited successfully.");
            preparedStatement.executeUpdate();
            dbConnection.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Editing mentor failed.");
        }
    }

    @Override
    public List<Mentor> getAll() {
        List<Mentor> mentors = new ArrayList<>();
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(
                    "SELECT ud.*, r.name AS role , m.mentor_id FROM user_details ud JOIN roles r " +
                            "ON r.id = ud.role_id JOIN mentors m ON m.user_details_id = ud.id ORDER BY surname;");
            ResultSet allMentors = preparedStatement.executeQuery();
            while (allMentors.next()) {
                final UUID userDetailsID = UUID.fromString(allMentors.getString("id"));
                final String name = allMentors.getString("name");
                final String surname = allMentors.getString("surname");
                final String email = allMentors.getString("email");
                final String password = allMentors.getString("password");
                final UUID roleID = UUID.fromString(allMentors.getString("role_id"));
                final UUID mentorID = UUID.fromString(allMentors.getString("mentor_id"));
                final boolean isActive = allMentors.getBoolean("is_active");
                final  String phoneNumber = allMentors.getString("phone_number");
                final String role = allMentors.getString("role");
                Mentor mentor = new Mentor(userDetailsID, name, surname, email, password, roleID, isActive, phoneNumber,
                        role, mentorID);
                mentors.add(mentor);
            }
            dbConnection.disconnect();
            System.out.println("Selected mentors from data base successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Selecting mentors from data base failed.");
        }
        return mentors;
    }

    @Override
    public Mentor get(UUID id) throws AbsenceOfRecordsException {
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(
                    "SELECT ud.*, r.name AS role , m.mentor_id FROM user_details ud JOIN roles r " +
                            "ON r.id = ud.role_id JOIN mentors m ON m.user_details_id = ud.id WHERE ud.id = ?;");
            preparedStatement.setObject(1, id, Types.OTHER);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                final String name = result.getString("name");
                final String surname = result.getString("surname");
                final String email = result.getString("email");
                final String password = result.getString("password");
                final UUID roleID = UUID.fromString(result.getString("role_id"));
                final UUID mentorID = UUID.fromString(result.getString("mentor_id"));
                final boolean isActive = result.getBoolean("is_active");
                final  String phoneNumber = result.getString("phone_number");
                final String role = result.getString("role");
                Mentor mentor = new Mentor(id, name, surname, email, password, roleID, isActive, phoneNumber,
                        role, mentorID);
                return mentor;
            }
            dbConnection.disconnect();
            System.out.println("Selected mentor from data base successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Selecting mentor from data base failed.");
        }
        throw new AbsenceOfRecordsException();
    }

}
