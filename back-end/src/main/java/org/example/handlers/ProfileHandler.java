package org.example.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.DAO.*;
import org.example.DAO.Exception.AbsenceOfRecordsException;
import org.example.model.Creep;
import org.example.model.Mentor;
import org.example.model.Student;
import org.example.model.User;

import java.io.IOException;
import java.util.UUID;

public class ProfileHandler implements HttpHandler {

    private DBConnection dbConnection;
    private UserDAO userDAO;

    public ProfileHandler(DBConnection dbConnection, UserDAO userDAO) {
        this.dbConnection = dbConnection;
        this.userDAO = userDAO;
    }

    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String response = "";
        int status = 200;
        try {
            if (method.equals("POST")) {
                String url = exchange.getRequestURI().getRawPath();
                String[] urlParts = url.split("/");
                String id = urlParts[3].replace("user=", "").split("%20")[0];
                User user = userDAO.get(UUID.fromString(id));
                ObjectMapper objectMapper = new ObjectMapper();
                String role = user.getRole();
                switch (role){
                    case "mentor":
                        response = objectMapper.writeValueAsString((Mentor)user);
                        break;
                    case "student":
                        response = objectMapper.writeValueAsString((Student)user);
                        break;
                    case "creep":
                        response = objectMapper.writeValueAsString((Creep)user);
                        break;
                }
                ResponseHelper.sendResponse(response, exchange, status);
            }
        } catch (AbsenceOfRecordsException e) {
            e.printStackTrace();
        }
    }

}
