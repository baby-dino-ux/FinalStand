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
import javax.swing.table.DefaultTableModel;
import session.UserData;

/**
 *
 * @author ashlaran
 */
public class config {
    
    public static Connection connectDB() {
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC"); // Load the SQLite JDBC driver
            con = DriverManager.getConnection("jdbc:sqlite:clean.db"); // Establish connection
            System.out.println("Connection Successful");
        } catch (Exception e) {
            System.out.println("Connection Failed: " + e);
        }
        return con;
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
     * Get username for the logged-in user
     * @param sql SQL query
     * @param values Parameters for SQL query
     * @return Username string
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
        return "User"; // Default fallback
    }
    
    /**
     * Get complete user data for session management
     * UPDATED: Now checks if 'id' column exists in the table, and uses ROWID if it doesn't
     * @param email User email
     * @param password User password
     * @return UserData object with all user information, or null if not found
     */
    public UserData getUserData(String email, String password) {
        // First, try to get data WITH id column
        String sqlWithId = "SELECT id, username, firstname, lastname, email, password, type " +
                          "FROM tbl_users WHERE email = ? AND password = ?";
        
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sqlWithId)) {
            
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new UserData(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("type")
                    );
                }
            }
        } catch (SQLException e) {
            // If 'id' column doesn't exist, try with ROWID
            System.out.println("ID column not found, trying with ROWID: " + e.getMessage());
            
            String sqlWithRowId = "SELECT ROWID as id, username, firstname, lastname, email, password, type " +
                                 "FROM tbl_users WHERE email = ? AND password = ?";
            
            try (Connection conn = connectDB();
                 PreparedStatement pstmt = conn.prepareStatement(sqlWithRowId)) {
                
                pstmt.setString(1, email);
                pstmt.setString(2, password);
                
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return new UserData(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("firstname"),
                            rs.getString("lastname"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("type")
                        );
                    }
                } catch (SQLException ex) {
                    System.out.println("Error getting user data with ROWID: " + ex.getMessage());
                }
            } catch (SQLException ex) {
                System.out.println("Error connecting to database: " + ex.getMessage());
            }
        }
        return null;
    }
    
    /**
     * Display data in JTable without using DbUtils library
     * This is a custom implementation that converts ResultSet to TableModel
     */
    public void displayData(String sql, javax.swing.JTable table) {
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            // Get metadata to determine column count and names
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            
            // Create column names array
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }
            
            // Create table model
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            
            // Add rows to the model
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                model.addRow(row);
            }
            
            // Set the model to the table
            table.setModel(model);
            
        } catch (SQLException e) {
            System.out.println("Error displaying data: " + e.getMessage());
        }
    }
    
    /**
     * Update a record in the database
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
     * Delete a record from the database
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
     * Get a single value from the database
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
}