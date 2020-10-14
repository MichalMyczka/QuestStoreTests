package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Quest {

    private UUID id;
    private  String name;
    private String description;
    private Integer value;

    public Quest(UUID id, String name, String description, Integer value) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public List<String> getAll(){
        List<String> list = new ArrayList<>();
        list.add(getName());
        list.add(getValue().toString());
        list.add(getDescription());
        list.add(getId().toString());
        return list;
    }
}
