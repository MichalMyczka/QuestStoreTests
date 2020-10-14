package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Artifact {

    private UUID id;
    private String name;
    private Integer price;
    private  String category;
    private  String description;
    private  String type;
    private UUID categoryID;
    private UUID typeID;

    public Artifact(UUID id, String name, Integer price, String category, String description, String type, UUID categoryID, UUID typeID) {
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

    public Integer getPrice() {
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

    public void setPrice(Integer price) {
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

    public List<String> getAll(){
        List<String> result = new ArrayList<>();
        result.add(getId().toString());
        result.add(getName());
        result.add(getPrice().toString());
        result.add(getCategory());
        result.add(getDescription());
        result.add(getType());
        result.add(getCategoryID().toString());
        result.add(getTypeID().toString());
        return  result;
    }
}
