package org.example.DAO;

import org.example.DAO.Exception.AbsenceOfRecordsException;
import org.example.DAO.helpers.Service;
import org.example.model.Creep;
import org.example.model.Mentor;
import org.example.model.Student;
import org.example.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDAO implements DAO<User> {
    DBConnection dbConnection;

    public UserDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public void add(User user) {
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(
                    "INSERT INTO user_details (id, name, surname, email, password, role_id, is_active, phone_number)" +
                            " VALUES (?, ?, ?, ?, ?, ?, true, ?);");
            preparedStatement.setObject(1, user.getUserDetailsID(), Types.OTHER);
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getSurname());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setObject(6, user.getRoleID(), Types.OTHER);
            preparedStatement.setString(7, user.getPhoneNumber());
            preparedStatement.executeUpdate();
            System.out.println("Added user successfully.");
            switch (user.getRole()) {
                case "student":
                    Student student = (Student) user;
                    PreparedStatement statement = dbConnection.getConnection().prepareStatement(
                            "INSERT INTO students (student_id, user_details_id, coins) VALUES (?, ?, 0);");
                    statement.setObject(1, student.getStudentID(), Types.OTHER);
                    statement.setObject(2, user.getUserDetailsID());
                    statement.executeUpdate();
                    break;
                case "mentor":
                    Mentor mentor = (Mentor) user;
                    PreparedStatement mentorStatement = dbConnection.getConnection().prepareStatement(
                            "INSERT INTO mentors (mentor_id, user_details_id) VALUES (?, ?);");
                    mentorStatement.setObject(1, mentor.getMentorID(), Types.OTHER);
                    mentorStatement.setObject(2, mentor.getUserDetailsID(), Types.OTHER);
                    mentorStatement.executeUpdate();
                    break;
                case "creep":
                    Creep creep = (Creep) user;
                    PreparedStatement creepStatement = dbConnection.getConnection().prepareStatement(
                            "INSERT INTO creeps (creep_id, user_details_id) VALUES (?, ?);");
                    creepStatement.setObject(1, creep.getCreepID(), Types.OTHER);
                    creepStatement.setObject(2, creep.getUserDetailsID(), Types.OTHER);
                    creepStatement.executeUpdate();
                    break;
            }

            Service.closeDBConnection("Added user successfully.", dbConnection);
        } catch (SQLException e) {
            Service.exceptionHandling(e, "Adding user failed.");
        }
    }

    @Override
    public void remove(User user) {

    }

    @Override
    public void edit(User user) {

    }

    @Override
    public List<User> getAll() {
        List<User> allUsers = new ArrayList<>();
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(
                    "SELECT ud.*, r.name AS role FROM user_details ud JOIN roles r ON r.id = ud.role_id " +
                            "ORDER BY ud.surname;");
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                final UUID userDetailsID = UUID.fromString(result.getString("id"));
                final String name = result.getString("name");
                final String surname = result.getString("surname");
                final String email = result.getString("email");
                final String password = result.getString("password");
                final String role = result.getString("role");
                final boolean isActive = result.getBoolean("is_active");
                final String phoneNumber = result.getString("phone_number");
                final UUID roleID = UUID.fromString(result.getString("role_id"));
                User user;
                switch (role) {
                    case "student" :
                        UUID temporarID = null;
                        int coins = 0;
                        PreparedStatement studentStatement = dbConnection.getConnection().prepareStatement(
                                "SELECT coins, student_id FROM students WHERE user_details_id = ?;");
                        studentStatement.setObject(1, userDetailsID, Types.OTHER);
                        ResultSet resultSet = studentStatement.executeQuery();
                        while (resultSet.next()) {
                            coins += resultSet.getInt("coins");
                            temporarID = UUID.fromString(resultSet.getString("student_id"));
                        }
                        allUsers.add(user = new Student(userDetailsID, name, surname, email, password, roleID, isActive,
                                phoneNumber, role, temporarID, coins));
                        break;
                }
            }
            Service.closeDBConnection("Selected students from data base successfully.", dbConnection);
        } catch (SQLException e) {
            Service.exceptionHandling(e, "Selecting students from data base failed.");
        }
        return allUsers;
    }

    @Override
    public User get(UUID id) throws AbsenceOfRecordsException {
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(
                    "SELECT ud.*, r.name AS role FROM user_details ud JOIN roles r ON r.id = ud.role_id " +
                            "WHERE ud.id = ?;");
            preparedStatement.setObject(1, id, Types.OTHER);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                final UUID userDetailsID = UUID.fromString(result.getString("id"));
                final String name = result.getString("name");
                final String surname = result.getString("surname");
                final String email = result.getString("email");
                final String password = result.getString("password");
                final String role = result.getString("role");
                final boolean isActive = result.getBoolean("is_active");
                final String phoneNumber = result.getString("phone_number");
                final UUID roleID = UUID.fromString(result.getString("role_id"));
                User user;
                switch (role) {
                    case "mentor" :
                        UUID tempID = null;
                        PreparedStatement mentorStatement = dbConnection.getConnection().prepareStatement(
                                "SELECT mentor_id FROM mentors WHERE user_details_id = ?;");
                        mentorStatement.setObject(1, id, Types.OTHER);
                        ResultSet mentorSet = mentorStatement.executeQuery();
                        while (mentorSet.next()) {
                            tempID = UUID.fromString(mentorSet.getString("mentor_id"));
                        }
                        user = new Mentor(userDetailsID, name, surname, email, password, roleID, isActive, phoneNumber,
                                role, tempID);
                        break;
                    case "creep" :
                        UUID temporaryID = null;
                        PreparedStatement creepStatement = dbConnection.getConnection().prepareStatement(
                                "SELECT creep_id FROM creeps WHERE user_details_id = ?;");
                        creepStatement.setObject(1, id, Types.OTHER);
                        ResultSet creepSet = creepStatement.executeQuery();
                        while (creepSet.next()) {
                        temporaryID = UUID.fromString(creepSet.getString("creep_id"));
                        }
                        user = new Creep(id, name, surname, email, password, roleID, isActive, phoneNumber,
                                role, temporaryID);
                        break;
                    default :
                        UUID temporarID = null;
                        int coins = 0;
                        PreparedStatement studentStatement = dbConnection.getConnection().prepareStatement(
                                "SELECT coins, student_id FROM students WHERE user_details_id = ?;");
                        studentStatement.setObject(1, id, Types.OTHER);
                        ResultSet resultSet = studentStatement.executeQuery();
                        while (resultSet.next()) {
                            coins += resultSet.getInt("coins");
                            temporarID = UUID.fromString(resultSet.getString("student_id"));
                        }
                        user = new Student(userDetailsID, name, surname, email, password, roleID, isActive,
                                phoneNumber, role, temporarID, coins);
                        break;
                }
                return user;
            }
            Service.closeDBConnection("Selected user from data base successfully.", dbConnection);
        } catch (SQLException e) {
            Service.exceptionHandling(e, "Selecting user from data base failed.");
        }
        throw new AbsenceOfRecordsException();
    }

    public List<User> getAllMentors() {
        List<User> allUsers = new ArrayList<>();
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(
                    "SELECT ud.*, r.name AS role FROM user_details ud JOIN roles r ON r.id = ud.role_id " +
                            "ORDER BY ud.surname;");
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                final UUID userDetailsID = UUID.fromString(result.getString("id"));
                final String name = result.getString("name");
                final String surname = result.getString("surname");
                final String email = result.getString("email");
                final String password = result.getString("password");
                final String role = result.getString("role");
                final boolean isActive = result.getBoolean("is_active");
                final String phoneNumber = result.getString("phone_number");
                final UUID roleID = UUID.fromString(result.getString("role_id"));
                User user;
                switch (role) {
                    case "mentor" :
                        UUID tempID = null;
                        PreparedStatement mentorStatement = dbConnection.getConnection().prepareStatement(
                                "SELECT mentor_id FROM mentors WHERE user_details_id = ?;");
                        mentorStatement.setObject(1, userDetailsID, Types.OTHER);
                        ResultSet mentorSet = mentorStatement.executeQuery();
                        while (mentorSet.next()) {
                            tempID = UUID.fromString(mentorSet.getString("mentor_id"));
                        }
                        allUsers.add(user = new Mentor(userDetailsID, name, surname, email, password, roleID, isActive,
                                phoneNumber, role, tempID));
                        break;
                }

            }
            Service.closeDBConnection("Selected mentors from data base successfully.", dbConnection);
        } catch (SQLException e) {
            Service.exceptionHandling(e, "Selecting mentors from data base failed.");
        }
        return allUsers;
    }

    public List<User> getAllCreeps() {
        List<User> allUsers = new ArrayList<>();
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(
                    "SELECT ud.*, r.name AS role FROM user_details ud JOIN roles r ON r.id = ud.role_id " +
                            "ORDER BY ud.surname;");
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                final UUID userDetailsID = UUID.fromString(result.getString("id"));
                final String name = result.getString("name");
                final String surname = result.getString("surname");
                final String email = result.getString("email");
                final String password = result.getString("password");
                final String role = result.getString("role");
                final boolean isActive = result.getBoolean("is_active");
                final String phoneNumber = result.getString("phone_number");
                final UUID roleID = UUID.fromString(result.getString("role_id"));
                User user;
                switch (role) {
                    case "creep" :
                        UUID temporaryID = null;
                        PreparedStatement creepStatement = dbConnection.getConnection().prepareStatement(
                                "SELECT creep_id FROM creeps WHERE user_details_id = ?;");
                        creepStatement.setObject(1, userDetailsID, Types.OTHER);
                        ResultSet creepSet = creepStatement.executeQuery();
                        while (creepSet.next()) {
                            temporaryID = UUID.fromString(creepSet.getString("creep_id"));
                        }
                        allUsers.add(user = new Creep(userDetailsID, name, surname, email, password, roleID, isActive,
                                phoneNumber, role, temporaryID));
                        break;
                }

            }
            Service.closeDBConnection("Selected creeps from data base successfully.", dbConnection);
        } catch (SQLException e) {
            Service.exceptionHandling(e, "Selecting creeps from data base failed.");
        }
        return allUsers;
    }

}
