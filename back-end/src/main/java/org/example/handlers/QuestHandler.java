package org.example.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.DAO.DAO;
import org.example.DAO.DBConnection;
import org.example.model.Quest;

import java.io.IOException;
import java.util.List;

public class QuestHandler implements HttpHandler {

    private DBConnection dbConnection;
    private DAO<Quest> questDAO;

    public QuestHandler(DBConnection dbConnection, DAO<Quest> questDAO) {
        this.dbConnection = dbConnection;
        this.questDAO = questDAO;
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String response = "";
        int status = 200;
        try {
            if (method.equals("GET")) {
                response = getQuests();
                ResponseHelper.sendResponse(response, exchange, status);
            }
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(404, response.getBytes().length);
        }
    }

    private String getQuests() throws JsonProcessingException {
        List<Quest> quests = questDAO.getAll();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(quests);
    }

}
