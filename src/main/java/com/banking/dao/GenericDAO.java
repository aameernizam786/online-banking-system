package com.banking.dao;
import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T, ID> {
    T findById(ID id) throws SQLException;
    List<T> findAll() throws SQLException;
    void save(T t) throws SQLException;
    void update(T t) throws SQLException;
    void delete(ID id) throws SQLException;
}
