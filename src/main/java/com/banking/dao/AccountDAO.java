package com.banking.dao;



import com.banking.model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO implements GenericDAO<Account, Long> {
    @Override
    public Account findById(Long id) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_id = ?";
        try (Connection c = DBConnectionManager.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
            return null;
        }
    }

    public List<Account> findByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE user_id = ?";
        try (Connection c = DBConnectionManager.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            List<Account> list = new ArrayList<>();
            while (rs.next()) list.add(mapRow(rs));
            return list;
        }
    }

    @Override
    public List<Account> findAll() throws SQLException {
        String sql = "SELECT * FROM accounts";
        try (Connection c = DBConnectionManager.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            List<Account> list = new ArrayList<>();
            while (rs.next()) list.add(mapRow(rs));
            return list;
        }
    }

    @Override
    public void save(Account t) throws SQLException {
        String sql = "INSERT INTO accounts (user_id, account_type, balance) VALUES (?,?,?)";
        try (Connection c = DBConnectionManager.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, t.getUserId());
            ps.setString(2, t.getAccountType());
            ps.setBigDecimal(3, t.getBalance());
            ps.executeUpdate();
            ResultSet gk = ps.getGeneratedKeys();
            if (gk.next()) t.setAccountId(gk.getLong(1));
        }
    }

    @Override
    public void update(Account t) throws SQLException {
        String sql = "UPDATE accounts SET user_id=?, account_type=?, balance=? WHERE account_id=?";
        try (Connection c = DBConnectionManager.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, t.getUserId());
            ps.setString(2, t.getAccountType());
            ps.setBigDecimal(3, t.getBalance());
            ps.setLong(4, t.getAccountId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM accounts WHERE account_id = ?";
        try (Connection c = DBConnectionManager.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    private Account mapRow(ResultSet rs) throws SQLException {
        Account a = new Account();
        a.setAccountId(rs.getLong("account_id"));
        a.setUserId(rs.getInt("user_id"));
        a.setAccountType(rs.getString("account_type"));
        a.setBalance(rs.getBigDecimal("balance"));
        return a;
    }
}

