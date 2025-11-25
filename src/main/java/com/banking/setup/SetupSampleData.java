package com.banking.setup;



import com.banking.dao.AccountDAO;
import com.banking.dao.UserDAO;
import com.banking.model.Account;
import com.banking.model.User;
import com.banking.util.PasswordUtil;

/*
 Helper to create a sample user + account with hashed password.
 Run this once after you have created the database schema (schema.sql).
*/
public class SetupSampleData {
    public static void main(String[] args) {
        try {
            UserDAO userDAO = new UserDAO();
            AccountDAO accountDAO = new AccountDAO();

            User u = new User();
            u.setUsername("alice");
            u.setPasswordHash(PasswordUtil.hash("alice123")); // sample password: alice123
            u.setFullName("Alice Johnson");
            u.setEmail("alice@example.com");
            u.setRole(User.Role.CUSTOMER);
            userDAO.save(u);
            System.out.println("Created user id: " + u.getUserId());

            Account a = new Account();
            a.setUserId(u.getUserId());
            a.setAccountType("SAVINGS");
            a.setBalance(new java.math.BigDecimal("1000.00"));
            accountDAO.save(a);
            System.out.println("Created account id: " + a.getAccountId());
            System.out.println("Setup complete. Login with username=alice password=alice123");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

