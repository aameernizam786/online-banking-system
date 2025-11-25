package com.banking.dao;

import com.banking.model.User;
import java.sql.*;

public class UserDAO {

    public User findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username=?";
        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return null;

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

    // ✅ ADD THIS METHOD (FIX)
    public void save(User u) throws SQLException {
        String sql =
                "INSERT INTO users(username,password_hash,full_name,email,role) VALUES(?,?,?,?,?)";

        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPasswordHash());
            ps.setString(3, u.getFullName());
            ps.setString(4, u.getEmail());
            ps.setString(5, u.getRole().name());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                u.setUserId(rs.getInt(1));
            }
        }
    }
}
