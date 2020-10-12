package org.example.model;

import java.util.UUID;

public class StudentArtifact {

    private UUID id;
    private boolean status;
    private String name;
    private int price;
    private  String category;
    private  String description;
    private  String type;

    public StudentArtifact(UUID id, boolean status, String name, int price, String category, String description, String type) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public boolean isStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
