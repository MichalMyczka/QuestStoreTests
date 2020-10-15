package org.example.handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.apache.commons.io.output.NullOutputStream;
import org.example.DAO.LoginDAO;
import org.example.model.Mentor;
import org.example.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class LoginHandlerTest {

    @Test
    void handle() throws Exception {
        LoginDAO loginDAO = setupLoginDaoMock();
        HttpExchange httpExchange = setupHttpExchangeMock();
        LoginHandler loginHandler = new LoginHandler(null,loginDAO);

        loginHandler.handle(httpExchange);

        Assertions.assertAll(
                () -> verify()
        );
    }

    private HttpExchange setupHttpExchangeMock() {
        Headers header = new Headers();

        String initialString = "email=email@gmail.com&password=password";
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());

        HttpExchange httpExchange = mock(HttpExchange.class);
        when(httpExchange.getResponseHeaders()).thenReturn(header);
        when(httpExchange.getRequestBody()).thenReturn(targetStream);
        when(httpExchange.getResponseBody()).thenReturn(new NullOutputStream());
        return httpExchange;
    }

    private LoginDAO setupLoginDaoMock() throws Exception {
        UUID uuid = UUID.randomUUID();
        User mentor = new Mentor(uuid,null,null,"email@gmail.com",null,null ,false, null, "mentor", null);
        LoginDAO loginDAO = mock(LoginDAO.class);
        when(loginDAO.login("email@gmail.com","password")).thenReturn(mentor);
        return loginDAO;
    }
}