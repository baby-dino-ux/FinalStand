package AdminInternalPage;


import Admin.Listofusers;
import LoginandRegister.LoginForm;
import Session.Session;
import config.config;
import java.util.UUID;
import javax.swing.JOptionPane;
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
        wireButtonGroups();           // wire AFTER initComponents so objects are final
        setMode(mode);
        this.userId = id;

        if ("Update".equals(mode)) {
            idfield.setText(id != null ? id : "");
            fnamefield.setText(firstname != null ? firstname : "");
            lastnamefield.setText(lastname != null ? lastname : "");
            emailfield1.setText(email != null ? email : "");
            usernamefield.setText(username != null ? username : "");

            // Pre-select TYPE radio button from the database value
            if ("Cleaner".equalsIgnoreCase(type)) {
                cleanerbutt.setSelected(true);
            } else {
                staffbutt1.setSelected(true); // default Staff
            }

            // Pre-select STATUS radio button from the database value
            if ("Active".equalsIgnoreCase(status)) {
                active.setSelected(true);
            } else if ("Pending".equalsIgnoreCase(status)) {
                pending.setSelected(true);
            } else {
                snooze.setSelected(true); // Snooze = account deactivated
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
        idfield = new javax.swing.JTextField();
        type = new javax.swing.JLabel();
        cleanerbutt = new javax.swing.JRadioButton();
        snooze = new javax.swing.JRadioButton();
        usernamefield = new javax.swing.JTextField();
        username = new javax.swing.JLabel();
        staffbutt1 = new javax.swing.JRadioButton();
        active = new javax.swing.JRadioButton();
        pending = new javax.swing.JRadioButton();
        email2 = new javax.swing.JLabel();
        emailfield1 = new javax.swing.JTextField();

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

        jPanel1.add(add, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 370, 100, 40));

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

        idfield.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        idfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        idfield.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 234, 234), 2));
        idfield.setEnabled(false);
        idfield.setOpaque(false);
        jPanel1.add(idfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 90, 210, 30));

        type.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        type.setForeground(new java.awt.Color(239, 234, 234));
        type.setText("Type:");
        jPanel1.add(type, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 280, 90, 30));

        cleanerbutt.setBackground(new java.awt.Color(55, 86, 93));
        cleanerbutt.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        cleanerbutt.setForeground(new java.awt.Color(255, 255, 255));
        cleanerbutt.setText("Cleaner");
        cleanerbutt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cleanerbuttActionPerformed(evt);
            }
        });
        jPanel1.add(cleanerbutt, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 290, 90, 30));

        snooze.setBackground(new java.awt.Color(55, 86, 93));
        snooze.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        snooze.setForeground(new java.awt.Color(255, 255, 255));
        snooze.setText("Snooze");
        snooze.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                snoozeActionPerformed(evt);
            }
        });
        jPanel1.add(snooze, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 330, 90, 30));

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
        jPanel1.add(active, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 330, 80, 30));

        pending.setBackground(new java.awt.Color(55, 86, 93));
        pending.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        pending.setForeground(new java.awt.Color(255, 255, 255));
        pending.setText("Pending");
        pending.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pendingActionPerformed(evt);
            }
        });
        jPanel1.add(pending, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 330, 90, 30));

        email2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        email2.setForeground(new java.awt.Color(239, 234, 234));
        email2.setText("Email:");
        jPanel1.add(email2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 210, 90, 30));

        emailfield1.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        emailfield1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        emailfield1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 234, 234), 2));
        emailfield1.setOpaque(false);
        jPanel1.add(emailfield1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 210, 210, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -20, -1, 430));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void addMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseClicked
   
        // Step 1 — Validate
        if (!validateFields()) {
            return; // validateFields() already shows the error dialog
        }

        // Step 2 — Collect field values
        String fn   = fnamefield.getText().trim();
        String ln   = lastnamefield.getText().trim();
        String em   = emailfield1.getText().trim();
        String un   = usernamefield.getText().trim();
        String tp   = staffbutt1.isSelected() ? "Staff" : "Cleaner";
        String stat = active.isSelected()  ? "Active"
                    : pending.isSelected() ? "Pending"
                    : "Snooze";

        // Step 3 — Execute SQL
        if ("Add".equals(mode)) {
            // Auto-generate a secure temporary password (first 8 chars of a UUID, uppercase)
            String generatedPassword = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();

            // INSERT — 7 columns, 7 values (auto-generated password included)
            conf.addRecord(
                "INSERT INTO tbl_users (firstname, lastname, email, username, password, type, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)",
                fn, ln, em, un, generatedPassword, tp, stat
            );
            // Show the generated password to the admin so they can share it
            JOptionPane.showMessageDialog(this,
                "User added successfully!\n\n" +
                "Temporary Password: " + generatedPassword + "\n\n" +
                "Please share this with the user so they can log in.",
                "Success — Save This Password", JOptionPane.INFORMATION_MESSAGE);

        } else {
            // UPDATE — saves firstname, lastname, email, username, TYPE and status.
            // Password is never touched here.
            conf.updateRecord(
                "UPDATE tbl_users SET firstname=?, lastname=?, email=?, username=?, type=?, status=? " +
                "WHERE id=?",
                fn, ln, em, un, tp, stat, Integer.parseInt(userId)
            );
            JOptionPane.showMessageDialog(this,
                "User updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }

        // Step 4 — Close the form (triggers windowClosed in Listofusers → reloads table)
        dispose();
            
    }//GEN-LAST:event_addMouseClicked

    private void addMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseEntered
  
    }//GEN-LAST:event_addMouseEntered

    private void addMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseExited
  
    }//GEN-LAST:event_addMouseExited

    private void cleanerbuttActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cleanerbuttActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cleanerbuttActionPerformed

    private void snoozeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_snoozeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_snoozeActionPerformed

    private void staffbutt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_staffbutt1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_staffbutt1ActionPerformed

    private void activeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_activeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_activeActionPerformed

    private void pendingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pendingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pendingActionPerformed

    private void exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMouseClicked
        int confirm = javax.swing.JOptionPane.showConfirmDialog(this,
            "Discard changes and close?", "Confirm Exit", javax.swing.JOptionPane.YES_NO_OPTION);
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            dispose(); // triggers windowClosed → Listofusers reloads the table
        }
    }//GEN-LAST:event_exitMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        Session session = Session.getInstance();
        if (!session.isLoggedIn()) {
            JOptionPane.showMessageDialog(null,
                "Please login first!",
                "Unauthorized Access",
                JOptionPane.WARNING_MESSAGE);
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new LoginForm().setVisible(true);
                }
            });
            return;
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form().setVisible(true);
            }
        });
    }
   private void wireButtonGroups() {
        buttonGroup1.add(staffbutt1);   // Type: Staff / Cleaner
        buttonGroup1.add(cleanerbutt);
        buttonGroup2.add(active);       // Status: Active / Pending / Snooze
        buttonGroup2.add(pending);
        buttonGroup2.add(snooze);
   }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton active;
    private javax.swing.JPanel add;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JRadioButton cleanerbutt;
    private javax.swing.JLabel email2;
    public javax.swing.JTextField emailfield1;
    private javax.swing.JLabel ex;
    private javax.swing.JLabel exit;
    private javax.swing.JLabel firstname;
    public javax.swing.JTextField fnamefield;
    private javax.swing.JLabel id;
    public javax.swing.JTextField idfield;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel lastname;
    public javax.swing.JTextField lastnamefield;
    private javax.swing.JRadioButton pending;
    private javax.swing.JRadioButton snooze;
    public javax.swing.JLabel st_label;
    private javax.swing.JRadioButton staffbutt1;
    private javax.swing.JLabel status;
    private javax.swing.JLabel type;
    private javax.swing.JLabel username;
    public javax.swing.JTextField usernamefield;
    // End of variables declaration//GEN-END:variables

    private Listofusers Listofusers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  
}

