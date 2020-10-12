package org.example.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.DAO.DBConnection;
import org.example.DAO.Exception.AbsenceOfRecordsException;
import org.example.DAO.MentorDAO;
import org.example.DAO.UserDAO;
import org.example.config.PasswordCrypter;
import org.example.model.Mentor;
import org.example.model.User;
import org.example.services.DecoderURL;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class MentorHandler implements HttpHandler {

    //TODO generate is_active update possibility

    private MentorDAO mentorDAO;
    private DBConnection dbConnection;
    private UserDAO userDAO;

    public MentorHandler(DBConnection dbConnection, MentorDAO mentorDAO, UserDAO userDAO) {
        this.dbConnection = dbConnection;
        this.mentorDAO = mentorDAO;
        this.userDAO = userDAO;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String response = "";
        int status = 200;
        try {
            if (method.equals("GET")) {
                response = getMentors();
                ResponseHelper.sendResponse(response, exchange, status);
            }
            if (method.equals("POST")) {
                String url = exchange.getRequestURI().getRawPath();
                String[] urlParts = url.split("/");
                String userDetailsID = urlParts[2];
                if(urlParts[3].equals("remove")) {
                    removeMentor(userDetailsID);
                }
                if(urlParts[3].equals("edit-name")){
                    editMentorName(urlParts[2], DecoderURL.polishDecoder(urlParts[4]));
                }
                if(urlParts[3].equals("edit-surname")){
                    editMentorSurname(urlParts[2], DecoderURL.polishDecoder(urlParts[4]));
                }
                if(urlParts[3].equals("edit-mail")){
                    editMentorMail(urlParts[2], DecoderURL.polishDecoder(urlParts[4]));
                }
                if(urlParts[3].equals("edit-phone")){
                    editMentorPhone(urlParts[2], DecoderURL.polishDecoder(urlParts[4]));
                }
                if(urlParts[3].equals("add")){
                    addMentor();
                }
                response = getMentors();
                ResponseHelper.sendResponse(response, exchange, status);
            }
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(404, response.getBytes().length);
        }
    }

    private String getMentors() throws JsonProcessingException {
        List<User> mentors = userDAO.getAllMentors();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(mentors);
    }

    private void removeMentor(String userDetailsID) throws Exception {
        Mentor mentor = mentorDAO.get(UUID.fromString(userDetailsID));
        mentorDAO.remove(mentor);
    }

    private void editMentorName(String userDetailsID, String newName) throws Exception {
        Mentor mentor = mentorDAO.get(UUID.fromString(userDetailsID));
        mentor.setName(newName);
        mentorDAO.edit(mentor);
    }

    private void editMentorSurname(String userDetailsID, String newSurname) throws AbsenceOfRecordsException {
        Mentor mentor = mentorDAO.get(UUID.fromString(userDetailsID));
        mentor.setSurname(newSurname);
        mentorDAO.edit(mentor);
    }

    private void editMentorMail(String userDetailsID, String newMail) throws AbsenceOfRecordsException {
        Mentor mentor = mentorDAO.get(UUID.fromString(userDetailsID));
        mentor.setEmail(newMail);
        mentorDAO.edit(mentor);
    }

    private void editMentorPhone(String userDetailsID, String newPhone) throws AbsenceOfRecordsException {
        Mentor mentor = mentorDAO.get(UUID.fromString(userDetailsID));
        mentor.setPhoneNumber(newPhone);
        mentorDAO.edit(mentor);
    }

    private void addMentor() {
        Mentor mentor = new Mentor(UUID.randomUUID(), "Name", "Surname", "mail@mail.com",
                PasswordCrypter.crypter("password"), UUID.fromString("745792a7-681b-4efe-abdd-ca027678b397"),
                true, "444 222 000","mentor" , UUID.randomUUID());
        mentorDAO.add(mentor);
    }

}
