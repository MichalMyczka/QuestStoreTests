
package org.example.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.DAO.DBConnection;
import org.example.DAO.LoginDAO;
import org.example.model.User;
import org.example.model.ValueObject.LoggedUser;
import org.example.services.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Collections;
import java.util.Map;

public class LoginHandler implements HttpHandler {

    private LoginDAO loginDAO;
    private ObjectMapper mapper;
    private DBConnection dbConnection;

    public LoginHandler(DBConnection dbConnection, LoginDAO loginDAO) {
        this.dbConnection = dbConnection;
        this.loginDAO = loginDAO;
        this.mapper = new ObjectMapper();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            exchange.getResponseHeaders().put("Access-Control-Allow-Origin", Collections.singletonList("*"));
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);

            Map<String, String> data = Parser.parseFormData(br.readLine());
            String email = data.get("email");
            String password = data.get("password");

            User user = loginDAO.login(email, password);
            LoggedUser loggedUser = new LoggedUser(
                    user.getUserDetailsID().toString(),
                    user.getEmail(),
                    user.getRole()
            );
            HttpCookie cookie = new HttpCookie("user", mapper.writeValueAsString(loggedUser));
            exchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
            ResponseHelper.sendResponse(mapper.writeValueAsString(loggedUser), exchange, 200);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseHelper.sendResponse(e.getMessage(), exchange, 404);
        }
    }

}
