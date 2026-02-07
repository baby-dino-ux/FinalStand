/*
 * Session Management Class
 * This class manages user session throughout the application
 * It stores logged-in user information and provides access to it from anywhere
 */
package Session;

/**
 * Singleton class to manage user session
 * @author ashlaran
 */
public class Session {
    
    // Static instance (Singleton pattern)
    private static Session instance = null;
    
    // Session data
    private int userId;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String userType;
    
    // Private constructor (Singleton pattern)
    private Session() {
        // Initialize with default values
        this.userId = -1;
        this.username = "";
        this.firstname = "";
        this.lastname = "";
        this.email = "";
        this.userType = "";
    }
    
    /**
     * Get the single instance of Session
     * @return Session instance
     */
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }
    
    /**
     * Start a new session with user data
     * @param userId User ID
     * @param username Username
     * @param firstname First name
     * @param lastname Last name
     * @param email Email address
     * @param userType User type (Admin or User)
     */
    public void login(int userId, String username, String firstname, String lastname, 
                      String email, String userType) {
        this.userId = userId;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.userType = userType;
        
        System.out.println("=== SESSION STARTED ===");
        System.out.println("User: " + username);
        System.out.println("Type: " + userType);
        System.out.println("=======================");
    }
    
    /**
     * End the current session (logout)
     */
    public void logout() {
        System.out.println("=== SESSION ENDED ===");
        System.out.println("User: " + username + " logged out");
        System.out.println("=====================");
        
        // Clear all session data
        this.userId = -1;
        this.username = "";
        this.firstname = "";
        this.lastname = "";
        this.email = "";
        this.userType = "";
    }
    
    /**
     * Check if user is logged in
     * @return true if user is logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return userId != -1 && !username.isEmpty();
    }
    
    /**
     * Check if current user is an Admin
     * @return true if user is Admin, false otherwise
     */
    public boolean isAdmin() {
        return "Admin".equalsIgnoreCase(userType);
    }
    
    /**
     * Check if current user is a regular User
     * @return true if user is User, false otherwise
     */
    public boolean isUser() {
        return "User".equalsIgnoreCase(userType);
    }
    
    // Getters
    public int getUserId() {
        return userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getFirstname() {
        return firstname;
    }
    
    public String getLastname() {
        return lastname;
    }
    
    public String getFullName() {
        return firstname + " " + lastname;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getUserType() {
        return userType;
    }
    
    /**
     * Get a formatted display of current session
     * @return Session information as String
     */
    public String getSessionInfo() {
        if (!isLoggedIn()) {
            return "No active session";
        }
        
        return "User: " + getFullName() + " (" + username + ")\n" +
               "Email: " + email + "\n" +
               "Type: " + userType;
    }
    
    /**
     * Print session info to console (for debugging)
     */
    public void printSessionInfo() {
        System.out.println("\n=== CURRENT SESSION ===");
        System.out.println(getSessionInfo());
        System.out.println("=======================\n");
    }
}