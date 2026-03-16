/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Session.UserData;

/**
 *
 * @author ashlaran
 */
public class config {
    
    public static Connection connectDB() {
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:clean.db");
            System.out.println("Connection Successful");
        } catch (Exception e) {
            System.out.println("Connection Failed: " + e);
        }
        return con;
    }

    /**
     * Run once on app startup to ensure the status column exists.
     * Safe to call every time — it handles the error if column already exists.
     */
    public void setupDatabase() {

        try (Connection conn = connectDB()) {

            // ── Add status column to users table (account status: Active/Inactive) ──
            try (PreparedStatement pstmt = conn.prepareStatement(
                    "ALTER TABLE tbl_users ADD COLUMN status TEXT DEFAULT 'Inactive'")) {
                pstmt.executeUpdate();
            } catch (SQLException e) {
                // Column already exists — silent ignore
            }

            // ── Add work_status column (Available/Busy/Off Duty — employees only) ──
            try (PreparedStatement pstmt = conn.prepareStatement(
                    "ALTER TABLE tbl_users ADD COLUMN work_status TEXT DEFAULT 'Available'")) {
                pstmt.executeUpdate();
            } catch (SQLException e) {
                // Column already exists — silent ignore
            }


            // ── Add description column to tbl_services ───────────────────────
            try (PreparedStatement p = conn.prepareStatement(
                    "ALTER TABLE tbl_services ADD COLUMN s_description TEXT DEFAULT ''")) {
                p.executeUpdate();
            } catch (SQLException e) {
                // Column already exists — silent ignore
            }

            // ── Ensure tbl_feedback exists with all columns ───────────────────
            try (PreparedStatement p = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS tbl_feedback " +
                    "(f_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "f_employee TEXT, f_rating TEXT, f_comment TEXT, " +
                    "f_date TEXT, f_booking_id INTEGER DEFAULT NULL)")) {
                p.executeUpdate();
            } catch (SQLException e) {
                // Table already exists — silent ignore
            }
            try (PreparedStatement p = conn.prepareStatement(
                    "ALTER TABLE tbl_feedback ADD COLUMN f_booking_id INTEGER DEFAULT NULL")) {
                p.executeUpdate();
            } catch (SQLException e) {
                // Column already exists — silent ignore
            }
            String[] newCols = {

                "ALTER TABLE tbl_bookings ADD COLUMN b_address TEXT DEFAULT NULL",
                "ALTER TABLE tbl_bookings ADD COLUMN b_contact TEXT DEFAULT NULL",
                "ALTER TABLE tbl_bookings ADD COLUMN b_email TEXT DEFAULT NULL",
                "ALTER TABLE tbl_bookings ADD COLUMN b_tasknote TEXT DEFAULT NULL",
                "ALTER TABLE tbl_bookings ADD COLUMN b_staff TEXT DEFAULT NULL"

            };

            for (String colSql : newCols) {

                try (PreparedStatement p = conn.prepareStatement(colSql)) {

                    p.executeUpdate();

                } catch (SQLException e) {
                    // Column already exists — silent ignore
                }
            }


            // ── Make sure Admin accounts are Active ──────────────────────────
            try (PreparedStatement pstmt2 = conn.prepareStatement(
                    "UPDATE tbl_users SET status = 'Active' WHERE type = 'Admin' AND (status IS NULL OR status = 'Inactive' OR status = 'Pending')")) {
                int rows = pstmt2.executeUpdate();
                if (rows > 0) System.out.println("Admin accounts set to Active: " + rows);
            } catch (SQLException e) {
                System.out.println("Error updating admin status: " + e.getMessage());
            }

            // ── Set default work_status for employees who have none yet ──────
            try (PreparedStatement pstmt3 = conn.prepareStatement(
                    "UPDATE tbl_users SET work_status = 'Available' WHERE type = 'Employee' AND (work_status IS NULL OR work_status = '')")) {
                pstmt3.executeUpdate();
            } catch (SQLException e) {
                // ignore
            }

        } catch (SQLException e) {

            System.out.println("Database setup error: " + e.getMessage());
        }
    }
    
    public void addRecord(String sql, Object... values) {
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < values.length; i++) {
                pstmt.setObject(i + 1, values[i]);
            }

            pstmt.executeUpdate();
            System.out.println("Record added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding record: " + e.getMessage());
        }
    }
    
    public String authenticate(String sql, Object... values) {
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < values.length; i++) {
                pstmt.setObject(i + 1, values[i]);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("type");
                }
            }
        } catch (SQLException e) {
            System.out.println("Login Error: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Get username for the logged-in user.
     */
    public String getUsername(String sql, Object... values) {
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < values.length; i++) {
                pstmt.setObject(i + 1, values[i]);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("username");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting username: " + e.getMessage());
        }
        return "User";
    }
    
    /**
     * Get complete user data for session management.
     * Includes the 'status' column (Pending / Active / Rejected).
     * Falls back to ROWID if 'id' column doesn't exist.
     */
    public UserData getUserData(String email, String password) {

        String sql = "SELECT id, username, firstname, lastname, email, password, type, status " +
                     "FROM tbl_users WHERE email = ?";

        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");

                    // Check 1: compare with hashed password (new accounts)
                    boolean matchesHash  = hashPassword(password).equals(storedPassword);
                    // Check 2: compare with plain text (old accounts before hashing was added)
                    boolean matchesPlain = password.equals(storedPassword);

                    if (!matchesHash && !matchesPlain) {
                        return null; // wrong password
                    }

                    // Auto-upgrade: if old account still has plain text, hash it now
                    if (matchesPlain && !matchesHash) {
                        String upgradeSql = "UPDATE tbl_users SET password = ? WHERE id = ?";
                        try (PreparedStatement up = conn.prepareStatement(upgradeSql)) {
                            up.setString(1, hashPassword(password));
                            up.setInt(2, rs.getInt("id"));
                            up.executeUpdate();
                            System.out.println("Password auto-upgraded to hash for user: " + email);
                        } catch (SQLException ex) {
                            System.out.println("Auto-upgrade failed (non-critical): " + ex.getMessage());
                        }
                    }

                    return new UserData(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("email"),
                        storedPassword,
                        rs.getString("type"),
                        rs.getString("status")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Display data in JTable — supports optional query parameters (varargs).
     * Works for both parameterless queries and queries with one or more ? placeholders.
     */
    public void displayData(String sql, JTable table, String... params) {
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Bind any ? parameters passed in
            for (int i = 0; i < params.length; i++) {
                pstmt.setString(i + 1, params[i]);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                // Build column headers using label (respects AS aliases in SQL)
                String[] columnNames = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    columnNames[i - 1] = metaData.getColumnLabel(i);
                }

                // Build rows
                DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false; // make table read-only
                    }
                };

                while (rs.next()) {
                    Object[] row = new Object[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        row[i - 1] = rs.getObject(i);
                    }
                    model.addRow(row);
                }

                table.setModel(model);
            }

        } catch (SQLException e) {
            System.out.println("Error displaying data: " + e.getMessage());
        }
    }
    
    /**
     * Update a record in the database.
     */
    public void updateRecord(String sql, Object... values) {
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            for (int i = 0; i < values.length; i++) {
                pstmt.setObject(i + 1, values[i]);
            }
            
            pstmt.executeUpdate();
            System.out.println("Record updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating record: " + e.getMessage());
        }
    }
    
    /**
     * Delete a record from the database.
     */
    public void deleteRecord(String sql, Object... values) {
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            for (int i = 0; i < values.length; i++) {
                pstmt.setObject(i + 1, values[i]);
            }
            
            pstmt.executeUpdate();
            System.out.println("Record deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Error deleting record: " + e.getMessage());
        }
    }
    
    /**
     * Get a single value from the database.
     */
    public Object getValue(String sql, Object... values) {
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            for (int i = 0; i < values.length; i++) {
                pstmt.setObject(i + 1, values[i]);
            }
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getObject(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting value: " + e.getMessage());
        }
        return null;
    }

    public void setupBookingTable() {
        // Not implemented
    }

    /**
     * Register a new user — hashes the password using SHA-256 before storing.
     * No external library needed.
     */
    public boolean registerUser(String firstname, String lastname, String username,
                                 String email, String password) {
        String hashedPassword = hashPassword(password);
        String sql = "INSERT INTO tbl_users (firstname, lastname, username, email, password, type, status) " +
                     "VALUES (?, ?, ?, ?, ?, 'User', 'Pending')";
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstname);
            pstmt.setString(2, lastname);
            pstmt.setString(3, username);
            pstmt.setString(4, email);
            pstmt.setString(5, hashedPassword);
            pstmt.executeUpdate();
            System.out.println("User registered successfully!");
            return true;
        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
            return false;
        }
    }

    /**
     * Hash a password using SHA-256 (built into Java — no library needed).
     */
    public static String hashPassword(String plain) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(plain.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Hashing failed", e);
        }
    }

}