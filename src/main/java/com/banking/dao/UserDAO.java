package com.banking.dao;



import com.banking.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements GenericDAO<User, Integer> {
    @Override
    public User findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (Connection c = DBConnectionManager.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
            return null;
        }
    }

    public User findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection c = DBConnectionManager.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
            return null;
        }
    }

    @Override
    public List<User> findAll() throws SQLException {
        String sql = "SELECT * FROM users";
        try (Connection c = DBConnectionManager.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            List<User> list = new ArrayList<>();
            while (rs.next()) list.add(mapRow(rs));
            return list;
        }
    }

    @Override
    public void save(User t) throws SQLException {
        String sql = "INSERT INTO users (username, password_hash, full_name, email, role) VALUES (?,?,?,?,?)";
        try (Connection c = DBConnectionManager.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, t.getUsername());
            ps.setString(2, t.getPasswordHash());
            ps.setString(3, t.getFullName());
            ps.setString(4, t.getEmail());
            ps.setString(5, t.getRole().name());
            ps.executeUpdate();
            ResultSet gk = ps.getGeneratedKeys();
            if (gk.next()) t.setUserId(gk.getInt(1));
        }
    }

    @Override
    public void update(User t) throws SQLException {
        String sql = "UPDATE users SET username=?, password_hash=?, full_name=?, email=?, role=? WHERE user_id=?";
        try (Connection c = DBConnectionManager.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, t.getUsername());
            ps.setString(2, t.getPasswordHash());
            ps.setString(3, t.getFullName());
            ps.setString(4, t.getEmail());
            ps.setString(5, t.getRole().name());
            ps.setInt(6, t.getUserId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection c = DBConnectionManager.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private User mapRow(ResultSet rs) throws SQLException {
        User u = new User();
        u.setUserId(rs.getInt("user_id"));
        u.setUsername(rs.getString("username"));
        u.setPasswordHash(rs.getString("password_hash"));
        u.setFullName(rs.getString("full_name"));
        u.setEmail(rs.getString("email"));
        u.setRole(User.Role.valueOf(rs.getString("role")));
        return u;
    }
}
