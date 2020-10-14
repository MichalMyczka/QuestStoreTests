package org.example.DAO;

import org.example.model.Artifact;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ArtifactDAOTest {

    @Test
    void add() {
        UUID categoryID = UUID.fromString("d5e59add-a91c-488e-8f7b-061d36b9ec36"); // z tabeli categories
        UUID typeID = UUID.fromString("a623b302-c968-4125-aaab-de6c15dd1a41"); // private z tabeli artifact_types
        UUID artifactID = UUID.randomUUID();
        Artifact testingArtifact = new Artifact(artifactID, "testowyArtefakt", 666, "physical item", "to jest testowy art", "private", categoryID, typeID);
        DBConnection dbConnection = new DBConnection();
        ArtifactDAO artifactDAO = new ArtifactDAO(dbConnection);
        int expected = artifactDAO.getAll().size() +1;
        artifactDAO.add(testingArtifact);
        Assert.assertEquals(expected,artifactDAO.getAll().size());
    }

    @Test
    void remove() {
    }

    @Test
    void edit() {
    }

    @Test
    void getAll() {
    }

    @Test
    void get() {
    }

    @Test
    void getAllStudentsArtifacts() {
    }
}