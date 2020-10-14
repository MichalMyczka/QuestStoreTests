package org.example.DAO;

import junit.framework.Assert;
import org.example.DAO.Exception.AbsenceOfRecordsException;
import org.example.model.Creep;
import org.example.model.Student;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CreepDAOTest {

    @Test
    @Order(1)
    void testIsAddingCreepWorks() {
        UUID roleUUID = UUID.fromString("84c5eca9-e046-42b4-a316-55d3258021ec");
        UUID creepIDUUID = UUID.fromString("fb470bd5-c2c4-482c-a85c-abd71e0f937f");
        UUID userDetailsUUID = UUID.fromString("04717e18-6251-4393-a665-bc7cb61e0c60");
        DBConnection dbConnection = new DBConnection();
        Creep testingCreep = new Creep(userDetailsUUID, "creep", "epep", "mail", "haslo", roleUUID, true, "999", "creep",creepIDUUID);
        CreepDAO creepDAO = new CreepDAO(dbConnection);
        int expected = creepDAO.getAll().size()+1;
        creepDAO.add(testingCreep);
        Assert.assertEquals(expected, creepDAO.getAll().size());
    }

    @Test
    @Order(2)
    void get() throws AbsenceOfRecordsException {
        UUID roleUUID = UUID.fromString("84c5eca9-e046-42b4-a316-55d3258021ec");
        UUID creepIDUUID = UUID.fromString("fb470bd5-c2c4-482c-a85c-abd71e0f937f");
        UUID userDetailsUUID = UUID.fromString("04717e18-6251-4393-a665-bc7cb61e0c60");
        DBConnection dbConnection = new DBConnection();
        Creep testingCreep = new Creep(userDetailsUUID, "creep", "epep", "mail", "haslo", roleUUID, true, "999", "creep",creepIDUUID);
        CreepDAO creepDAO = new CreepDAO(dbConnection);
        Assert.assertEquals(testingCreep.getAll(), creepDAO.get(userDetailsUUID).getAll());
    }

    @Test
    @Order(3)
    void edit() throws AbsenceOfRecordsException {
        UUID roleUUID = UUID.fromString("84c5eca9-e046-42b4-a316-55d3258021ec");
        UUID creepIDUUID = UUID.fromString("fb470bd5-c2c4-482c-a85c-abd71e0f937f");
        UUID userDetailsUUID = UUID.fromString("04717e18-6251-4393-a665-bc7cb61e0c60");
        DBConnection dbConnection = new DBConnection();
        Creep testingCreep = new Creep(userDetailsUUID, "po", "zmianie", "powinno", "byc", roleUUID, true, "999", "creep",creepIDUUID);
        CreepDAO creepDAO = new CreepDAO(dbConnection);
        creepDAO.edit(testingCreep);
        Assert.assertEquals(testingCreep.getAll(), creepDAO.get(userDetailsUUID).getAll());
    }

    @Test
    @Order(4)
    void remove() {
        UUID roleUUID = UUID.fromString("84c5eca9-e046-42b4-a316-55d3258021ec");
        UUID creepIDUUID = UUID.fromString("fb470bd5-c2c4-482c-a85c-abd71e0f937f");
        UUID userDetailsUUID = UUID.fromString("04717e18-6251-4393-a665-bc7cb61e0c60");
        DBConnection dbConnection = new DBConnection();
        Creep testingCreep = new Creep(userDetailsUUID, "po", "zmianie", "powinno", "byc", roleUUID, true, "999", "creep",creepIDUUID);
        CreepDAO creepDAO = new CreepDAO(dbConnection);
        int expected = creepDAO.getAll().size()-1;
        creepDAO.remove(testingCreep);
        Assert.assertEquals(expected, creepDAO.getAll().size());
    }

}