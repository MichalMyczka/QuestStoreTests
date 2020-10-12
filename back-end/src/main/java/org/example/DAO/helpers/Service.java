package org.example.DAO.helpers;

import org.example.DAO.DBConnection;

import java.sql.SQLException;

public class Service {

    public static void closeDBConnection(String message, DBConnection dbConnection) {
        dbConnection.disconnect();
        System.out.println(message);
    }

    public static void exceptionHandling(SQLException e, String message) {
        e.printStackTrace();
        System.out.println(message);
    }

}
