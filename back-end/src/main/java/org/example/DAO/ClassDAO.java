package org.example.DAO;

import org.example.DAO.Exception.AbsenceOfRecordsException;
import org.example.DAO.helpers.Service;
import org.example.model.Class;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClassDAO implements DAO<Class>{

    DBConnection dbConnection;

    public ClassDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public void add(Class claass) {
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(
                    "INSERT INTO classes (id, name) VALUES (?, ?);");
            preparedStatement.setObject(1, claass.getId(), Types.OTHER);
            preparedStatement.setString(2, claass.getName());
            preparedStatement.executeUpdate();
            Service.closeDBConnection("Class added successfully.", dbConnection);
        } catch (SQLException e) {
            Service.exceptionHandling(e, "Adding class failed.");
        }
    }

    @Override
    public void remove(Class claass) {
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(
                    "DELETE FROM classes WHERE id = ?;");
            preparedStatement.setObject(1, claass.getId(), Types.OTHER);
            preparedStatement.executeUpdate();
            Service.closeDBConnection("Class removed successfully.", dbConnection);
        } catch (SQLException e) {
            Service.exceptionHandling(e, "Removing class failed.");
        }
    }

    @Override
    public void edit(Class claass) {
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(
                    "UPDATE classes SET id = ?, name = ? WHERE id = ?;");
            preparedStatement.setObject(1, claass.getId(), Types.OTHER);
            preparedStatement.setString(2, claass.getName());
            preparedStatement.setObject(3, claass.getId());
            preparedStatement.executeUpdate();
            Service.closeDBConnection("Class edited successfully.", dbConnection);
        } catch (SQLException e) {
            Service.exceptionHandling(e, "Editing class failed.");
        }
    }

    @Override
    public List<Class> getAll() {
        List<Class> classes = new ArrayList<>();
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.connect().prepareStatement(
                    "SELECT * FROM classes ORDER BY name;");
            ResultSet classesSet = preparedStatement.executeQuery();
            while (classesSet.next()) {
                classes.add(prepareClass(classesSet));
            }
            Service.closeDBConnection("Selected classes from data base successfully.", dbConnection);
        } catch (SQLException e) {
            Service.exceptionHandling(e, "Selecting classes from data base failed.");
        }
        return classes;
    }

    @Override
    public Class get(UUID id) throws AbsenceOfRecordsException {
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.connect().prepareStatement(
                    "SELECT * FROM classes WHERE id = ?;");
            preparedStatement.setObject(1, id, Types.OTHER);
            ResultSet classesSet = preparedStatement.executeQuery();
            while (classesSet.next()) {
                return prepareClass(classesSet);
            }
            Service.closeDBConnection("Selected class from data base successfully.", dbConnection);
        } catch (SQLException e) {
            Service.exceptionHandling(e, "Selecting class from data base failed.");
        }
        throw new AbsenceOfRecordsException();
    }

    public List<Class> getAllStudentClasses(UUID id) {
        /*Getting all classes assigned to specific student by his id*/
        List<Class> classes = new ArrayList<>();
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.connect().prepareStatement(
                    " SELECT id, name FROM classes, students_classes " +
                            "WHERE classes.id = students_classes.classes_id " +
                            "AND students_classes.student_id= ? ORDER BY name;");
            preparedStatement.setObject(1, id, Types.OTHER);
            ResultSet classesSet = preparedStatement.executeQuery();
            while (classesSet.next()) {
                classes.add(prepareClass(classesSet));
            }
            Service.closeDBConnection("Selected classes from data base successfully.", dbConnection);
        } catch (SQLException e) {
            Service.exceptionHandling(e, "Selecting classes from data base failed.");
        }
        return classes;
    }

    public List<Class> getAllMentorClasses(UUID id) {
        /*Getting all classes assigned to specific mentor by his id*/
        List<Class> classes = new ArrayList<>();
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.connect().prepareStatement(
                    " SELECT id, name FROM classes, mentors_classes " +
                            "WHERE classes.id = mentors_classes.classes_id " +
                            "AND mentors_classes.mentor_id= ? ORDER BY name;");
            preparedStatement.setObject(1, id, Types.OTHER);
            ResultSet classesSet = preparedStatement.executeQuery();
            while (classesSet.next()) {
                classes.add(prepareClass(classesSet));
            }
            Service.closeDBConnection("Selected classes from data base successfully.", dbConnection);
        } catch (SQLException e) {
            Service.exceptionHandling(e, "Selecting classes from data base failed.");
        }
        return classes;
    }

    private Class prepareClass(ResultSet classesSet) throws SQLException {
        final UUID classID = UUID.fromString(classesSet.getString("id"));
        final String name = classesSet.getString("name");
        Class claass = new Class(classID, name);
        return claass;
    }

}