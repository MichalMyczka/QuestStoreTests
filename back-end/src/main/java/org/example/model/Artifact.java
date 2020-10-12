package org.example.model;

import java.util.UUID;

public class Artifact {

    private UUID id;
    private String name;
    private int price;
    private  String category;
    private  String description;
    private  String type;
    private UUID categoryID;
    private UUID typeID;

    public Artifact(UUID id, String name, int price, String category, String description, String type, UUID categoryID, UUID typeID) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.type = type;
        this.categoryID = categoryID;
        this.typeID = typeID;
    }

    public UUID getId() {
        return id;
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

    public UUID getCategoryID() {
        return categoryID;
    }

    public UUID getTypeID() {
        return typeID;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCategoryID(UUID categoryID) {
        this.categoryID = categoryID;
    }

    public void setTypeID(UUID typeID) {
        this.typeID = typeID;
    }

}
