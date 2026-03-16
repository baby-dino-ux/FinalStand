/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AdminInternalPage;


import Admin.AdminDash;
import Employee.EmployeeDash;
import LoginandRegister.LoginForm;
import Staff.StaffDash;
import Session.Session;
import config.config;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


public class Profile extends javax.swing.JFrame {

    
    // ── Constructor ───────────────────────────────────────────────────────────
    public Profile() {
        initComponents();
        loadSessionInfo();    // fill text labels
        loadProfilePicture(); // fill image
    }
 
    // ── Populate labels from the active session ───────────────────────────────
    private void loadSessionInfo() {
        Session session = Session.getInstance();
        if (session.isLoggedIn()) {
            lblid.setText(String.valueOf(session.getUserId()));
            lblfirstname.setText(session.getFirstname());
            lbllastname.setText(session.getLastname());
            lblemail.setText(session.getEmail());
            lblrole.setText(session.getUserType());
            username.setText(session.getUsername());
        } else {
            lblid.setText("—");
            lblfirstname.setText("—");
            lbllastname.setText("—");
            lblemail.setText("—");
            lblrole.setText("—");
            username.setText("—");
        }
    }
 
    // ── Load profile picture stored as Base64 in DB ───────────────────────────
    private void loadProfilePicture() {
        Session session = Session.getInstance();
        if (!session.isLoggedIn()) return;
 
        String sql = "SELECT profile_pic FROM tbl_users WHERE id = ?";
        try (Connection conn = config.connectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setInt(1, session.getUserId());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String base64 = rs.getString("profile_pic");
                    if (base64 != null && !base64.isEmpty()) {
                        byte[] imgBytes = Base64.getDecoder().decode(base64);
                        BufferedImage img = ImageIO.read(new ByteArrayInputStream(imgBytes));
                        if (img != null) {
                            int w = image.getWidth()  > 0 ? image.getWidth()  : 190;
                            int h = image.getHeight() > 0 ? image.getHeight() : 160;
                            java.awt.Image scaled =
                                img.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
                            image.setIcon(new ImageIcon(scaled));
                            image.setText(""); // clear any placeholder text
                        }
                    } else {
                        image.setText("No Photo");
                        image.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                    }
                }
            }
        } catch (Exception e) {
            image.setText("No Photo");
            image.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            System.out.println("Profile pic load note: " + e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        listadmin = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        exit = new javax.swing.JLabel();
        profile = new javax.swing.JLabel();
        ex = new javax.swing.JLabel();
        staffpanel = new javax.swing.JPanel();
        role = new javax.swing.JLabel();
        lastname = new javax.swing.JLabel();
        yourid = new javax.swing.JLabel();
        lblid = new javax.swing.JLabel();
        lblfirstname = new javax.swing.JLabel();
        lbllastname = new javax.swing.JLabel();
        firstname = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        username = new javax.swing.JLabel();
        lblrole = new javax.swing.JLabel();
        email = new javax.swing.JLabel();
        lblemail = new javax.swing.JLabel();
        editprofilepanel = new javax.swing.JPanel();
        edityourprofile = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        image = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        listadmin.setBackground(new java.awt.Color(29, 45, 61));
        listadmin.setForeground(new java.awt.Color(255, 255, 255));
        listadmin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(55, 86, 93));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout (2).png"))); // NOI18N
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitMouseClicked(evt);
            }
        });
        jPanel1.add(exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, 30, 30));

        profile.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        profile.setForeground(new java.awt.Color(239, 234, 234));
        profile.setText("Profile");
        jPanel1.add(profile, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 110, -1));

        ex.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        ex.setForeground(new java.awt.Color(239, 234, 234));
        ex.setText("Exit");
        jPanel1.add(ex, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, 50, 30));

        listadmin.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 590, 50));

        staffpanel.setBackground(new java.awt.Color(29, 45, 61));
        staffpanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        staffpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        role.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        role.setForeground(new java.awt.Color(239, 234, 234));
        role.setText("Role :");
        staffpanel.add(role, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, -1, -1));

        lastname.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        lastname.setForeground(new java.awt.Color(239, 234, 234));
        lastname.setText("Last Name :");
        staffpanel.add(lastname, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, -1));

        yourid.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        yourid.setForeground(new java.awt.Color(239, 234, 234));
        yourid.setText("Your ID :");
        staffpanel.add(yourid, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        lblid.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        lblid.setForeground(new java.awt.Color(239, 234, 234));
        lblid.setText("id");
        staffpanel.add(lblid, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 150, -1));

        lblfirstname.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        lblfirstname.setForeground(new java.awt.Color(239, 234, 234));
        lblfirstname.setText("firstname");
        staffpanel.add(lblfirstname, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 110, 150, 20));

        lbllastname.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        lbllastname.setForeground(new java.awt.Color(239, 234, 234));
        lbllastname.setText("lastname");
        staffpanel.add(lbllastname, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 140, 160, 20));

        firstname.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        firstname.setForeground(new java.awt.Color(239, 234, 234));
        firstname.setText("First Name :");
        staffpanel.add(firstname, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));
        staffpanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 100, -1, -1));

        username.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        username.setForeground(new java.awt.Color(239, 234, 234));
        username.setText("Username");
        staffpanel.add(username, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 200, 140, -1));

        lblrole.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        lblrole.setForeground(new java.awt.Color(239, 234, 234));
        lblrole.setText("role");
        staffpanel.add(lblrole, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 200, 90, 20));

        email.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        email.setForeground(new java.awt.Color(239, 234, 234));
        email.setText("Email :");
        staffpanel.add(email, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, -1));

        lblemail.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        lblemail.setForeground(new java.awt.Color(239, 234, 234));
        lblemail.setText("email");
        staffpanel.add(lblemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, 200, 20));

        editprofilepanel.setBackground(new java.awt.Color(55, 86, 93));
        editprofilepanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editprofilepanelMouseClicked(evt);
            }
        });
        editprofilepanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        edityourprofile.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        edityourprofile.setForeground(new java.awt.Color(239, 234, 234));
        edityourprofile.setText("Edit Your Profile");
        editprofilepanel.add(edityourprofile, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 170, 20));

        staffpanel.add(editprofilepanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 280, 170, 40));

        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        image.setBackground(new java.awt.Color(102, 102, 255));
        image.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        image.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        image.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imageMouseClicked(evt);
            }
        });
        jPanel3.add(image, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 190, 160));

        staffpanel.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 210, 180));

        listadmin.add(staffpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 510, 340));

        jPanel4.setBackground(new java.awt.Color(232, 230, 230));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 590, 490));

        getContentPane().add(listadmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 590, 490));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void editprofilepanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editprofilepanelMouseClicked
               UserForm u = new UserForm();
        u.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_editprofilepanelMouseClicked

    private void exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMouseClicked
        Session session = Session.getInstance();
        String userType = session.getUserType();
        if ("Admin".equalsIgnoreCase(userType)) {
            new AdminDash().setVisible(true);
        } else if ("Employee".equalsIgnoreCase(userType)) {
            new EmployeeDash().setVisible(true);
        } else {
            new StaffDash().setVisible(true);
        }
        this.dispose();
    }//GEN-LAST:event_exitMouseClicked

    private void imageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imageMouseClicked
       
    }//GEN-LAST:event_imageMouseClicked

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
       imageMouseClicked(evt);
    }//GEN-LAST:event_jPanel3MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
     Session session = Session.getInstance();
        if (!session.isLoggedIn()) {
            JOptionPane.showMessageDialog(null,
                "Please login first!", "Unauthorized Access", JOptionPane.WARNING_MESSAGE);
            java.awt.EventQueue.invokeLater(() -> new LoginForm().setVisible(true));
            return;
        }
        java.awt.EventQueue.invokeLater(() -> new Profile().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel editprofilepanel;
    private javax.swing.JLabel edityourprofile;
    private javax.swing.JLabel email;
    private javax.swing.JLabel ex;
    private javax.swing.JLabel exit;
    private javax.swing.JLabel firstname;
    public javax.swing.JLabel image;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lastname;
    private javax.swing.JLabel lblemail;
    private javax.swing.JLabel lblfirstname;
    private javax.swing.JLabel lblid;
    private javax.swing.JLabel lbllastname;
    private javax.swing.JLabel lblrole;
    private javax.swing.JPanel listadmin;
    private javax.swing.JLabel profile;
    private javax.swing.JLabel role;
    private javax.swing.JPanel staffpanel;
    private javax.swing.JLabel username;
    private javax.swing.JLabel yourid;
    // End of variables declaration//GEN-END:variables

    }

