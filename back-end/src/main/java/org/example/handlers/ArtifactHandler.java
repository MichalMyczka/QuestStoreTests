package org.example.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.DAO.DAO;
import org.example.DAO.DBConnection;
import org.example.model.Artifact;

import java.io.IOException;
import java.util.List;

public class ArtifactHandler implements HttpHandler {

    private DBConnection dbConnection;
    private DAO<Artifact> artifactDAO;

    public ArtifactHandler(DBConnection dbConnection, DAO<Artifact> artifactDAO) {
        this.dbConnection = dbConnection;
        this.artifactDAO = artifactDAO;
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String response = "";
        int status = 200;
        try {
            if (method.equals("GET")) {
                response = getArtifacts();
                ResponseHelper.sendResponse(response, exchange, status);
            }
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(404, response.getBytes().length);
        }
    }

    private String getArtifacts() throws JsonProcessingException {
        List<Artifact> artifacts = artifactDAO.getAll();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(artifacts);
    }

}
