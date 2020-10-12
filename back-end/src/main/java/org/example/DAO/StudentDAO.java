package org.example.DAO;

import org.example.DAO.Exception.AbsenceOfRecordsException;
import org.example.model.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

public class StudentDAO /*extends UserDAO*/ implements DAO<Student> {

    DBConnection dbConnection;

    public StudentDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void add(Student student) {
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(
                    "INSERT INTO user_details (id, name, surname, email, password, role_id, is_active, phone_number)" +
                            " VALUES (?, ?, ?, ?, ?, ?, true, ?);");
            preparedStatement.setObject(1, student.getUserDetailsID(), Types.OTHER);
            preparedStatement.setString(2, student.getName());
            preparedStatement.setString(3, student.getSurname());
            preparedStatement.setString(4, student.getEmail());
            preparedStatement.setString(5, student.getPassword());
            preparedStatement.setObject(6, student.getRoleID(), Types.OTHER);
            preparedStatement.setString(7, student.getPhoneNumber());
            preparedStatement.executeUpdate();
            System.out.println("Added user successfully.");
            PreparedStatement statement = dbConnection.getConnection().prepareStatement(
                    "INSERT INTO students (student_id, user_details_id, coins) VALUES (?, ?, 0);");
            statement.setObject(1, student.getStudentID(), Types.OTHER);
            statement.setObject(2, student.getUserDetailsID());
            statement.executeUpdate();
            System.out.println("Added student successfully.");
            dbConnection.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Adding student failed.");
        }
    }

    public void remove(Student student) {
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(
                    "DELETE FROM students WHERE student_id = ?;");
            preparedStatement.setObject(1, student.getStudentID(), Types.OTHER);
            preparedStatement.executeUpdate();
            System.out.println("Removed student successfully.");

            PreparedStatement statement = dbConnection.getConnection().prepareStatement(
                    "DELETE FROM user_details WHERE id = ?;");
            statement.setObject(1, student.getUserDetailsID(), Types.OTHER);
            statement.executeUpdate();
            System.out.println("Removed user successfully.");
            dbConnection.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Removing student failed.");
        }
    }

    public void edit(Student student) {
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(
                    "UPDATE students SET coins = ? WHERE student_id = ?;");
            preparedStatement.setInt(1, student.getCoins());
            preparedStatement.setObject(2, student.getStudentID());
            preparedStatement.executeUpdate();
            System.out.println("Students data edited successfully.");

            PreparedStatement statement = dbConnection.getConnection().prepareStatement(
                    "UPDATE user_details SET " +
                            "name = ?, surname = ?, email = ?, password = ?, role_id = ?, is_active = ? WHERE id = ?;");
            statement.setString(1, student.getName());
            statement.setString(2, student.getSurname());
            statement.setString(3, student.getEmail());
            statement.setString(4, student.getPassword());
            statement.setObject(5, student.getRoleID(), Types.OTHER);
            statement.setBoolean(6, student.isActive());
            statement.setObject(7, student.getUserDetailsID(), Types.OTHER);
            statement.executeUpdate();
            System.out.println("User data edited successfully.");
            dbConnection.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Editing student failed.");
        }
    }

    public List<Student> getAll() {
        List<Student> students = new ArrayList<>();
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(
                    "SELECT ud.*, r.name AS role , s.student_id, s.coins FROM user_details ud JOIN roles r " +
                            "ON r.id = ud.role_id JOIN students s ON s.user_details_id = ud.id ORDER BY surname;");
            ResultSet allStudents = preparedStatement.executeQuery();
            while (allStudents.next()) {
                students.add(preparedStudent(allStudents));
            }
            dbConnection.disconnect();
            System.out.println("Selected students from data base successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Selecting students from data base failed.");
        }
        return students;
    }

    @Override
    public Student get(UUID id) throws AbsenceOfRecordsException {
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(
                    "SELECT ud.*, r.name AS role , s.student_id, s.coins FROM user_details ud JOIN roles r " +
                            "ON r.id = ud.role_id JOIN students s ON s.user_details_id = ud.id " +
                            "WHERE ud.id = ?;");
            preparedStatement.setObject(1, id, Types.OTHER);
            ResultSet allStudents = preparedStatement.executeQuery();
            while (allStudents.next()) {
               return preparedStudent(allStudents);
            }
            dbConnection.disconnect();
            System.out.println("Selected student from data base successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Selecting student from data base failed.");
        }
        throw new AbsenceOfRecordsException();
    }

    private Student preparedStudent(ResultSet allStudents) throws SQLException {
        final UUID userDetailsID = UUID.fromString(allStudents.getString("id"));
        final String name = allStudents.getString("name");
        final String surname = allStudents.getString("surname");
        final String email = allStudents.getString("email");
        final String password = allStudents.getString("password");
        final String role = allStudents.getString("role");
        final UUID studentID = UUID.fromString(allStudents.getString("student_id"));
        final boolean isActive = allStudents.getBoolean("is_active");
        final  String phoneNumber = allStudents.getString("phone_number");
        final int coins = allStudents.getInt("coins");
        final UUID roleID = UUID.fromString(allStudents.getString("role_id"));
        Student student = new Student(userDetailsID, name, surname, email, password, roleID, isActive,
                phoneNumber, role, studentID, coins);
        return student;
    }

}
