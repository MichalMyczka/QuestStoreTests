package org.example.DAO;

import org.example.config.JSONreader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnection implements Connect {

    private String DBConnect;
    private String DBUser;
    private String DBPassword;
    public Statement statement;
    private Connection connection;
    private JSONreader reader;

    public DBConnection() {
        this.reader = new JSONreader();
        this.DBConnect = reader.JSONread().get("connection");
        this.DBUser = reader.JSONread().get("user");
        this.DBPassword = reader.JSONread().get("password");
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public Connection connect() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection(DBConnect,
                            DBUser, DBPassword);
            System.out.println("Opened database successfully.");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            e.printStackTrace();
        }
        return connection;
    }

    public void disconnect() {
        try {
            connection.close();
            System.out.println("Connection closed.");
        }  catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            e.printStackTrace();
        }
    }

    public void runSqlQuery(String sql) {
        connect();
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
            System.out.println("Query executed succesfully.");
        }  catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            e.printStackTrace();
        }
    }

}
