package org.example.model.ValueObject;

public class LoggedUser {

    private String id;
    private  String email;
    private String role;

    public LoggedUser(String id, String email, String role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
    
}
