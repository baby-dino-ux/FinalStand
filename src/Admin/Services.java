/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import AdminInternalPage.Profile;
import AdminInternalPage.ServiceForm;
import LoginandRegister.LoginForm;
import Session.Session;
import config.config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
/**
 *
 * @author ashlaran
 */
public class Services extends javax.swing.JFrame {
  private final config conf = new config();
 
    public Services() {
        initComponents();
        if (Session.getInstance().isLoggedIn()) {
            lblUsername.setText(Session.getInstance().getUsername());
        }
        loadTable("");
        wireSearch();
    }
 
    // ── Load services table — called by ServiceForm after save ────────────────
    public void loadTable(String ignored) {
        conf.displayData(
            "SELECT s_id AS ID, s_name AS Name, s_description AS Description, " +
            "s_price AS Price, s_status AS Status FROM tbl_services ORDER BY s_id DESC",
            TableServices);
        // Update total count
        Object total = conf.getValue("SELECT COUNT(*) FROM tbl_services");
        totalservicesnum.setText(total != null ? total.toString() : "0");
    }
 
    private void wireSearch() {
        searchfield.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override public void keyReleased(java.awt.event.KeyEvent e) {
                String kw = searchfield.getText().trim();
                if (kw.isEmpty() || kw.equals("Search ")) { loadTable(""); return; }
                conf.displayData(
                    "SELECT s_id AS ID, s_name AS Name, s_description AS Description, " +
                    "s_price AS Price, s_status AS Status FROM tbl_services " +
                    "WHERE s_name LIKE ? ORDER BY s_id DESC",
                    TableServices, "%" + kw + "%");
            }
        });
    }
 
    // ── Get the service row values for the selected row ───────────────────────
    private Object[] getSelectedServiceData() {
        int row = TableServices.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a service.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        // columns: ID, Name, Description, Price, Status
        return new Object[]{
            TableServices.getValueAt(row, 0),
            TableServices.getValueAt(row, 1),
            TableServices.getValueAt(row, 2),
            TableServices.getValueAt(row, 3),
            TableServices.getValueAt(row, 4)
        };
    }
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lg = new javax.swing.JLabel();
        Servicesheader = new javax.swing.JLabel();
        exit = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        addpanel = new javax.swing.JPanel();
        add = new javax.swing.JLabel();
        updatepanel = new javax.swing.JPanel();
        update = new javax.swing.JLabel();
        deletepanel = new javax.swing.JPanel();
        delete = new javax.swing.JLabel();
        approvepanel = new javax.swing.JPanel();
        approve = new javax.swing.JLabel();
        border2 = new javax.swing.JPanel();
        servicesscrollpane = new javax.swing.JScrollPane();
        TableServices = new javax.swing.JTable();
        searchfield = new javax.swing.JTextField();
        skyblue = new javax.swing.JPanel();
        totalservicesnum = new javax.swing.JLabel();
        totalservices = new javax.swing.JLabel();
        border3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        refreshlist = new javax.swing.JLabel();
        listadmin = new javax.swing.JPanel();
        settings = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        dashpanel = new javax.swing.JPanel();
        dashboard1 = new javax.swing.JLabel();
        userpanel = new javax.swing.JPanel();
        users = new javax.swing.JLabel();
        employeepanel = new javax.swing.JPanel();
        employee = new javax.swing.JLabel();
        servicespanel = new javax.swing.JPanel();
        services = new javax.swing.JLabel();
        bookingspanel = new javax.swing.JPanel();
        bookings = new javax.swing.JLabel();
        reportspanel = new javax.swing.JPanel();
        reports = new javax.swing.JLabel();
        feedbackpanel = new javax.swing.JPanel();
        review = new javax.swing.JLabel();
        feedback = new javax.swing.JLabel();
        administrator = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(28, 69, 91));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(29, 45, 61));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lg.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        lg.setForeground(new java.awt.Color(239, 234, 234));
        lg.setText("Log Out");
        jPanel1.add(lg, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, -1, 20));

        Servicesheader.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        Servicesheader.setForeground(new java.awt.Color(239, 234, 234));
        Servicesheader.setText("Services");
        jPanel1.add(Servicesheader, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 190, 50));

        exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout (2).png"))); // NOI18N
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitMouseClicked(evt);
            }
        });
        jPanel1.add(exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, 30, 20));

        jPanel4.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 640, 50));

        jPanel2.setBackground(new java.awt.Color(29, 45, 61));
        jPanel4.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 150, 140));

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

        jPanel4.add(addpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 100, 60, 20));

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

        jPanel4.add(updatepanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 100, 60, 20));

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

        jPanel4.add(deletepanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 100, 60, 20));

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

        jPanel4.add(approvepanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 100, 70, 20));

        border2.setBackground(new java.awt.Color(153, 255, 255));
        border2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        border2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                border2MouseClicked(evt);
            }
        });
        border2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(border2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 630, 10));

        servicesscrollpane.setBackground(new java.awt.Color(29, 45, 61));
        servicesscrollpane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        servicesscrollpane.setForeground(new java.awt.Color(255, 255, 255));

        TableServices.setBackground(new java.awt.Color(29, 45, 61));
        TableServices.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        TableServices.setForeground(new java.awt.Color(255, 255, 255));
        TableServices.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Service ID", "Service Name", "Description", "Price"
            }
        ));
        servicesscrollpane.setViewportView(TableServices);

        jPanel4.add(servicesscrollpane, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 120, 610, 350));

        searchfield.setFont(new java.awt.Font("Bahnschrift", 0, 11)); // NOI18N
        searchfield.setForeground(new java.awt.Color(255, 255, 255));
        searchfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchfieldActionPerformed(evt);
            }
        });
        jPanel4.add(searchfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 100, 320, 20));

        skyblue.setBackground(new java.awt.Color(0, 153, 153));
        skyblue.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        skyblue.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        totalservicesnum.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        totalservicesnum.setForeground(new java.awt.Color(0, 255, 255));
        totalservicesnum.setText("0");
        skyblue.add(totalservicesnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 40, 30));

        totalservices.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        totalservices.setForeground(new java.awt.Color(0, 255, 255));
        totalservices.setText("Total Services");
        skyblue.add(totalservices, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 100, 30));

        jPanel4.add(skyblue, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 150, 30));

        border3.setBackground(new java.awt.Color(153, 255, 255));
        border3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                border3MouseClicked(evt);
            }
        });
        border3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(border3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 490));

        jPanel6.setBackground(new java.awt.Color(29, 45, 61));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        refreshlist.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        refreshlist.setForeground(new java.awt.Color(255, 255, 255));
        refreshlist.setText("Refresh List");
        jPanel6.add(refreshlist, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 90, 30));

        jPanel4.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 60, -1, 30));

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
        listadmin.add(settings, new org.netbeans.lib.awtextra.AbsoluteConstraints(-220, 10, 280, 50));

        lblUsername.setBackground(new java.awt.Color(153, 255, 255));
        lblUsername.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        lblUsername.setText("Admin");
        listadmin.add(lblUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 90, 20));

        dashpanel.setBackground(new java.awt.Color(29, 45, 61));
        dashpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashpanelMouseClicked(evt);
            }
        });
        dashpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dashboard1.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        dashboard1.setForeground(new java.awt.Color(239, 234, 234));
        dashboard1.setText("Dashboard");
        dashpanel.add(dashboard1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 70, 20));

        listadmin.add(dashpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 150, 40));

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

        listadmin.add(userpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 150, 40));

        employeepanel.setBackground(new java.awt.Color(29, 45, 61));
        employeepanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                employeepanelMouseClicked(evt);
            }
        });
        employeepanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        employee.setBackground(new java.awt.Color(212, 226, 240));
        employee.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        employee.setForeground(new java.awt.Color(239, 234, 234));
        employee.setText("Employee");
        employeepanel.add(employee, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 100, -1));

        listadmin.add(employeepanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 150, 40));

        servicespanel.setBackground(new java.awt.Color(29, 45, 61));
        servicespanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                servicespanelMouseClicked(evt);
            }
        });
        servicespanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        services.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        services.setForeground(new java.awt.Color(239, 234, 234));
        services.setText("Services");
        servicespanel.add(services, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 110, -1));

        listadmin.add(servicespanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, -1, 40));

        bookingspanel.setBackground(new java.awt.Color(29, 45, 61));
        bookingspanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bookingspanelMouseClicked(evt);
            }
        });
        bookingspanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bookings.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        bookings.setForeground(new java.awt.Color(239, 234, 234));
        bookings.setText("Bookings");
        bookingspanel.add(bookings, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 60, -1));

        listadmin.add(bookingspanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 150, 40));

        reportspanel.setBackground(new java.awt.Color(29, 45, 61));
        reportspanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reportspanelMouseClicked(evt);
            }
        });
        reportspanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        reports.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        reports.setForeground(new java.awt.Color(239, 234, 234));
        reports.setText("Reports");
        reportspanel.add(reports, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, -1));

        listadmin.add(reportspanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 150, 40));

        feedbackpanel.setBackground(new java.awt.Color(29, 45, 61));
        feedbackpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                feedbackpanelMouseClicked(evt);
            }
        });
        feedbackpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        review.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        feedbackpanel.add(review, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 90, -1));

        feedback.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        feedback.setForeground(new java.awt.Color(239, 234, 234));
        feedback.setText("Feedback");
        feedbackpanel.add(feedback, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 80, -1));

        listadmin.add(feedbackpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 150, 40));

        administrator.setBackground(new java.awt.Color(153, 255, 255));
        administrator.setFont(new java.awt.Font("Century Gothic", 1, 10)); // NOI18N
        administrator.setForeground(new java.awt.Color(153, 255, 255));
        administrator.setText("ADMINISTRATOR");
        listadmin.add(administrator, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 90, 20));

        jPanel4.add(listadmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 150, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 790, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 790, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 490, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void addpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addpanelMouseClicked
       ServiceForm sf = new ServiceForm(0, "", 0, "Active", this);
        sf.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        sf.setVisible(true);
    }//GEN-LAST:event_addpanelMouseClicked

    private void updatepanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updatepanelMouseClicked
          Object[] data = getSelectedServiceData();
        if (data == null) return;
        int    id      = Integer.parseInt(data[0].toString());
        String name    = data[1].toString();
        double price   = Double.parseDouble(data[3].toString());
        String status  = data[4].toString();
        ServiceForm sf = new ServiceForm(id, name, price, status, this);
        sf.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        sf.setVisible(true);
    }//GEN-LAST:event_updatepanelMouseClicked

    private void deletepanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deletepanelMouseClicked
         Object[] data = getSelectedServiceData();
        if (data == null) return;
        int id = Integer.parseInt(data[0].toString());
        String name = data[1].toString();
        int confirm = JOptionPane.showConfirmDialog(this,
            "Delete service \"" + name + "\"? This cannot be undone.",
            "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm != JOptionPane.YES_OPTION) return;
        conf.deleteRecord("DELETE FROM tbl_services WHERE s_id = ?", id);
        JOptionPane.showMessageDialog(this, "Service deleted.", "Done", JOptionPane.INFORMATION_MESSAGE);
        loadTable("");
    }//GEN-LAST:event_deletepanelMouseClicked

    private void approvepanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_approvepanelMouseClicked
         // Toggle Active ↔ Inactive
        Object[] data = getSelectedServiceData();
        if (data == null) return;
        int    id        = Integer.parseInt(data[0].toString());
        String curStatus = data[4].toString();
        String newStatus = "Active".equalsIgnoreCase(curStatus) ? "Inactive" : "Active";
        conf.updateRecord("UPDATE tbl_services SET s_status = ? WHERE s_id = ?", newStatus, id);
        JOptionPane.showMessageDialog(this, "Service status changed to " + newStatus + ".", "Status Updated", JOptionPane.INFORMATION_MESSAGE);
        loadTable("");
    }//GEN-LAST:event_approvepanelMouseClicked

    private void border2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_border2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_border2MouseClicked

    private void searchfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchfieldActionPerformed

    private void border3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_border3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_border3MouseClicked

    private void settingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsMouseClicked
        Profile p = new Profile();
        p.SetVisible(true);
        this.dispose();
    }//GEN-LAST:event_settingsMouseClicked

    private void dashpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashpanelMouseClicked
        AdminDash a = new AdminDash();
        a.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_dashpanelMouseClicked

    private void userpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userpanelMouseClicked
        Users u = new Users();
        u.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_userpanelMouseClicked

    private void employeepanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employeepanelMouseClicked
        Employee e = new Employee();
        e.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_employeepanelMouseClicked

    private void servicespanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_servicespanelMouseClicked
        Services s  = new Services();
        s.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_servicespanelMouseClicked

    private void bookingspanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookingspanelMouseClicked
        Bookings b = new Bookings();
        b.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_bookingspanelMouseClicked

    private void reportspanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportspanelMouseClicked
        Reports r = new Reports();
        r.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_reportspanelMouseClicked

    private void feedbackpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_feedbackpanelMouseClicked
        Feedback f = new Feedback();
        f.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_feedbackpanelMouseClicked

    private void exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMouseClicked
        int c = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);
        if (c == JOptionPane.YES_OPTION) {
            Session.getInstance().logout();
            new LoginForm().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_exitMouseClicked

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        loadTable("");
    }//GEN-LAST:event_jPanel6MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
         Session session = Session.getInstance();
        if (!session.isLoggedIn()) {
            JOptionPane.showMessageDialog(null, "Please login first!", "Unauthorized Access", JOptionPane.WARNING_MESSAGE);
            java.awt.EventQueue.invokeLater(() -> new LoginForm().setVisible(true)); return;
        }
        java.awt.EventQueue.invokeLater(() -> new Services().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Servicesheader;
    private javax.swing.JTable TableServices;
    private javax.swing.JLabel add;
    private javax.swing.JPanel addpanel;
    private javax.swing.JLabel administrator;
    private javax.swing.JLabel approve;
    private javax.swing.JPanel approvepanel;
    private javax.swing.JLabel bookings;
    private javax.swing.JPanel bookingspanel;
    private javax.swing.JPanel border2;
    private javax.swing.JPanel border3;
    private javax.swing.JLabel dashboard1;
    private javax.swing.JPanel dashpanel;
    private javax.swing.JLabel delete;
    private javax.swing.JPanel deletepanel;
    private javax.swing.JLabel employee;
    private javax.swing.JPanel employeepanel;
    private javax.swing.JLabel exit;
    private javax.swing.JLabel feedback;
    private javax.swing.JPanel feedbackpanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lg;
    private javax.swing.JPanel listadmin;
    private javax.swing.JLabel refreshlist;
    private javax.swing.JLabel reports;
    private javax.swing.JPanel reportspanel;
    private javax.swing.JLabel review;
    private javax.swing.JTextField searchfield;
    private javax.swing.JLabel services;
    private javax.swing.JPanel servicespanel;
    private javax.swing.JScrollPane servicesscrollpane;
    private javax.swing.JLabel settings;
    private javax.swing.JPanel skyblue;
    private javax.swing.JLabel totalservices;
    private javax.swing.JLabel totalservicesnum;
    private javax.swing.JLabel update;
    private javax.swing.JPanel updatepanel;
    private javax.swing.JPanel userpanel;
    private javax.swing.JLabel users;
    // End of variables declaration//GEN-END:variables

   
}
