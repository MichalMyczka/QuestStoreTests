package org.example.DAO;

import junit.framework.TestCase;
import org.example.model.Artifact;
import org.junit.Assert;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ArtifactDAOTest extends TestCase {

    public void testAdd() {
        Artifact dummyArtifact = mock(Artifact.class);
        DBConnection dbConnection = new DBConnection();
        ArtifactDAO artifactDAO = new ArtifactDAO(dbConnection);
        artifactDAO.add(dummyArtifact);
        Assert.assertEquals(11,artifactDAO.getAll().size());
    }

    public void testRemove() {
        Artifact dummy = mock(Artifact.class);
    }

    public void testEdit() {
    }

    public void testGetAll() {
    }

    public void testGet() {
    }

    public void testGetAllStudentsArtifacts() {
    }
}