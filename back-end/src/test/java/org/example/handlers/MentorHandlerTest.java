package org.example.handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.example.DAO.DBConnection;
import org.example.DAO.MentorDAO;
import org.example.DAO.UserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MentorHandlerTest {

    @Test
    void handle() throws Exception {
        UserDAO userDAOMock = Mockito.mock(UserDAO.class);
        MentorDAO mentorDaoMock = Mockito.mock(MentorDAO.class);
        DBConnection dbConnection = new DBConnection();
        MentorHandler mentorHandler = new MentorHandler(dbConnection, mentorDaoMock, userDAOMock);
        HttpExchange httpExchangeMock = Mockito.mock(HttpExchange.class);
        String expected = "{\"id\":23,\"accountCredentials\":{\"login\":\"adrian\",\"password\":" +
                "\"c23ad6f18412014673b2d04794ca038ef6767fe94afe408dffb775362fe07e68\"," +
                "\"email\":\"adrian@gmail.com\",\"roleEnum\":\"MENTOR\"},\"firstName\":\"Adrian\",\"lastName\":\"Adrian\"}";

        Mockito.when(httpExchangeMock.getRequestURI()).thenReturn(URI.create("/mentors"));
        Mockito.when(mentorDaoMock.getAll()).thenReturn(new ArrayList<>());
        Mockito.when(httpExchangeMock.getResponseHeaders()).thenReturn(new Headers());
        Mockito.when(httpExchangeMock.getResponseBody()).thenReturn(new ByteArrayOutputStream(expected.length()));
        mentorHandler.handle(httpExchangeMock);
        Assertions.assertEquals(expected, mentorHandler.getMentors());
    }
}