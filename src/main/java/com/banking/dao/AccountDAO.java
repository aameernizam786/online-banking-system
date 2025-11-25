package com.banking.dao;

import com.banking.model.Account;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public Account findById(long id) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_id=?";
        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return null;

            Account a = new Account();
            a.setAccountId(rs.getLong("account_id"));
            a.setUserId(rs.getInt("user_id"));
            a.setAccountType(rs.getString("account_type"));
            a.setBalance(rs.getBigDecimal("balance"));
            return a;
        }
    }

    public List<Account> findByUserId(int userId) throws SQLException {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE user_id=?";
        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Account a = new Account();
                a.setAccountId(rs.getLong("account_id"));
                a.setUserId(userId);
                a.setAccountType(rs.getString("account_type"));
                a.setBalance(rs.getBigDecimal("balance"));
                list.add(a);
            }
        }
        return list;
    }

    // ✅ ADD THIS METHOD (FIX)
    public void save(Account a) throws SQLException {
        String sql =
                "INSERT INTO accounts(user_id, account_type, balance) VALUES (?,?,?)";

        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement ps =
                     c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, a.getUserId());
            ps.setString(2, a.getAccountType());
            ps.setBigDecimal(3, a.getBalance());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                a.setAccountId(rs.getLong(1));
            }
        }
    }

    public void update(Account a) throws SQLException {
        String sql = "UPDATE accounts SET balance=? WHERE account_id=?";
        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setBigDecimal(1, a.getBalance());
            ps.setLong(2, a.getAccountId());
            ps.executeUpdate();
        }
    }
}
