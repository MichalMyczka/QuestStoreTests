package org.example.DAO;

import org.example.DAO.Exception.AbsenceOfRecordsException;
import org.example.model.Creep;
import org.example.model.Mentor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreepDAO implements  DAO<Creep> {

    DBConnection dbConnection;

    public CreepDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public void add(Creep creep) {
        dbConnection.runSqlQuery(String.format("INSERT INTO user_details(id, name, surname, email, password, role_id, is_active) VALUES ('%s', '%s' ,'%s' ,'%s', '%s', 1, true);", creep.getUserDetailsID(), creep.getName(), creep.getSurname(), creep.getEmail(), creep.getPassword()));
        dbConnection.runSqlQuery(String.format("INSERT INTO creep(id, user_details_id) VALUES ('%s', '%s')", creep.getCreepID(), creep.getUserDetailsID()));
    }

    @Override
    public void remove(Creep creep) {
        dbConnection.runSqlQuery(String.format("DELETE FROM creep WHERE id = '%s';", creep.getUserDetailsID()));
        dbConnection.runSqlQuery(String.format("DELETE FROM user_details WHERE id = '%s';", creep.getUserDetailsID()));
    }

    @Override
    public void edit(Creep creep) {
        dbConnection.runSqlQuery(String.format("UPDATE creeps SET user_details_id = '%s' WHERE id = '%s';", creep.getUserDetailsID(), creep.getCreepID()));
        dbConnection.runSqlQuery(String.format("UPDATE user_details SET name = '%s', surname = '%s', email = '%s', password = '%s', role_id = '%s', is_active = '%b' WHERE id = '%s;'", creep.getName(), creep.getSurname(), creep.getEmail(), creep.getPassword(), creep.getRoleID(), creep.isActive(), creep.getUserDetailsID()));
    }

    @Override
    public List<Creep> getAll() {
        List<Creep> creeps = new ArrayList<>();
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement("SELECT ud.*, r.name" +
                    " AS role , c.creep_id FROM user_details ud JOIN roles r ON r.id = ud.role_id JOIN creeps c " +
                    "ON c.user_details_id = ud.id ORDER BY surname;");
            ResultSet allCreeps = preparedStatement.executeQuery();
            while (allCreeps.next()) {
                final UUID userDetailsID = UUID.fromString(allCreeps.getString("id"));
                final String name = allCreeps.getString("name");
                final String surname = allCreeps.getString("surname");
                final String email = allCreeps.getString("email");
                final String password = allCreeps.getString("password");
                final UUID roleID = UUID.fromString(allCreeps.getString("role_id"));
                final UUID creepID = UUID.fromString(allCreeps.getString("creep_id"));
                final boolean isActive = allCreeps.getBoolean("is_active");
                final String phoneNumber = allCreeps.getString("phone_number");
                final String role = allCreeps.getString("role");
                Creep creep = new Creep(userDetailsID, name, surname, email, password, roleID, isActive, phoneNumber,
                        role, creepID);
                creeps.add(creep);
            }
            dbConnection.disconnect();
            System.out.println("Selected creeps from data base successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Selecting creeps from data base failed.");
        }
        return creeps;
    }


    @Override
    public Creep get(UUID id) throws AbsenceOfRecordsException {
        try {
            dbConnection.connect();
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(
                    "SELECT ud.*, r.name AS role , c.creep_id FROM user_details ud JOIN roles r " +
                            "ON r.id = ud.role_id JOIN creeps c ON c.user_details_id = ud.id WHERE ud.id = ?;");
            preparedStatement.setObject(1, id, Types.OTHER);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                final String name = result.getString("name");
                final String surname = result.getString("surname");
                final String email = result.getString("email");
                final String password = result.getString("password");
                final UUID roleID = UUID.fromString(result.getString("role_id"));
                final UUID creepID = UUID.fromString(result.getString("creep_id"));
                final boolean isActive = result.getBoolean("is_active");
                final  String phoneNumber = result.getString("phone_number");
                final String role = result.getString("role");
                Creep creep = new Creep(id, name, surname, email, password, roleID, isActive, phoneNumber,
                        role, creepID);
                return creep;
            }
            dbConnection.disconnect();
            System.out.println("Selected creep from data base successfully.");
        } catch (SQLException e) {
            System.out.println("Selecting creep from data base failed.");
            e.printStackTrace();
        }
        throw new AbsenceOfRecordsException();
    }

}
