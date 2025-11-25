package com.banking.dao;

import com.banking.model.TransactionRecord;
import java.sql.*;

public class TransactionDAO {

    public void save(TransactionRecord t) throws SQLException {
        String sql =
                "INSERT INTO transactions(account_id,type,amount,description) VALUES(?,?,?,?)";
        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, t.getAccountId());
            ps.setString(2, t.getType());
            ps.setBigDecimal(3, t.getAmount());
            ps.setString(4, t.getDescription());
            ps.executeUpdate();
        }
    }
}
