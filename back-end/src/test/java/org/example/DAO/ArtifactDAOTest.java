package org.example.DAO;

import org.example.DAO.Exception.AbsenceOfRecordsException;
import org.example.model.Artifact;
import org.junit.Assert;
import org.junit.jupiter.api.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ArtifactDAOTest {

    @Test
    @Order(1)
    void add() throws AbsenceOfRecordsException {
        UUID categoryID = UUID.fromString("d5e59add-a91c-488e-8f7b-061d36b9ec36"); // z tabeli categories
        UUID typeID = UUID.fromString("a623b302-c968-4125-aaab-de6c15dd1a41"); // private z tabeli artifact_types
        UUID artifactID = UUID.fromString("2bb2bdd1-3e3a-49f7-afc4-98434282b110");
        Artifact testingArtifact = new Artifact(artifactID, "testowyArtefakt", 666, "physical item", "to jest testowy art", "private", categoryID, typeID);
        DBConnection dbConnection = new DBConnection();
        ArtifactDAO artifactDAO = new ArtifactDAO(dbConnection);
        artifactDAO.add(testingArtifact);
        Assert.assertEquals(testingArtifact.getAll(),artifactDAO.get(artifactID).getAll());
    }

    @Test
    @Order(4)
    void remove() throws AbsenceOfRecordsException {
        DBConnection dbConnection = new DBConnection();
        ArtifactDAO artifactDAO = new ArtifactDAO(dbConnection);
        UUID categoryID = UUID.fromString("d5e59add-a91c-488e-8f7b-061d36b9ec36"); // z tabeli categories
        UUID typeID = UUID.fromString("a623b302-c968-4125-aaab-de6c15dd1a41"); // private z tabeli artifact_types
        UUID artifactID = UUID.fromString("2bb2bdd1-3e3a-49f7-afc4-98434282b110");
        Artifact testingArtifact = new Artifact(artifactID, "testowyArtefakt", 666, "physical item", "to jest testowy art", "private", categoryID, typeID);
        artifactDAO.remove(testingArtifact);
        Assertions.assertThrows(AbsenceOfRecordsException.class, () ->{ artifactDAO.get(artifactID).getAll();});
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