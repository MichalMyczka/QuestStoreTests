package org.example.DAO;

import org.example.DAO.Exception.AbsenceOfRecordsException;
import org.example.model.StudentArtifact;

import java.util.List;
import java.util.UUID;

public class StudentArtifactDAO implements  DAO<StudentArtifact> {

    DBConnection dbConnection;

    public StudentArtifactDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }


    @Override
    public void add(StudentArtifact studentArtifact) {

    }

    @Override
    public void remove(StudentArtifact studentArtifact) {

    }

    @Override
    public void edit(StudentArtifact studentArtifact) {

    }

    @Override
    public List<StudentArtifact> getAll() {
        return null;
    }

    @Override
    public StudentArtifact get(UUID id) throws AbsenceOfRecordsException {
        return null;
    }

}
