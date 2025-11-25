package com.banking.util;



import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

// Simple placeholder hashing util. Replace with BCrypt in production.
public class PasswordUtil {
    public static String hash(String plain) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] dig = md.digest(plain.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(dig);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verify(String plain, String hash) {
        return hash(plain).equals(hash);
    }
}

