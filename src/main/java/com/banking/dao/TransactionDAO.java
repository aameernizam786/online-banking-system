package com.banking.dao;



import com.banking.model.TransactionRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    public void save(TransactionRecord tr) throws SQLException {
        String sql = "INSERT INTO transactions (account_id, type, amount, description) VALUES (?,?,?,?)";
        try (Connection c = DBConnectionManager.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, tr.getAccountId());
            ps.setString(2, tr.getType());
            ps.setBigDecimal(3, tr.getAmount());
            ps.setString(4, tr.getDescription());
            ps.executeUpdate();
            ResultSet gk = ps.getGeneratedKeys();
            if (gk.next()) tr.setTransactionId(gk.getLong(1));
        }
    }

    public List<TransactionRecord> findByAccountId(long accountId) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE account_id = ? ORDER BY timestamp DESC";
        try (Connection c = DBConnectionManager.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, accountId);
            ResultSet rs = ps.executeQuery();
            List<TransactionRecord> list = new ArrayList<>();
            while (rs.next()) {
                TransactionRecord tr = new TransactionRecord();
                tr.setTransactionId(rs.getLong("transaction_id"));
                tr.setAccountId(rs.getLong("account_id"));
                tr.setType(rs.getString("type"));
                tr.setAmount(rs.getBigDecimal("amount"));
                Timestamp ts = rs.getTimestamp("timestamp");
                if (ts != null) tr.setTimestamp(ts.toLocalDateTime());
                tr.setDescription(rs.getString("description"));
                list.add(tr);
            }
            return list;
        }
    }
}

