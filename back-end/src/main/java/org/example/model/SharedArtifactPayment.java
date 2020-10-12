package org.example.model;

import java.util.UUID;

public class SharedArtifactPayment {

    private UUID id;
    private UUID studentID;
    private UUID studentArtifactID;
    private int payment;

    public SharedArtifactPayment(UUID id, UUID studentID, UUID studentArtifactID, int payment) {
        this.id = id;
        this.studentID = studentID;
        this.studentArtifactID = studentArtifactID;
        this.payment = payment;
    }

    public UUID getId() {
        return id;
    }

    public UUID getStudentID() {
        return studentID;
    }

    public UUID getStudentArtifactID() {
        return studentArtifactID;
    }

    public int getPayment() {
        return payment;
    }
}
