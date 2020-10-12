package org.example.DAO;

import org.example.DAO.Exception.AbsenceOfRecordsException;
import org.example.DAO.helpers.Service;
import org.example.model.SharedArtifactPayment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SharedArtifactPaymentsDAO implements DAO<SharedArtifactPayment> {

    DBConnection dbConnection;

    public SharedArtifactPaymentsDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public void add(SharedArtifactPayment sharedArtifactPayment) {
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(
                    "INSERT INTO shared_artifacts_payments (id, student_id, student_artifacts_id, payment) " +
                            "VALUES(?, ?, ?, ?);");
            preparedStatement.setObject(1, sharedArtifactPayment.getId(), Types.OTHER);
            preparedStatement.setObject(2, sharedArtifactPayment.getStudentID(), Types.OTHER);
            preparedStatement.setObject(3, sharedArtifactPayment.getStudentArtifactID(), Types.OTHER);
            preparedStatement.setInt(4, sharedArtifactPayment.getPayment());
            preparedStatement.executeUpdate();
            Service.closeDBConnection("Artifact added successfully.", dbConnection);
        } catch (SQLException e) {
            Service.exceptionHandling(e,"Adding artifact failed.");
        }
    }

    @Override
    public void remove(SharedArtifactPayment sharedArtifactPayment) {
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(
                    "DELETE FROM shared_artifacts_payments WHERE id = ?;");
            preparedStatement.setObject(1, sharedArtifactPayment.getId(), Types.OTHER);
            preparedStatement.executeUpdate();
            Service.closeDBConnection("Shared artifacts payments removed successfully.", dbConnection);
        } catch (SQLException e) {
            Service.exceptionHandling(e,"Removing shared artifacts payments failed.");
        }
    }

    @Override
    public void edit(SharedArtifactPayment sharedArtifactPayment) {
        try {
        dbConnection.connect();
        PreparedStatement preparedStatement = dbConnection.connect().prepareStatement(
                "UPDATE shared_artifacts_payments SET payment = ? WHERE id = ?;");
        preparedStatement.setInt(1, sharedArtifactPayment.getPayment());
        preparedStatement.setObject(2, sharedArtifactPayment.getId(), Types.OTHER);
        preparedStatement.executeUpdate();
        Service.closeDBConnection("Shared artifacts payments edited successfully.", dbConnection);
        } catch (SQLException e) {
            Service.exceptionHandling(e, "Editing shared artifacts payments failed.");
        }
    }

    @Override
    public List<SharedArtifactPayment> getAll() {
        List<SharedArtifactPayment> sharedArtifactPayments = new ArrayList<>();
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.connect().prepareStatement(
                    "SELECT * FROM shared_artifacts_payments;");
            ResultSet allSharedArtifactPayment = preparedStatement.executeQuery();
            while (allSharedArtifactPayment.next()) {
                sharedArtifactPayments.add(prepareSharedArtifactPayment(allSharedArtifactPayment));
            }
            Service.closeDBConnection("Selected shared artifacts payments from data base successfully.", dbConnection);
        } catch (SQLException e) {
            Service.exceptionHandling(e, "Selecting shared artifacts payments from data base failed.");
        }
        return sharedArtifactPayments;
    }

    @Override
    public SharedArtifactPayment get(UUID id) throws AbsenceOfRecordsException {
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.connect().prepareStatement("SELECT * FROM " +
                    "shared_artifacts_payments WHERE id = ?;");
            preparedStatement.setObject(1, id, Types.OTHER);
            ResultSet allSharedArtifactPayment = preparedStatement.executeQuery();
            while (allSharedArtifactPayment.next()) {
                return prepareSharedArtifactPayment(allSharedArtifactPayment);
            }
            Service.closeDBConnection("Selected shared artifacts payments from data base successfully.", dbConnection);
        } catch (SQLException e) {
            Service.exceptionHandling(e, "Selecting shared artifacts payments from data base failed.");
        }
        throw new AbsenceOfRecordsException();
    }

    private SharedArtifactPayment prepareSharedArtifactPayment(ResultSet allSharedArtifactPayment) throws SQLException {
        final UUID artifactID = UUID.fromString(allSharedArtifactPayment.getString("id"));
        final UUID studentID = UUID.fromString(allSharedArtifactPayment.getString("student_id"));
        final UUID studentArtifactID = UUID.fromString(allSharedArtifactPayment.getString(
                "student_artifact_id"));
        final int payment = allSharedArtifactPayment.getInt("payment");
        SharedArtifactPayment sharedArtifactPayment = new SharedArtifactPayment(
                artifactID, studentID, studentArtifactID, payment);
        return sharedArtifactPayment;
    }

}
