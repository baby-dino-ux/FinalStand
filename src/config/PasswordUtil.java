/*
 * Place this file in: Source Packages > config > PasswordUtil.java
 */
package config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class PasswordUtil {

    private PasswordUtil() {} // Utility class — no instances

    /**
     * Hash a plain-text password using SHA-256.
     * Returns a 64-character hex string.
     */
    public static String hash(String plainPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(plainPassword.getBytes("UTF-8"));

            // Convert byte array to hex string
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            // SHA-256 is guaranteed to exist in all Java SE implementations
            throw new RuntimeException("SHA-256 algorithm not found", e);
        } catch (java.io.UnsupportedEncodingException e) {
            // UTF-8 is guaranteed to exist in all Java SE implementations
            throw new RuntimeException("UTF-8 encoding not found", e);
        }
    }

    /**
     * Verify a plain-text password against a stored SHA-256 hash.
     * Returns true if they match.
     */
    public static boolean verify(String plainPassword, String storedHash) {
        if (plainPassword == null || storedHash == null) return false;
        return hash(plainPassword).equals(storedHash);
    }
}