package config;

import java.security.MessageDigest;
import java.util.Base64;

public class PasswordUtil {
    
    /**
     * Hash a password using SHA-256.
     */
    public static String hash(String plainPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(plainPassword.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Password hashing failed", e);
        }
    }
    
    /**
     * Verify a plain password against a hash.
     */
    public static boolean verify(String plainPassword, String hash) {
        return hash(plainPassword).equals(hash);
    }
}