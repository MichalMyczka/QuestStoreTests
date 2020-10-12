package org.example.handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;

public class ResponseHelper {

    public static void sendResponse(String response, HttpExchange exchange, int status) throws IOException {
        if (status == 200) {
            exchange.getResponseHeaders().put("Access-Control-Allow-Methods", Collections.singletonList("*"));
            exchange.getResponseHeaders().put("Content-type", Collections.singletonList("application/json"));
            exchange.getResponseHeaders().put("Access-Control-Allow-Origin", Collections.singletonList("*"));
        }
        exchange.sendResponseHeaders(status, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}
