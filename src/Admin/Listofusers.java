package Admin;

import AdminInternalPage.Profile;
import AdminInternalPage.Form;
import AdminInternalPage.UserForm;
import LoginandRegister.LoginForm;
import Session.Session;
import config.config;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Listofusers extends javax.swing.JFrame {

      private config conf = new config();

    public Listofusers() {
        initComponents();
        loadSessionInfo(); // display the logged-in admin's name
        loadUsers("");
    }

    // ── Show logged-in admin name in the sidebar label ───────────────────────
    private void loadSessionInfo() {
        Session session = Session.getInstance();
        if (session.isLoggedIn()) {
            lblUsername.setText(session.getFullName());
        } else {
            lblUsername.setText("ADMIN");
        }
    }

    // ── Load / filter users — Admins are never shown in this list ───────────
    private void loadUsers(String keyword) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        String sql;
        if (keyword == null || keyword.trim().isEmpty()) {
            // Exclude Admin accounts entirely
            sql = "SELECT id, firstname, lastname, email, status, type FROM tbl_users " +
                  "WHERE type != 'Admin'";
        } else {
            // Exclude Admin accounts AND apply keyword filter
            sql = "SELECT id, firstname, lastname, email, status, type FROM tbl_users " +
                  "WHERE type != 'Admin' " +
                  "AND (firstname LIKE ? OR lastname LIKE ? OR email LIKE ? OR status LIKE ?)";
        }

        try (Connection conn = config.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (keyword != null && !keyword.trim().isEmpty()) {
                String kw = "%" + keyword.trim() + "%";
                pstmt.setString(1, kw);
                pstmt.setString(2, kw);
                pstmt.setString(3, kw);
                pstmt.setString(4, kw);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("email"),
                        rs.getString("status"),
                        rs.getString("type")
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading users: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── Fetch username for a given user ID (for Update pre-fill) ─────────────
    private String fetchUsername(String id) {
        try (Connection conn = config.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT username FROM tbl_users WHERE id = ?")) {
            pstmt.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("username");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching username: " + e.getMessage());
        }
        return "";
    }

    // ── Fetch type for a given user ID (for Update pre-fill) ─────────────────
    private String fetchType(String id) {
        try (Connection conn = config.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT type FROM tbl_users WHERE id = ?")) {
            pstmt.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("type");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching type: " + e.getMessage());
        }
        return "Staff"; // safe default
    }
  


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        listadmin = new javax.swing.JPanel();
        settings = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        userpanel = new javax.swing.JPanel();
        users = new javax.swing.JLabel();
        mstaffpanel = new javax.swing.JPanel();
        mstaff = new javax.swing.JLabel();
        memployeepanel = new javax.swing.JPanel();
        memployee = new javax.swing.JLabel();
        servicepanel = new javax.swing.JPanel();
        services = new javax.swing.JLabel();
        bookingpanel = new javax.swing.JPanel();
        booking = new javax.swing.JLabel();
        reportspanel = new javax.swing.JPanel();
        review = new javax.swing.JLabel();
        reports = new javax.swing.JLabel();
        dashpanel1 = new javax.swing.JPanel();
        dashboard1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lg = new javax.swing.JLabel();
        exit = new javax.swing.JLabel();
        logout1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        deletepanel = new javax.swing.JPanel();
        delete = new javax.swing.JLabel();
        updatepanel = new javax.swing.JPanel();
        update = new javax.swing.JLabel();
        addpanel = new javax.swing.JPanel();
        add = new javax.swing.JLabel();
        searchfield = new javax.swing.JTextField();
        search = new javax.swing.JLabel();
        approvepanel = new javax.swing.JPanel();
        approve = new javax.swing.JLabel();
        paymentpanel = new javax.swing.JPanel();
        payment = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        listadmin.setBackground(new java.awt.Color(55, 86, 93));
        listadmin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        settings.setBackground(new java.awt.Color(255, 255, 255));
        settings.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        settings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user (1).png"))); // NOI18N
        settings.setText("ACCOUNT");
        settings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                settingsMouseClicked(evt);
            }
        });
        listadmin.add(settings, new org.netbeans.lib.awtextra.AbsoluteConstraints(-220, 10, 290, 50));

        lblUsername.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lblUsername.setText("ADMIN");
        listadmin.add(lblUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 90, -1));

        userpanel.setBackground(new java.awt.Color(29, 45, 61));
        userpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userpanelMouseClicked(evt);
            }
        });
        userpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        users.setBackground(new java.awt.Color(29, 45, 61));
        users.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        users.setForeground(new java.awt.Color(239, 234, 234));
        users.setText("Users");
        userpanel.add(users, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 70, -1));

        listadmin.add(userpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 160, 40));

        mstaffpanel.setBackground(new java.awt.Color(29, 45, 61));
        mstaffpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mstaff.setBackground(new java.awt.Color(212, 226, 240));
        mstaff.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        mstaff.setForeground(new java.awt.Color(239, 234, 234));
        mstaff.setText("Manage Staff");
        mstaffpanel.add(mstaff, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 100, -1));

        listadmin.add(mstaffpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 160, 40));

        memployeepanel.setBackground(new java.awt.Color(29, 45, 61));
        memployeepanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        memployee.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        memployee.setForeground(new java.awt.Color(239, 234, 234));
        memployee.setText("Manage Employee");
        memployeepanel.add(memployee, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 120, -1));

        listadmin.add(memployeepanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, -1, 40));

        servicepanel.setBackground(new java.awt.Color(29, 45, 61));
        servicepanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                servicepanelMouseClicked(evt);
            }
        });
        servicepanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        services.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        services.setForeground(new java.awt.Color(239, 234, 234));
        services.setText("Services");
        servicepanel.add(services, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 60, -1));

        listadmin.add(servicepanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 160, 40));

        bookingpanel.setBackground(new java.awt.Color(29, 45, 61));
        bookingpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        booking.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        booking.setForeground(new java.awt.Color(239, 234, 234));
        booking.setText("Booking");
        bookingpanel.add(booking, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, -1));

        listadmin.add(bookingpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 160, 40));

        reportspanel.setBackground(new java.awt.Color(29, 45, 61));
        reportspanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        review.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        reportspanel.add(review, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 90, -1));

        reports.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        reports.setForeground(new java.awt.Color(239, 234, 234));
        reports.setText("Reports");
        reportspanel.add(reports, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 60, -1));

        listadmin.add(reportspanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 160, 40));

        dashpanel1.setBackground(new java.awt.Color(29, 45, 61));
        dashpanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashpanel1MouseClicked(evt);
            }
        });
        dashpanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dashboard1.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        dashboard1.setForeground(new java.awt.Color(239, 234, 234));
        dashboard1.setText("Dashboard");
        dashpanel1.add(dashboard1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 70, 20));

        listadmin.add(dashpanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 160, 40));

        getContentPane().add(listadmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, -1));

        jPanel4.setBackground(new java.awt.Color(232, 230, 230));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(29, 45, 61));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lg.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        lg.setForeground(new java.awt.Color(239, 234, 234));
        lg.setText("Log Out");
        jPanel1.add(lg, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, -1, 20));

        exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout (2).png"))); // NOI18N
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitMouseClicked(evt);
            }
        });
        jPanel1.add(exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, 30, 20));

        logout1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout (2).png"))); // NOI18N
        jPanel1.add(logout1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, 30, 20));

        jPanel4.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 640, 50));

        jPanel2.setBackground(new java.awt.Color(29, 45, 61));
        jPanel4.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 160, 100));

        table.setBackground(new java.awt.Color(55, 86, 93));
        table.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.blue, null, java.awt.Color.blue));
        table.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        table.setForeground(new java.awt.Color(153, 153, 255));
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "First Name", "Last Name", "Email", "Status", "Type"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(table);

        jPanel4.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, 630, 400));

        deletepanel.setBackground(new java.awt.Color(29, 45, 61));
        deletepanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deletepanelMouseClicked(evt);
            }
        });

        delete.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        delete.setForeground(new java.awt.Color(239, 234, 234));
        delete.setText("DELETE");
        deletepanel.add(delete);

        jPanel4.add(deletepanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, 70, 30));

        updatepanel.setBackground(new java.awt.Color(29, 45, 61));
        updatepanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updatepanelMouseClicked(evt);
            }
        });

        update.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        update.setForeground(new java.awt.Color(239, 234, 234));
        update.setText("UPDATE");
        updatepanel.add(update);

        jPanel4.add(updatepanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 60, 70, 30));

        addpanel.setBackground(new java.awt.Color(29, 45, 61));
        addpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addpanelMouseClicked(evt);
            }
        });

        add.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        add.setForeground(new java.awt.Color(239, 234, 234));
        add.setText("ADD");
        addpanel.add(add);

        jPanel4.add(addpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 60, 70, 30));

        searchfield.setBackground(new java.awt.Color(29, 45, 61));
        searchfield.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        searchfield.setForeground(new java.awt.Color(255, 255, 255));
        searchfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchfieldActionPerformed(evt);
            }
        });
        searchfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                searchfieldKeyTyped(evt);
            }
        });
        jPanel4.add(searchfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 60, 160, 30));

        search.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        search.setText("SEARCH");
        jPanel4.add(search, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 50, 60, 50));

        approvepanel.setBackground(new java.awt.Color(29, 45, 61));
        approvepanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                approvepanelMouseClicked(evt);
            }
        });

        approve.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        approve.setForeground(new java.awt.Color(239, 234, 234));
        approve.setText("APPROVE");
        approvepanel.add(approve);

        jPanel4.add(approvepanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 60, 70, 30));

        paymentpanel.setBackground(new java.awt.Color(29, 45, 61));
        paymentpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                paymentpanelMouseClicked(evt);
            }
        });
        paymentpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        payment.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        payment.setForeground(new java.awt.Color(239, 234, 234));
        payment.setText("Payment");
        paymentpanel.add(payment, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 70, 20));

        jPanel4.add(paymentpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 160, 40));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 490));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void settingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsMouseClicked
       Profile a = new Profile();
        a.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_settingsMouseClicked

    private void userpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userpanelMouseClicked

    }//GEN-LAST:event_userpanelMouseClicked

    private void searchfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchfieldActionPerformed
         loadUsers(searchfield.getText());
    }//GEN-LAST:event_searchfieldActionPerformed

    private void addpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addpanelMouseClicked
           Form form = new Form();
        form.setVisible(true);
        // Listofusers stays open; table refreshes when Form closes
        form.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent e) {
                loadUsers(""); // refresh table with new record
            }
        });
    }//GEN-LAST:event_addpanelMouseClicked

    private void updatepanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updatepanelMouseClicked
       int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to edit.",
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Safety guard — Admin accounts must never be edited here
        String selectedType = String.valueOf(table.getValueAt(row, 5));
        if ("Admin".equalsIgnoreCase(selectedType)) {
            JOptionPane.showMessageDialog(this,
                "Admin accounts cannot be edited from this panel.",
                "Not Allowed", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id        = String.valueOf(table.getValueAt(row, 0));
        String firstname = String.valueOf(table.getValueAt(row, 1));
        String lastname  = String.valueOf(table.getValueAt(row, 2));
        String email     = String.valueOf(table.getValueAt(row, 3));
        String status    = String.valueOf(table.getValueAt(row, 4));
        String username  = fetchUsername(id); // fetch from DB (not in table columns)
        String type      = fetchType(id);     // fetch from DB (not in table columns)

        Form form = new Form("Update", id, firstname, lastname, email, username, type, status);
        form.setVisible(true);
        // Listofusers stays open; table refreshes when Form closes
        form.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent e) {
                loadUsers(""); // refresh table with updated record
            }
        });
    }//GEN-LAST:event_updatepanelMouseClicked

    private void deletepanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deletepanelMouseClicked
           int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.",
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id   = String.valueOf(table.getValueAt(row, 0));
        String name = table.getValueAt(row, 1) + " " + table.getValueAt(row, 2);

        // Safety guard — Admin accounts must never be deleted here
        String selectedType = String.valueOf(table.getValueAt(row, 5));
        if ("Admin".equalsIgnoreCase(selectedType)) {
            JOptionPane.showMessageDialog(this,
                "Admin accounts cannot be deleted from this panel.",
                "Not Allowed", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Delete " + name + "? This cannot be undone.",
            "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            conf.deleteRecord("DELETE FROM tbl_users WHERE id = ?", Integer.parseInt(id));
            JOptionPane.showMessageDialog(this, name + " deleted successfully.",
                "Deleted", JOptionPane.INFORMATION_MESSAGE);
            loadUsers("");
        }
    }//GEN-LAST:event_deletepanelMouseClicked

    private void approvepanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_approvepanelMouseClicked
       int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to approve.",
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id     = String.valueOf(table.getValueAt(row, 0));
        String name   = table.getValueAt(row, 1) + " " + table.getValueAt(row, 2);
        String status = String.valueOf(table.getValueAt(row, 4));

        // Safety guard — Admin accounts must never be approved/modified here
        String selectedType = String.valueOf(table.getValueAt(row, 5));
        if ("Admin".equalsIgnoreCase(selectedType)) {
            JOptionPane.showMessageDialog(this,
                "Admin accounts cannot be modified from this panel.",
                "Not Allowed", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if ("Active".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(this, name + " is already Active.",
                "Already Active", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Approve and activate account for " + name + "?",
            "Confirm Approve", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            conf.updateRecord("UPDATE tbl_users SET status = 'Active' WHERE id = ?",
                Integer.parseInt(id));
            JOptionPane.showMessageDialog(this, name + "'s account is now Active!",
                "Approved", JOptionPane.INFORMATION_MESSAGE);
            loadUsers("");
        }
    }//GEN-LAST:event_approvepanelMouseClicked

    private void paymentpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paymentpanelMouseClicked
        AdminDash ad = new AdminDash();
        ad.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_paymentpanelMouseClicked

    private void exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMouseClicked
           int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to log out?",
            "Log Out", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Session.getInstance().logout();
            LoginForm lf = new LoginForm();
            lf.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_exitMouseClicked

    private void dashpanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashpanel1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_dashpanel1MouseClicked

    private void searchfieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchfieldKeyTyped
 loadUsers(searchfield.getText());       
    }//GEN-LAST:event_searchfieldKeyTyped

    private void servicepanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_servicepanelMouseClicked
        Services s = new Services();
        s.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_servicepanelMouseClicked

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
                new Listofusers().setVisible(true);
            }
        });
     
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel add;
    private javax.swing.JPanel addpanel;
    private javax.swing.JLabel approve;
    private javax.swing.JPanel approvepanel;
    private javax.swing.JLabel booking;
    private javax.swing.JPanel bookingpanel;
    private javax.swing.JLabel dashboard1;
    private javax.swing.JPanel dashpanel1;
    private javax.swing.JLabel delete;
    private javax.swing.JPanel deletepanel;
    private javax.swing.JLabel exit;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lg;
    private javax.swing.JPanel listadmin;
    private javax.swing.JLabel logout1;
    private javax.swing.JLabel memployee;
    private javax.swing.JPanel memployeepanel;
    private javax.swing.JLabel mstaff;
    private javax.swing.JPanel mstaffpanel;
    private javax.swing.JLabel payment;
    private javax.swing.JPanel paymentpanel;
    private javax.swing.JLabel reports;
    private javax.swing.JPanel reportspanel;
    private javax.swing.JLabel review;
    private javax.swing.JLabel search;
    private javax.swing.JTextField searchfield;
    private javax.swing.JPanel servicepanel;
    private javax.swing.JLabel services;
    private javax.swing.JLabel settings;
    private javax.swing.JTable table;
    private javax.swing.JLabel update;
    private javax.swing.JPanel updatepanel;
    private javax.swing.JPanel userpanel;
    private javax.swing.JLabel users;
    // End of variables declaration//GEN-END:variables

   
    }

    

