package org.example.model;

import java.util.UUID;

public class StudentQuest {

    private UUID id;
    private UUID questID;
    private  UUID statusID;
    private UUID studentID;

    public StudentQuest(UUID id, UUID questID, UUID statusID, UUID studentID) {
        this.id = id;
        this.questID = questID;
        this.statusID = statusID;
        this.studentID = studentID;
    }

    public UUID getId() {
        return id;
    }

    public UUID getQuestID() {
        return questID;
    }

    public UUID getStatusID() {
        return statusID;
    }

    public UUID getStudentID() {
        return studentID;
    }
}
