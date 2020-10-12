package org.example.DAO;

import org.example.DAO.Exception.AbsenceOfRecordsException;

import java.util.List;
import java.util.UUID;

public interface DAO<T> {

    void add(T t);
    void remove(T t);
    void edit(T t);
    List<T> getAll();
    T get(UUID id) throws AbsenceOfRecordsException;

}
