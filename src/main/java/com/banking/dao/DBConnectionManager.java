package com.banking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {
    // EDIT these for your local DB
    private static final String URL = "jdbc:mysql://localhost:3306/bank?useSSL=false&serverTimezone=UTC";
    private static final String USER = "banker";
    private static final String PASS = "Aameer@12214203";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL driver not found", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
