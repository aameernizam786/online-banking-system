package com.banking.model;

public class User {
    public enum Role { CUSTOMER, ADMIN }

    private int userId;
    private String username;
    private String passwordHash;
    private String fullName;
    private String email;
    private Role role;

    public int getUserId() { return userId; }
    public void setUserId(int id) { this.userId = id; }
    public String getUsername() { return username; }
    public void setUsername(String u) { this.username = u; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String p) { this.passwordHash = p; }
    public String getFullName() { return fullName; }
    public void setFullName(String f) { this.fullName = f; }
    public String getEmail() { return email; }
    public void setEmail(String e) { this.email = e; }
    public Role getRole() { return role; }
    public void setRole(Role r) { this.role = r; }
}
