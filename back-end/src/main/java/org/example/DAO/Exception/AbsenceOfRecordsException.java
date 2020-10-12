package org.example.DAO.Exception;

public class AbsenceOfRecordsException extends Exception{

    public AbsenceOfRecordsException() {
        System.out.println("There is no expected record in data given.");
    }

}
