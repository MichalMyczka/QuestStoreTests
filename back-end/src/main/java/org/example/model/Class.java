package org.example.model;

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

}
