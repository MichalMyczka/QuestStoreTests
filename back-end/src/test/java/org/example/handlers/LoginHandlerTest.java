package org.example.handlers;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.apache.commons.io.output.NullOutputStream;
import org.example.DAO.LoginDAO;
import org.example.model.Mentor;
import org.example.model.User;
import org.example.model.ValueObject.LoggedUser;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static jdk.internal.net.http.common.Log.headers;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class LoginHandlerTest {
    ObjectMapper mapper = new ObjectMapper();
    Headers header;
    HttpCookie cookie;
    @Test
    void handle() throws Exception {
        LoginDAO loginDAO = setupLoginDaoMock();
        HttpExchange httpExchange = setupHttpExchangeMock();
        LoginHandler loginHandler = new LoginHandler(null,loginDAO);
        loginHandler.handle(httpExchange);
        User user = loginDAO.login("email@gmail.com","password");
        LoggedUser loggedUser = new LoggedUser(
                user.getUserDetailsID().toString(),
                user.getEmail(),
                user.getRole()
        );

        cookie = new HttpCookie("user", mapper.writeValueAsString(loggedUser));
        List<String> testList = new ArrayList<>();
        testList.add(cookie.toString());

        Assertions.assertAll(
                () -> verify(loginDAO,atLeast(1)).login("email@gmail.com","password"),
                () -> verify(httpExchange,atLeast(1)).getResponseHeaders(),
                () -> verify(httpExchange).getResponseBody(),
                () -> assertEquals(header.get("Access-Control-Allow-Origin"), Collections.singletonList("*")),
                () -> assertEquals(header.get("Set-Cookie"), testList),
                () -> assertEquals(header.get("Content-type"), Collections.singletonList("application/json")),
                () -> assertEquals(header.get("Access-Control-Allow-Origin"), Collections.singletonList("*")),
                () -> assertEquals(header.get("Access-Control-Allow-Methods"), Collections.singletonList("*"))
        );
    }

    private HttpExchange setupHttpExchangeMock() {
        header = new Headers();
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