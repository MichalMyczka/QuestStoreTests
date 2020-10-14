package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Class {

    private UUID id;
    private String name;

    public Class(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getAll(){
        List<String> list = new ArrayList<>();
        list.add(getName());
        list.add(getId().toString());
        return list;
    }
}
