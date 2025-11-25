package com.banking.util;

import java.security.MessageDigest;
import java.util.Base64;

public class PasswordUtil {
    public static String hash(String p) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return Base64.getEncoder().encodeToString(md.digest(p.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verify(String p, String h) {
        return hash(p).equals(h);
    }
}
