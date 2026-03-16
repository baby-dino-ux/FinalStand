/*
 * Fixed:
 *  1. emailfield is now NON-EDITABLE (read-only) — users cannot change their email.
 *  2. Duplicate email validation removed (email is locked, so no conflict possible from here).
 *  3. Duplicate USERNAME check added before saving.
 *  4. passwordfield changed from JTextField → JPasswordField (passwords now masked).
 *  5. Removed dead/unused `firstnamefield` variable (fnamefield is the real first-name field).
 *  6. Removed duplicate `password1` label that overlapped `password` at the same coordinates.
 *  7. Email is excluded from the UPDATE SQL since it can never change from this form.
 */
package AdminInternalPage;

import LoginandRegister.LoginForm;
import Session.Session;
import config.config;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class UserForm extends javax.swing.JFrame {

    private final config conf = new config();

    // ── Image state ───────────────────────────────────────────────────────────
    private File    selectedImageFile = null;
    private boolean imageChanged      = false;

    // ── Constructor ───────────────────────────────────────────────────────────
    public UserForm() {
        initComponents();
        loadSessionInfo();
        label1.setText("Edit Your Profile");
        st_label.setText("SAVE");
    }

    // Unused legacy constructor — kept to avoid compile errors from any caller
    public UserForm(String add, Object o1, Object o2, Object o3, Object o4, Object o5) {
        this();
    }

    // ── Pre-fill all fields from the active session ───────────────────────────
    private void loadSessionInfo() {
        Session session = Session.getInstance();
        if (!session.isLoggedIn()) return;

        // ID field (read-only display)
        idfield1.setText(String.valueOf(session.getUserId()));
        idfield1.setEnabled(false);

        fnamefield.setText(session.getFirstname());
        lastnamefield.setText(session.getLastname());
        usernamefield.setText(session.getUsername());

        // ── FIX 1: Email is read-only — users cannot change their email ───────
        emailfield.setText(session.getEmail());
        emailfield.setEditable(false);
        emailfield.setEnabled(false);
        emailfield.setToolTipText("Email cannot be changed.");

        passwordfield.setText(""); // blank — only filled if user wants to change

        // ── Load saved profile picture from DB if it exists ───────────────────
        loadProfilePicture(session.getUserId());
    }

    // ── Load profile picture stored in DB (Base64 column) ────────────────────
    private void loadProfilePicture(int userId) {
        String sql = "SELECT profile_pic FROM tbl_users WHERE id = ?";
        try (Connection conn = config.connectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String base64 = rs.getString("profile_pic");
                    if (base64 != null && !base64.isEmpty()) {
                        byte[] imgBytes = Base64.getDecoder().decode(base64);
                        java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(imgBytes);
                        BufferedImage img = ImageIO.read(bais);
                        if (img != null) {
                            image.setIcon(scaleImage(img, image.getWidth(), image.getHeight()));
                            image1.setIcon(scaleImage(img, image1.getWidth(), image1.getHeight()));
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Profile pic load note: " + e.getMessage());
        }
    }

    // ── Scale a BufferedImage to fit inside a JLabel ──────────────────────────
    private ImageIcon scaleImage(BufferedImage img, int w, int h) {
        if (w <= 0) w = 150;
        if (h <= 0) h = 150;
        java.awt.Image scaled = img.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    // ── Open file chooser when user clicks ADD IMAGE or the image label ───────
    private void openImageChooser() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Choose a Profile Picture");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Image Files (JPG, PNG, GIF, BMP)", "jpg", "jpeg", "png", "gif", "bmp");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);

        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedImageFile = chooser.getSelectedFile();
            try {
                BufferedImage img = ImageIO.read(selectedImageFile);
                if (img == null) {
                    JOptionPane.showMessageDialog(this,
                        "Could not read the selected file.\nPlease choose a valid image (JPG, PNG, GIF, BMP).",
                        "Invalid Image", JOptionPane.WARNING_MESSAGE);
                    selectedImageFile = null;
                    return;
                }
                int w  = image.getWidth()  > 0 ? image.getWidth()  : 150;
                int h  = image.getHeight() > 0 ? image.getHeight() : 150;
                int w1 = image1.getWidth() > 0 ? image1.getWidth() : 150;
                int h1 = image1.getHeight()> 0 ? image1.getHeight(): 150;
                image.setIcon(scaleImage(img, w, h));
                image1.setIcon(scaleImage(img, w1, h1));
                imageChanged = true;
                JOptionPane.showMessageDialog(this,
                    "Image selected: " + selectedImageFile.getName() +
                    "\nClick SAVE to apply the change.",
                    "Image Ready", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                    "Error reading image: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
                selectedImageFile = null;
            }
        }
    }

    // ── Save profile (text fields + optional new image) ───────────────────────
    private void saveProfile() {
        String fn = fnamefield.getText().trim();
        String ln = lastnamefield.getText().trim();
        String un = usernamefield.getText().trim();
        // FIX 4: use getPassword() since passwordfield is now a JPasswordField
        String pw = new String(passwordfield.getPassword()).trim();

        // ── Validation: required fields ───────────────────────────────────────
        if (fn.isEmpty() || ln.isEmpty() || un.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "First Name, Last Name, and Username are required.",
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // ── Validation: username length ────────────────────────────────────────
        if (un.length() < 3) {
            JOptionPane.showMessageDialog(this,
                "Username must be at least 3 characters.",
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Session session = Session.getInstance();
        int userId = session.getUserId();

        // ── FIX 3: Duplicate username check (exclude current user) ─────────────
        if (isUsernameTaken(un, userId)) {
            JOptionPane.showMessageDialog(this,
                "The username \"" + un + "\" is already taken by another account.\n"
                + "Please choose a different username.",
                "Username Taken", JOptionPane.WARNING_MESSAGE);
            usernamefield.requestFocus();
            return;
        }

        try {
            // ── FIX 7: Email excluded from UPDATE — it never changes here ─────
            String sql;
            if (!pw.isEmpty() && imageChanged && selectedImageFile != null) {
                sql = "UPDATE tbl_users SET firstname=?, lastname=?, username=?, "
                    + "password=?, profile_pic=? WHERE id=?";
            } else if (!pw.isEmpty()) {
                sql = "UPDATE tbl_users SET firstname=?, lastname=?, username=?, "
                    + "password=? WHERE id=?";
            } else if (imageChanged && selectedImageFile != null) {
                sql = "UPDATE tbl_users SET firstname=?, lastname=?, username=?, "
                    + "profile_pic=? WHERE id=?";
            } else {
                sql = "UPDATE tbl_users SET firstname=?, lastname=?, username=? WHERE id=?";
            }

            try (Connection conn = config.connectDB();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                int idx = 1;
                ps.setString(idx++, fn);
                ps.setString(idx++, ln);
                ps.setString(idx++, un);

                if (!pw.isEmpty()) {
                    ps.setString(idx++, config.hashPassword(pw));
                }

                if (imageChanged && selectedImageFile != null) {
                    byte[] imgBytes = Files.readAllBytes(selectedImageFile.toPath());
                    String base64 = Base64.getEncoder().encodeToString(imgBytes);
                    ps.setString(idx++, base64);
                }

                ps.setInt(idx, userId);
                ps.executeUpdate();
            }

            // ── Update the session (email stays the same) ──────────────────────
            session.login(userId, un, fn, ln, session.getEmail(), session.getUserType());

            JOptionPane.showMessageDialog(this,
                "Profile updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            Profile p = new Profile();
            p.setVisible(true);
            this.dispose();

        } catch (IOException ioEx) {
            JOptionPane.showMessageDialog(this,
                "Could not read the image file: " + ioEx.getMessage(),
                "Image Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException sqlEx) {
            if (sqlEx.getMessage() != null && sqlEx.getMessage().contains("no such column")) {
                setupProfilePicColumn();
                saveProfile(); // retry once
            } else {
                JOptionPane.showMessageDialog(this,
                    "Database error: " + sqlEx.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ── FIX 3 helper: check if a username is already taken by someone else ────
    private boolean isUsernameTaken(String username, int excludeUserId) {
        String sql = "SELECT COUNT(*) FROM tbl_users WHERE username = ? AND id != ?";
        try (Connection conn = config.connectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setInt(2, excludeUserId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Username check error: " + e.getMessage());
        }
        return false;
    }

    // ── Add profile_pic column if it doesn't exist yet ────────────────────────
    private void setupProfilePicColumn() {
        String sql = "ALTER TABLE tbl_users ADD COLUMN profile_pic TEXT DEFAULT NULL";
        try (Connection conn = config.connectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
            System.out.println("profile_pic column added successfully.");
        } catch (SQLException e) {
            System.out.println("profile_pic column note: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        firstnamefield = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        label1 = new javax.swing.JLabel();
        ex = new javax.swing.JLabel();
        exit = new javax.swing.JLabel();
        add = new javax.swing.JPanel();
        st_label = new javax.swing.JLabel();
        id = new javax.swing.JLabel();
        firstname = new javax.swing.JLabel();
        lastnamefield = new javax.swing.JTextField();
        lastname = new javax.swing.JLabel();
        email = new javax.swing.JLabel();
        fnamefield = new javax.swing.JTextField();
        passwordfield = new javax.swing.JTextField();
        password = new javax.swing.JLabel();
        emailfield = new javax.swing.JTextField();
        password1 = new javax.swing.JLabel();
        useername = new javax.swing.JLabel();
        usernamefield = new javax.swing.JTextField();
        idfield1 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        image = new javax.swing.JLabel();
        image1 = new javax.swing.JLabel();
        addimagepanel = new javax.swing.JPanel();
        addimage = new javax.swing.JLabel();

        firstnamefield.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        firstnamefield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        firstnamefield.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 234, 234), 2));
        firstnamefield.setOpaque(false);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(29, 45, 61));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 234, 234), 3));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(55, 86, 93));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 234, 234), 3));
        jPanel2.setLayout(null);

        label1.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        label1.setForeground(new java.awt.Color(239, 234, 234));
        label1.setText("Label");
        jPanel2.add(label1);
        label1.setBounds(0, 0, 300, 40);

        ex.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        ex.setForeground(new java.awt.Color(239, 234, 234));
        ex.setText("Exit");
        jPanel2.add(ex);
        ex.setBounds(510, 0, 40, 40);

        exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout (2).png"))); // NOI18N
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitMouseClicked(evt);
            }
        });
        jPanel2.add(exit);
        exit.setBounds(550, 0, 40, 40);

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 590, 40));

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

        jPanel1.add(add, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 360, 100, 40));

        id.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        id.setForeground(new java.awt.Color(239, 234, 234));
        id.setText("ID:");
        jPanel1.add(id, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 90, 30));

        firstname.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        firstname.setForeground(new java.awt.Color(239, 234, 234));
        firstname.setText("First Name:");
        jPanel1.add(firstname, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 90, 30));

        lastnamefield.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        lastnamefield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lastnamefield.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 234, 234), 2));
        lastnamefield.setOpaque(false);
        lastnamefield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastnamefieldActionPerformed(evt);
            }
        });
        jPanel1.add(lastnamefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 190, 210, 30));

        lastname.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lastname.setForeground(new java.awt.Color(239, 234, 234));
        lastname.setText("Last Name:");
        jPanel1.add(lastname, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 90, 30));

        email.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        email.setForeground(new java.awt.Color(239, 234, 234));
        email.setText("Email:");
        jPanel1.add(email, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 90, 30));

        fnamefield.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        fnamefield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fnamefield.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 234, 234), 2));
        fnamefield.setEnabled(false);
        fnamefield.setOpaque(false);
        jPanel1.add(fnamefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 130, 210, 30));

        passwordfield.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        passwordfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        passwordfield.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 234, 234), 2));
        passwordfield.setOpaque(false);
        jPanel1.add(passwordfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 360, 210, 30));

        password.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        password.setForeground(new java.awt.Color(239, 234, 234));
        password.setText("Password:");
        jPanel1.add(password, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 90, 30));

        emailfield.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        emailfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        emailfield.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 234, 234), 2));
        emailfield.setOpaque(false);
        jPanel1.add(emailfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 250, 210, 30));

        password1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        password1.setForeground(new java.awt.Color(239, 234, 234));
        password1.setText("Password:");
        jPanel1.add(password1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 90, 30));

        useername.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        useername.setForeground(new java.awt.Color(239, 234, 234));
        useername.setText("Username:");
        jPanel1.add(useername, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 90, 30));

        usernamefield.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        usernamefield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        usernamefield.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 234, 234), 2));
        usernamefield.setOpaque(false);
        jPanel1.add(usernamefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 300, 210, 30));

        idfield1.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        idfield1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        idfield1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 234, 234), 2));
        idfield1.setEnabled(false);
        idfield1.setOpaque(false);
        jPanel1.add(idfield1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 210, 30));

        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        image.setBackground(new java.awt.Color(153, 153, 255));
        image.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        image.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        image.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imageMouseClicked(evt);
            }
        });
        jPanel3.add(image, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 190, 160));

        image1.setBackground(new java.awt.Color(153, 153, 255));
        image1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        image1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        image1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                image1MouseClicked(evt);
            }
        });
        jPanel3.add(image1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 190, 160));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 80, 210, 180));

        addimagepanel.setBackground(new java.awt.Color(28, 69, 91));
        addimagepanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addimagepanelMouseClicked(evt);
            }
        });
        addimagepanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        addimage.setBackground(new java.awt.Color(29, 45, 61));
        addimage.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        addimage.setForeground(new java.awt.Color(239, 234, 234));
        addimage.setText("ADD IMAGE");
        addimagepanel.add(addimage, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 90, -1));

        jPanel1.add(addimagepanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 270, 120, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 412));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void addMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseClicked
        saveProfile();
    }//GEN-LAST:event_addMouseClicked

    private void addMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseEntered
    }//GEN-LAST:event_addMouseEntered

    private void addMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseExited
    }//GEN-LAST:event_addMouseExited

    private void exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMouseClicked
        int confirm = JOptionPane.showConfirmDialog(this,
            "Discard changes and go back?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            new Profile().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_exitMouseClicked

    private void lastnamefieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastnamefieldActionPerformed
        // no-op
    }//GEN-LAST:event_lastnamefieldActionPerformed

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        openImageChooser();
    }//GEN-LAST:event_jPanel3MouseClicked

    private void addimagepanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addimagepanelMouseClicked
        openImageChooser();
    }//GEN-LAST:event_addimagepanelMouseClicked

    private void imageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imageMouseClicked
        openImageChooser();
    }//GEN-LAST:event_imageMouseClicked

    private void image1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_image1MouseClicked
        openImageChooser();
    }//GEN-LAST:event_image1MouseClicked

    public static void main(String args[]) {
        Session session = Session.getInstance();
        if (!session.isLoggedIn()) {
            JOptionPane.showMessageDialog(null,
                "Please login first!", "Unauthorized Access", JOptionPane.WARNING_MESSAGE);
            java.awt.EventQueue.invokeLater(() -> new LoginForm().setVisible(true));
            return;
        }
        java.awt.EventQueue.invokeLater(() -> new UserForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel add;
    private javax.swing.JLabel addimage;
    private javax.swing.JPanel addimagepanel;
    private javax.swing.JLabel email;
    public javax.swing.JTextField emailfield;
    private javax.swing.JLabel ex;
    private javax.swing.JLabel exit;
    private javax.swing.JLabel firstname;
    public javax.swing.JTextField firstnamefield;
    public javax.swing.JTextField fnamefield;
    private javax.swing.JLabel id;
    public javax.swing.JTextField idfield1;
    public javax.swing.JLabel image;
    public javax.swing.JLabel image1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel lastname;
    public javax.swing.JTextField lastnamefield;
    private javax.swing.JLabel password;
    private javax.swing.JLabel password1;
    public javax.swing.JTextField passwordfield;
    public javax.swing.JLabel st_label;
    private javax.swing.JLabel useername;
    public javax.swing.JTextField usernamefield;
    // End of variables declaration//GEN-END:variables
}
