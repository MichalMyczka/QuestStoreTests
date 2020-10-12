package org.example.model;

import java.util.UUID;

public class StudentClass {

    private UUID classID;
    private UUID studentID;

    public StudentClass(UUID classID, UUID studentID) {
        this.classID = classID;
        this.studentID = studentID;
    }

    public UUID getClassID() {
        return classID;
    }

    public UUID getStudentID() {
        return studentID;
    }
}
