package org.example.DAO;

import junit.framework.Assert;
import org.example.model.Creep;
import org.example.model.Student;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CreepDAOTest {

    @Test
    void testIsAddingCreepWorks() {
        UUID roleUUID = UUID.fromString("84c5eca9-e046-42b4-a316-55d3258021ec");
        UUID creepIDUUID = UUID.randomUUID();
        UUID userDetailsUUID = UUID.randomUUID();
        DBConnection dbConnection = new DBConnection();
        Creep testingCreep = new Creep(userDetailsUUID, "creep", "epep", "mail", "haslo", roleUUID, true, "999", "creep",creepIDUUID);
        CreepDAO creepDAO = new CreepDAO(dbConnection);
        int expected = creepDAO.getAll().size()+1;
        creepDAO.add(testingCreep);
        Assert.assertEquals(expected, creepDAO.getAll().size());
    }
//
//    @Test
//    void remove() {
//    }
//
//    @Test
//    void edit() {
//    }
//
//    @Test
//    void getAll() {
//    }
//
//    @Test
//    void get() {
//    }
}