package AdminInternalPage;


import Admin.Users;
import LoginandRegister.LoginForm;
import Session.Session;
import config.config;
import java.util.UUID;
import javax.swing.JOptionPane;
import config.PasswordUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class Form extends javax.swing.JFrame {
   
    private String mode   = "Add"; // "Add" or "Update"
    private String userId = null;
    private config conf   = new config();
 
    // ── Default constructor — Add mode ──────────────────────────────────────
    public Form() {
        initComponents();
        wireButtonGroups();           // wire AFTER initComponents so objects are final
        setMode("Add");
        active.setSelected(true);     // default status
        staffbutt1.setSelected(true); // default type
    }
 
    // ── Constructor with pre-filled data — Update mode ──────────────────────
    public Form(String mode, String id, String firstname, String lastname,
                String email, String username, String type, String status) {
        initComponents();
        wireButtonGroups();
        setMode(mode);
        this.userId = id;
 
        if ("Update".equals(mode)) {
            idfield.setText(id != null ? id : "");
            fnamefield.setText(firstname != null ? firstname : "");
            lastnamefield.setText(lastname != null ? lastname : "");
            emailfield1.setText(email != null ? email : "");
            // FIX: username is now properly passed from DB and displayed here
            usernamefield.setText(username != null ? username : "");
 
            // Pre-select TYPE radio button from the database value
            if ("Employee".equalsIgnoreCase(type)) {
                Employeebutt.setSelected(true);
            } else {
                staffbutt1.setSelected(true); // default Staff
            }
 
            // Pre-select STATUS radio button
            if ("Active".equalsIgnoreCase(status)) {
                active.setSelected(true);
            } else if ("Inactive".equalsIgnoreCase(status)) {
                Inactive.setSelected(true);
            } else {
                // Pending or any other status — treat as Inactive visually
                Inactive.setSelected(true);
            }
        } else {
            active.setSelected(true);
            staffbutt1.setSelected(true);
        }
    }
 
    // ── Set mode label & button text ─────────────────────────────────────────
    public void setMode(String mode) {
        this.mode = mode;
        if ("Update".equals(mode)) {
            label1.setText("Edit User");
            st_label.setText("SAVE");
        } else {
            label1.setText("Add User");
            st_label.setText("ADD");
        }
    }
 
    // ── Field validation ─────────────────────────────────────────────────────
    private boolean validateFields() {
        String fn = fnamefield.getText().trim();
        String ln = lastnamefield.getText().trim();
        String em = emailfield1.getText().trim();
        String un = usernamefield.getText().trim();
        if (fn.isEmpty() || ln.isEmpty() || em.isEmpty() || un.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "First Name, Last Name, Email, and Username are all required.",
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
 
        // Basic e-mail format check
        if (!em.contains("@") || !em.contains(".")) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid email address.",
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
 
        return true;
    }
 
    // FIX: Check if an email already exists in the database (for Add mode)
    private boolean isEmailAlreadyExists(String email) {
        try (Connection conn = config.connectDB()) {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(*) FROM tbl_users WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
 
    // FIX: Check if a username already exists in the database (for Add mode)
    private boolean isUsernameAlreadyExists(String username) {
        try (Connection conn = config.connectDB()) {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(*) FROM tbl_users WHERE username = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
 

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        ex = new javax.swing.JLabel();
        label1 = new javax.swing.JLabel();
        exit = new javax.swing.JLabel();
        add = new javax.swing.JPanel();
        st_label = new javax.swing.JLabel();
        id = new javax.swing.JLabel();
        firstname = new javax.swing.JLabel();
        fnamefield = new javax.swing.JTextField();
        lastnamefield = new javax.swing.JTextField();
        lastname = new javax.swing.JLabel();
        status = new javax.swing.JLabel();
        type = new javax.swing.JLabel();
        Employeebutt = new javax.swing.JRadioButton();
        Inactive = new javax.swing.JRadioButton();
        usernamefield = new javax.swing.JTextField();
        username = new javax.swing.JLabel();
        staffbutt1 = new javax.swing.JRadioButton();
        active = new javax.swing.JRadioButton();
        email2 = new javax.swing.JLabel();
        emailfield1 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        idfield = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(29, 45, 61));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 234, 234), 3));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(55, 86, 93));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 234, 234), 3));
        jPanel2.setLayout(null);

        ex.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        ex.setForeground(new java.awt.Color(239, 234, 234));
        ex.setText("Exit");
        jPanel2.add(ex);
        ex.setBounds(510, 20, 40, 40);

        label1.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        label1.setForeground(new java.awt.Color(239, 234, 234));
        label1.setText("Label");
        jPanel2.add(label1);
        label1.setBounds(20, 20, 180, 40);

        exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout (2).png"))); // NOI18N
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitMouseClicked(evt);
            }
        });
        jPanel2.add(exit);
        exit.setBounds(550, 20, 40, 40);

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 590, 60));

        add.setBackground(new java.awt.Color(55, 86, 93));
        add.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        add.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addMouseExited(evt);
            }
        });
        add.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        st_label.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        st_label.setForeground(new java.awt.Color(239, 234, 234));
        st_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        st_label.setText("Label");
        add.add(st_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 100, 20));

        jPanel1.add(add, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 390, 100, 40));

        id.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        id.setForeground(new java.awt.Color(239, 234, 234));
        id.setText("ID:");
        jPanel1.add(id, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 90, 30));

        firstname.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        firstname.setForeground(new java.awt.Color(239, 234, 234));
        firstname.setText("First Name:");
        jPanel1.add(firstname, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 90, 30));

        fnamefield.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        fnamefield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fnamefield.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 234, 234), 2));
        fnamefield.setOpaque(false);
        jPanel1.add(fnamefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 130, 210, 30));

        lastnamefield.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        lastnamefield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lastnamefield.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 234, 234), 2));
        lastnamefield.setOpaque(false);
        jPanel1.add(lastnamefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 170, 210, 30));

        lastname.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lastname.setForeground(new java.awt.Color(239, 234, 234));
        lastname.setText("Last Name:");
        jPanel1.add(lastname, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 90, 30));

        status.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        status.setForeground(new java.awt.Color(239, 234, 234));
        status.setText("Status:");
        jPanel1.add(status, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 330, 90, 30));

        type.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        type.setForeground(new java.awt.Color(239, 234, 234));
        type.setText("Type:");
        jPanel1.add(type, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 280, 90, 30));

        Employeebutt.setBackground(new java.awt.Color(55, 86, 93));
        Employeebutt.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        Employeebutt.setForeground(new java.awt.Color(255, 255, 255));
        Employeebutt.setText("Employee");
        Employeebutt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmployeebuttActionPerformed(evt);
            }
        });
        jPanel1.add(Employeebutt, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 290, 90, 30));

        Inactive.setBackground(new java.awt.Color(55, 86, 93));
        Inactive.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        Inactive.setForeground(new java.awt.Color(255, 255, 255));
        Inactive.setText("Inactive");
        Inactive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InactiveActionPerformed(evt);
            }
        });
        jPanel1.add(Inactive, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 330, 90, 30));

        usernamefield.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        usernamefield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        usernamefield.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 234, 234), 2));
        usernamefield.setOpaque(false);
        jPanel1.add(usernamefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 250, 210, 30));

        username.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        username.setForeground(new java.awt.Color(239, 234, 234));
        username.setText("Username:");
        jPanel1.add(username, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 250, 90, 30));

        staffbutt1.setBackground(new java.awt.Color(55, 86, 93));
        staffbutt1.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        staffbutt1.setForeground(new java.awt.Color(255, 255, 255));
        staffbutt1.setText("Staff");
        staffbutt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                staffbutt1ActionPerformed(evt);
            }
        });
        jPanel1.add(staffbutt1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 290, 80, 30));

        active.setBackground(new java.awt.Color(55, 86, 93));
        active.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        active.setForeground(new java.awt.Color(255, 255, 255));
        active.setText("Active");
        active.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                activeActionPerformed(evt);
            }
        });
        jPanel1.add(active, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 330, 80, 30));

        email2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        email2.setForeground(new java.awt.Color(239, 234, 234));
        email2.setText("Email:");
        jPanel1.add(email2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 210, 90, 30));

        emailfield1.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        emailfield1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        emailfield1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 234, 234), 2));
        emailfield1.setOpaque(false);
        jPanel1.add(emailfield1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 210, 210, 30));

        jPanel3.setBackground(new java.awt.Color(29, 45, 61));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        idfield.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        idfield.setForeground(new java.awt.Color(239, 234, 234));
        idfield.setText("ID");
        jPanel3.add(idfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, 40, 30));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 420, 320));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -20, -1, 440));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void addMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseClicked
    if (!validateFields()) return;
 
        String fn   = fnamefield.getText().trim();
        String ln   = lastnamefield.getText().trim();
        String em   = emailfield1.getText().trim();
        String un   = usernamefield.getText().trim();
        String tp   = staffbutt1.isSelected() ? "Staff" : "Employee";
        String stat = active.isSelected() ? "Active" : "Inactive";
 
        if ("Add".equals(mode)) {
            // FIX: Validate that the email does not already exist before inserting
            if (isEmailAlreadyExists(em)) {
                JOptionPane.showMessageDialog(this,
                    "The email address '" + em + "' is already registered.\nPlease use a different email.",
                    "Duplicate Email", JOptionPane.WARNING_MESSAGE);
                return; // Stop — do not proceed with saving
            }
 
            // FIX: Also validate that the username is not taken
            if (isUsernameAlreadyExists(un)) {
                JOptionPane.showMessageDialog(this,
                    "The username '" + un + "' is already taken.\nPlease choose a different username.",
                    "Duplicate Username", JOptionPane.WARNING_MESSAGE);
                return;
            }
 
            String generatedPassword = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
            conf.addRecord(
                "INSERT INTO tbl_users (firstname, lastname, email, username, password, type, status) VALUES (?, ?, ?, ?, ?, ?, ?)",
                fn, ln, em, un, PasswordUtil.hash(generatedPassword), tp, stat
            );
            JOptionPane.showMessageDialog(this,
                "User added successfully!\nTemporary Password: " + generatedPassword,
                "Success — Save This Password", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // UPDATE — email and ID cannot be changed (per spec), but we still save other fields
            conf.updateRecord(
                "UPDATE tbl_users SET firstname=?, lastname=?, username=?, type=?, status=? WHERE id=?",
                fn, ln, un, tp, stat, Integer.parseInt(userId)
            );
            JOptionPane.showMessageDialog(this, "User updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
 
        dispose(); // Close form only — does not exit the application
            
    }//GEN-LAST:event_addMouseClicked

    private void addMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseEntered
  
    }//GEN-LAST:event_addMouseEntered

    private void addMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseExited
  
    }//GEN-LAST:event_addMouseExited

    private void EmployeebuttActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmployeebuttActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EmployeebuttActionPerformed

    private void InactiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InactiveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InactiveActionPerformed

    private void staffbutt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_staffbutt1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_staffbutt1ActionPerformed

    private void activeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_activeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_activeActionPerformed

    private void exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMouseClicked
         int confirm = JOptionPane.showConfirmDialog(this, "Discard changes and close?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) dispose();
    }//GEN-LAST:event_exitMouseClicked

   private void wireButtonGroups() {
        buttonGroup1.add(staffbutt1);   // Type: Staff / Employee
        buttonGroup1.add(Employeebutt);
        buttonGroup2.add(active);       // Status: Active / Inactive
        buttonGroup2.add(Inactive);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
          Session session = Session.getInstance();
        if (!session.isLoggedIn()) {
            JOptionPane.showMessageDialog(null, "Please login first!", "Unauthorized Access", JOptionPane.WARNING_MESSAGE);
            java.awt.EventQueue.invokeLater(() -> new LoginForm().setVisible(true));
            return;
        }
        java.awt.EventQueue.invokeLater(() -> new Form().setVisible(true));
    
   }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton Employeebutt;
    private javax.swing.JRadioButton Inactive;
    private javax.swing.JRadioButton active;
    private javax.swing.JPanel add;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel email2;
    public javax.swing.JTextField emailfield1;
    private javax.swing.JLabel ex;
    private javax.swing.JLabel exit;
    private javax.swing.JLabel firstname;
    public javax.swing.JTextField fnamefield;
    private javax.swing.JLabel id;
    private javax.swing.JLabel idfield;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel lastname;
    public javax.swing.JTextField lastnamefield;
    public javax.swing.JLabel st_label;
    private javax.swing.JRadioButton staffbutt1;
    private javax.swing.JLabel status;
    private javax.swing.JLabel type;
    private javax.swing.JLabel username;
    public javax.swing.JTextField usernamefield;
    // End of variables declaration//GEN-END:variables

  

  
}

