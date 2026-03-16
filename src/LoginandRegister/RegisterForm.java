/*
 * Fixed:
 *  1. passwordfield.getText() → new String(passwordfield.getPassword())
 *     JPasswordField.getText() is deprecated and insecure; getPassword() is correct.
 *  2. emailExists() and usernameExists() now use config.connectDB() for
 *     consistency instead of a duplicate hardcoded DriverManager.getConnection().
 */
package LoginandRegister;

import config.config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class RegisterForm extends javax.swing.JFrame {

    public RegisterForm() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        exitbutton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        usernanefield = new javax.swing.JTextField();
        firstnamefield = new javax.swing.JTextField();
        passwordfield = new javax.swing.JPasswordField();
        lastnamefield = new javax.swing.JTextField();
        emailfield = new javax.swing.JTextField();
        background = new javax.swing.JLabel();
        logo = new javax.swing.JLabel();
        createbutton1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        create = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        exitbutton.setText("EXIT");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/registrationheader.jpg"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 500, 400));

        usernanefield.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 51, 255), new java.awt.Color(51, 51, 255), new java.awt.Color(51, 51, 255), new java.awt.Color(51, 51, 255)));
        getContentPane().add(usernanefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 250, 240, 30));

        firstnamefield.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 51, 255), new java.awt.Color(51, 51, 255), new java.awt.Color(51, 51, 255), new java.awt.Color(51, 51, 255)));
        firstnamefield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstnamefieldActionPerformed(evt);
            }
        });
        getContentPane().add(firstnamefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 130, 240, 30));

        passwordfield.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 51, 255), new java.awt.Color(51, 51, 255), new java.awt.Color(51, 51, 255), new java.awt.Color(51, 51, 255)));
        getContentPane().add(passwordfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 370, 240, 30));

        lastnamefield.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 51, 255), new java.awt.Color(51, 51, 255), new java.awt.Color(51, 51, 255), new java.awt.Color(51, 51, 255)));
        getContentPane().add(lastnamefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 190, 240, 30));

        emailfield.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 51, 255), new java.awt.Color(51, 51, 255), new java.awt.Color(51, 51, 255), new java.awt.Color(51, 51, 255)));
        emailfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailfieldActionPerformed(evt);
            }
        });
        getContentPane().add(emailfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 310, 240, 30));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/headername.png"))); // NOI18N
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 470, 350));

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo.jpg"))); // NOI18N
        getContentPane().add(logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(-150, 10, 310, 120));

        createbutton1.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        createbutton1.setForeground(new java.awt.Color(255, 255, 255));
        createbutton1.setText("CREATE");
        createbutton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createbutton1MouseClicked(evt);
            }
        });
        getContentPane().add(createbutton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 410, -1, -1));

        jLabel4.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        jLabel4.setText(" Already have an Account?");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 450, -1, -1));

        create.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/createbutton.jpg"))); // NOI18N
        getContentPane().add(create, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 350, 340, 90));

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 204));
        jLabel5.setText("Sign in");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 450, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/registration.jpg"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, -1, 400));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/background.jpg"))); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-300, -40, 1090, 530));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // ── Validate email format ─────────────────────────────────────────────────
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

    // ── FIX 2: Use config.connectDB() instead of a duplicate hardcoded URL ────
    private boolean emailExists(String email) {
        String query = "SELECT COUNT(*) FROM tbl_users WHERE email = ?";
        try (Connection conn = config.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    private boolean usernameExists(String username) {
        String query = "SELECT COUNT(*) FROM tbl_users WHERE username = ?";
        try (Connection conn = config.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    private boolean isPasswordSecure(String password) {
        if (password.length() < 6) return false;
        boolean hasLetter = false, hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) hasLetter = true;
            if (Character.isDigit(c)) hasDigit = true;
        }
        return hasLetter && hasDigit;
    }

    private void firstnamefieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstnamefieldActionPerformed
        // no-op
    }//GEN-LAST:event_firstnamefieldActionPerformed

    private void emailfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailfieldActionPerformed
        // no-op
    }//GEN-LAST:event_emailfieldActionPerformed

    private void createbutton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createbutton1MouseClicked
        String firstname = firstnamefield.getText().trim();
        String lastname  = lastnamefield.getText().trim();
        String username  = usernanefield.getText().trim();
        String email     = emailfield.getText().trim();
        // FIX 1: JPasswordField.getText() is deprecated — use getPassword() instead
        String password  = new String(passwordfield.getPassword()).trim();

        // ── All fields required ───────────────────────────────────────────────
        if (firstname.isEmpty() || lastname.isEmpty() || username.isEmpty()
                || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!",
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // ── Email format ──────────────────────────────────────────────────────
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid email address!\n\nExample: user@example.com",
                "Invalid Email", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // ── Duplicate email ───────────────────────────────────────────────────
        if (emailExists(email)) {
            JOptionPane.showMessageDialog(this,
                "This email is already registered!\nPlease use a different email address.",
                "Email Already Exists", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // ── Duplicate username ────────────────────────────────────────────────
        if (usernameExists(username)) {
            JOptionPane.showMessageDialog(this,
                "This username is already taken!\nPlease choose a different username.",
                "Username Already Exists", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // ── Password strength ─────────────────────────────────────────────────
        if (!isPasswordSecure(password)) {
            JOptionPane.showMessageDialog(this,
                "Password is not secure!\n\n"
                + "Password must:\n"
                + "• Be at least 6 characters long\n"
                + "• Contain at least one letter\n"
                + "• Contain at least one number",
                "Insecure Password", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // ── Insert with type = User, status = Pending ─────────────────────────
        config conf = new config();
        boolean success = conf.registerUser(firstname, lastname, username, email, password);

        if (success) {
            JOptionPane.showMessageDialog(this,
                "Registration Successful!\n\n"
                + "Your account has been created.\n"
                + "An admin must activate your account before you can log in.",
                "Account Created", JOptionPane.INFORMATION_MESSAGE);

            firstnamefield.setText("");
            lastnamefield.setText("");
            usernanefield.setText("");
            emailfield.setText("");
            passwordfield.setText("");

            new LoginForm().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Registration failed! Please try again.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_createbutton1MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        new LoginForm().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel5MouseClicked

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(RegisterForm.class.getName())
                .log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new RegisterForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JLabel create;
    private javax.swing.JLabel createbutton1;
    private javax.swing.JTextField emailfield;
    private javax.swing.JButton exitbutton;
    private javax.swing.JTextField firstnamefield;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField lastnamefield;
    private javax.swing.JLabel logo;
    private javax.swing.JPasswordField passwordfield;
    private javax.swing.JTextField usernanefield;
    // End of variables declaration//GEN-END:variables
}
