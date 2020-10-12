package org.example.model;

import java.util.UUID;

public class MentorClass {

    private UUID classID;
    private  UUID mentorID;

    public MentorClass(UUID classID, UUID mentorID) {
        this.classID = classID;
        this.mentorID = mentorID;
    }

    public UUID getClassID() {
        return classID;
    }

    public UUID getMentorID() {
        return mentorID;
    }
}
