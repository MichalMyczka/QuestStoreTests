package org.example.DAO;

import org.example.DAO.Exception.AbsenceOfRecordsException;
import org.example.DAO.helpers.Service;
import org.example.model.Artifact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

public class ArtifactDAO implements DAO<Artifact>{

    DBConnection dbConnection;

    public ArtifactDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public void add(Artifact artifact) {
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(
                    "INSERT INTO artifacts (id, name, price, category_id, description, artifact_type_id) " +
                            "VALUES(?, ?, ?, ?, ?, ?);");
            preparedStatement.setObject(1, artifact.getId(), Types.OTHER);
            preparedStatement.setString(2, artifact.getName());
            preparedStatement.setInt(3, artifact.getPrice());
            preparedStatement.setObject(4, artifact.getCategoryID(), Types.OTHER);
            preparedStatement.setString(5, artifact.getDescription());
            preparedStatement.setObject(6, artifact.getTypeID(), Types.OTHER);
            preparedStatement.executeUpdate();
            Service.closeDBConnection("Artifact added successfully.", dbConnection);
        } catch (SQLException e) {
            Service.exceptionHandling(e, "Adding artifact failed.");
        }
    }

    @Override
    public void remove(Artifact artifact) {
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(
                    "DELETE FROM artifacts WHERE id = ?;");
            preparedStatement.setObject(1, artifact.getId(), Types.OTHER);
            preparedStatement.executeUpdate();
            Service.closeDBConnection("Artifact removed successfully.", dbConnection);
        } catch (SQLException e) {
            Service.exceptionHandling(e, "Removing artifact failed.");
        }
    }

    @Override
    public void edit(Artifact artifact) {
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(
                    "UPDATE artifacts SET name = ?, price = ?, description = ? WHERE id = ?;");
            preparedStatement.setString(1, artifact.getName());
            preparedStatement.setInt(2, artifact.getPrice());
            preparedStatement.setString(3, artifact.getDescription());
            preparedStatement.setObject(4, artifact.getId(), Types.OTHER);
            preparedStatement.executeUpdate();
            Service.closeDBConnection("Artifact edited successfully.", dbConnection);
        } catch (SQLException e) {
            Service.exceptionHandling(e, "Editing artifact failed.");
        }
    }

    @Override
    public List<Artifact> getAll() {
        List<Artifact> artifacts = new ArrayList<>();
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.connect().prepareStatement(
                    "SELECT a.*, at.name, c.name FROM artifacts a LEFT JOIN artifact_types at " +
                            "ON a.artifact_type_id=at.id LEFT JOIN categories c ON a.category_id=c.id " +
                            "ORDER BY a.name;");
            ResultSet allArtifacts = preparedStatement.executeQuery();
            while (allArtifacts.next()) {
                artifacts.add(prepareArtifact(allArtifacts));
            }
            Service.closeDBConnection("Selected artifacts from data base successfully.", dbConnection);
        } catch (SQLException e) {
            Service.exceptionHandling(e, "Selecting artifacts from data base failed.");
        }
        return artifacts;
    }

    @Override
    public Artifact get(UUID id) throws AbsenceOfRecordsException {
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.connect().prepareStatement(
                    "SELECT artifacts.id, artifacts.name, price, categories.name AS category," +
                            " description, artifact_types.name AS type, categories.id AS category_id," +
                            " artifact_types.id AS type_id FROM artifacts, categories, artifact_types " +
                            "WHERE artifacts.category_id = categories.id AND artifacts.artifact_type_id = artifact_types.id " +
                            "AND artifacts.id = ?;");
            preparedStatement.setObject(1, id, Types.OTHER);
            ResultSet allArtifacts = preparedStatement.executeQuery();
            while (allArtifacts.next()) {
                return prepareArtifact(allArtifacts);
            }
            Service.closeDBConnection("Selected artifact from data base successfully.", dbConnection);
        } catch (SQLException e) {
            Service.exceptionHandling(e, "Selecting artifact from data base failed.");
        }
        throw new AbsenceOfRecordsException();
    }

    public List<Artifact> getAllStudentsArtifacts(UUID studentID) {
        List<Artifact> artifacts = new ArrayList<>();
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.connect().prepareStatement(
                    "SELECT a.*, at.name AS type, at.id AS type_id, c.name AS category, sa.id AS student_a_id, sa.status, " +
                            "sa.student_id, ud.id, s.student_id as s_studentID, s.user_details_id " +
                            "FROM student_artifacts sa, artifacts a, categories c, artifact_types at, user_details ud, " +
                            "students s WHERE a.category_id=c.id AND a.artifact_type_id=at.id AND sa.artifact_id=a.id " +
                            "AND sa.student_id=s.student_id AND s.user_details_id=? AND s.user_details_id=ud.id " +
                            "ORDER BY a.name;");
            preparedStatement.setObject(1, studentID, Types.OTHER);
            ResultSet allArtifacts = preparedStatement.executeQuery();
            while (allArtifacts.next()) {
                artifacts.add(prepareArtifact(allArtifacts));
            }
            Service.closeDBConnection("Selected artifacts from data base successfully.", dbConnection);
        } catch (SQLException e) {
            Service.exceptionHandling(e, "Selecting artifacts from data base failed.");
        }
        return artifacts;
    }

    private Artifact prepareArtifact(ResultSet allArtifacts) throws SQLException {
        final UUID id = UUID.fromString(allArtifacts.getString("id"));
        final String name = allArtifacts.getString("name");
        final int price = allArtifacts.getInt("price");
        final String category = allArtifacts.getString("category");
        final String description = allArtifacts.getString("description");
        final String type = allArtifacts.getString("type");
        final UUID categoryID = UUID.fromString(allArtifacts.getString("category_id"));
        final UUID typeID = UUID.fromString(allArtifacts.getString("type_id"));
        return new Artifact(id, name, price, category, description, type, categoryID, typeID);
    }

}