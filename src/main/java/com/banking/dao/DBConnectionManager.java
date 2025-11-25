package com.banking.dao;

import java.sql.*;

public class DBConnectionManager {
    private static final String URL =
            "jdbc:mysql://localhost:3306/bank?useSSL=false&serverTimezone=UTC";
    private static final String USER = "banker";
    private static final String PASS = "Aameer@12214203";

    static {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
