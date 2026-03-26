package Session;

public class UserData {
    
    private int id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String type;
    private String status;   // ✅ ADD THIS FIELD
    
    // Updated Constructor
    public UserData(int id, String username, String firstname, String lastname, 
                    String email, String password, String type, String status) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.type = type;
        this.status = status;   // ✅ ADD THIS
    }
    
    // Getters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getType() { return type; }
    public String getStatus() { return status; }   // ✅ FIXED
    
    // Setters
    public void setId(int id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setFirstname(String firstname) { this.firstname = firstname; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setType(String type) { this.type = type; }
    public void setStatus(String status) { this.status = status; }   // ✅ ADD THIS
    
    @Override
    public String toString() {
        return "UserData{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +   // optional but recommended
                '}';
    }
}