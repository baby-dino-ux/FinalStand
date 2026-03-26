/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StaffInternalPage;

import LoginandRegister.LoginForm;
import Session.Session;
import Staff.MyBookings;
import Staff.StaffDash;
import config.config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author ashlaran
 */
public class MyBookingForm extends javax.swing.JFrame {

      // ── Parameterized constructor — called from MyBookings UPDATE ─────────────
    public MyBookingForm(int bookingId, String customer, String service,
                          String employee, String date, String status,
                          MyBookings parent) {
        initComponents();           // UI must be built first
        this.bookingId   = bookingId;
        this.parentTable = parent;
        loadServicesCombo();
        loadEmployeesCombo();
        // Pre-fill the fields with existing booking data
        fullnamefield.setText(customer);
        bookingdatefield.setText(date);
        serviceslist.setSelectedItem(service);
        assignemployeelist.setSelectedItem(employee);
        // Fetch address / contact / email / tasknote from DB
        loadExtraFields(bookingId);
    }
 
    // ── Default constructor (NetBeans designer) ───────────────────────────────
    public MyBookingForm() {
        initComponents();
        loadServicesCombo();
        loadEmployeesCombo();
    }

    // ── ADD mode constructor — called from MyBookings ADD button ─────────────
    public MyBookingForm(MyBookings parent) {
        initComponents();
        this.parentTable = parent;
        this.bookingId   = 0;  // 0 = new booking → will INSERT on save
        loadServicesCombo();
        loadEmployeesCombo();
        // Clear the designer placeholder text from the fields
        fullnamefield.setText("");
        addressfield.setText("");
        contactnumberfield.setText("");
        emailaddressfield.setText("");
        bookingdatefield.setText("");
        tasknotefield.setText("");
    }

    // FIX: was throwing UnsupportedOperationException — delegate to full constructor
    public MyBookingForm(int id, String customer, String service, String employee, String date, MyBookings aThis) {
        this(id, customer, service, employee, date, "Pending", aThis);
    }
 
    // ── Load extra fields that are not passed via constructor ─────────────────
    private void loadExtraFields(int id) {
        String sql = "SELECT b_address, b_contact, b_email, b_tasknote " +
                     "FROM tbl_bookings WHERE b_id = ?";
        try (Connection conn = config.connectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String addr = rs.getString("b_address");
                    String cont = rs.getString("b_contact");
                    String mail = rs.getString("b_email");
                    String note = rs.getString("b_tasknote");
                    addressfield.setText(addr       != null ? addr : "");
                    contactnumberfield.setText(cont != null ? cont : "");
                    emailaddressfield.setText(mail  != null ? mail : "");
                    tasknotefield.setText(note      != null ? note : "");
                }
            }
        } catch (SQLException e) {
            System.out.println("MyBookingForm load error: " + e.getMessage());
        }
    }
 
    // ── Populate services dropdown (Active only) ──────────────────────────────
    private void loadServicesCombo() {
        serviceslist.removeAllItems();
        String sql = "SELECT s_name FROM tbl_services " +
                     "WHERE s_status = 'Active' ORDER BY s_name ASC";
        try (Connection conn = config.connectDB();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                serviceslist.addItem(rs.getString("s_name"));
            }
        } catch (SQLException e) {
            System.out.println("Services combo error: " + e.getMessage());
        }
        if (serviceslist.getItemCount() == 0) {
            serviceslist.addItem("No services available");
        }
    }
 
    // ── Populate employees dropdown (Available OR Busy) ───────────────────────
    private void loadEmployeesCombo() {
        assignemployeelist.removeAllItems();
        // FIX: query work_status (not status) — status is Active/Inactive, work_status is Available/Busy/Off Duty
        String sql = "SELECT (firstname || ' ' || lastname) AS fullname " +
                     "FROM tbl_users " +
                     "WHERE type = 'Employee' AND status = 'Active' " +
                     "AND (work_status = 'Available' OR work_status = 'Busy') " +
                     "ORDER BY firstname ASC";
        try (Connection conn = config.connectDB();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                assignemployeelist.addItem(rs.getString("fullname"));
            }
        } catch (SQLException e) {
            System.out.println("Employees combo error: " + e.getMessage());
        }
        if (assignemployeelist.getItemCount() == 0) {
            assignemployeelist.addItem("No employees available");
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        bookingdetails = new javax.swing.JLabel();
        tasknote = new javax.swing.JLabel();
        tasknotefield = new javax.swing.JTextField();
        address = new javax.swing.JLabel();
        addressfield = new javax.swing.JTextField();
        contactnumberfield = new javax.swing.JTextField();
        emailaddress = new javax.swing.JLabel();
        fullnamefield = new javax.swing.JTextField();
        assignemployeelist = new javax.swing.JComboBox<>();
        chooseservices = new javax.swing.JLabel();
        emailaddressfield = new javax.swing.JTextField();
        bookingdate = new javax.swing.JLabel();
        bookingdatefield = new javax.swing.JTextField();
        assigncleaner = new javax.swing.JLabel();
        customerdetails = new javax.swing.JLabel();
        statuspanel = new javax.swing.JPanel();
        status = new javax.swing.JLabel();
        fullname = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        reminder = new javax.swing.JLabel();
        createpanel = new javax.swing.JPanel();
        create = new javax.swing.JLabel();
        contactnumber = new javax.swing.JLabel();
        serviceslist = new javax.swing.JComboBox<>();

        // FIX: was EXIT_ON_CLOSE — closing the form was killing the entire application
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(29, 45, 61));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bookingdetails.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        bookingdetails.setForeground(new java.awt.Color(153, 255, 255));
        bookingdetails.setText("Booking Details");
        jPanel2.add(bookingdetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 0, 130, 30));

        tasknote.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        tasknote.setForeground(new java.awt.Color(255, 255, 255));
        tasknote.setText("Task Note (Optional)");
        jPanel2.add(tasknote, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 240, 130, 30));

        tasknotefield.setFont(new java.awt.Font("Bahnschrift", 0, 11)); // NOI18N
        tasknotefield.setForeground(new java.awt.Color(102, 102, 102));
        tasknotefield.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        tasknotefield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tasknotefieldActionPerformed(evt);
            }
        });
        jPanel2.add(tasknotefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 270, 260, 50));

        address.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        address.setForeground(new java.awt.Color(255, 255, 255));
        address.setText("Address");
        jPanel2.add(address, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 80, 30));

        addressfield.setFont(new java.awt.Font("Bahnschrift", 0, 11)); // NOI18N
        addressfield.setForeground(new java.awt.Color(102, 102, 102));
        addressfield.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        addressfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addressfieldActionPerformed(evt);
            }
        });
        jPanel2.add(addressfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 260, 30));

        contactnumberfield.setFont(new java.awt.Font("Bahnschrift", 0, 11)); // NOI18N
        contactnumberfield.setForeground(new java.awt.Color(102, 102, 102));
        contactnumberfield.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        contactnumberfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactnumberfieldActionPerformed(evt);
            }
        });
        jPanel2.add(contactnumberfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 260, 30));

        emailaddress.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        emailaddress.setForeground(new java.awt.Color(255, 255, 255));
        emailaddress.setText("Email Address");
        jPanel2.add(emailaddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 80, 30));

        fullnamefield.setFont(new java.awt.Font("Bahnschrift", 0, 11)); // NOI18N
        fullnamefield.setForeground(new java.awt.Color(102, 102, 102));
        fullnamefield.setText("e.g. John Kaisen");
        fullnamefield.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        fullnamefield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fullnamefieldActionPerformed(evt);
            }
        });
        jPanel2.add(fullnamefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 260, 30));

        assignemployeelist.setFont(new java.awt.Font("Bahnschrift", 0, 11)); // NOI18N
        assignemployeelist.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        assignemployeelist.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        assignemployeelist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignemployeelistActionPerformed(evt);
            }
        });
        jPanel2.add(assignemployeelist, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 200, 260, 30));

        chooseservices.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        chooseservices.setForeground(new java.awt.Color(255, 255, 255));
        chooseservices.setText("Choose Services");
        jPanel2.add(chooseservices, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, 100, 30));

        emailaddressfield.setFont(new java.awt.Font("Bahnschrift", 0, 11)); // NOI18N
        emailaddressfield.setForeground(new java.awt.Color(102, 102, 102));
        emailaddressfield.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        emailaddressfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailaddressfieldActionPerformed(evt);
            }
        });
        jPanel2.add(emailaddressfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 260, 30));

        bookingdate.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        bookingdate.setForeground(new java.awt.Color(255, 255, 255));
        bookingdate.setText("Booking Date");
        jPanel2.add(bookingdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 100, 100, 30));

        bookingdatefield.setFont(new java.awt.Font("Bahnschrift", 0, 11)); // NOI18N
        bookingdatefield.setForeground(new java.awt.Color(102, 102, 102));
        bookingdatefield.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        bookingdatefield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookingdatefieldActionPerformed(evt);
            }
        });
        jPanel2.add(bookingdatefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 130, 260, 30));

        assigncleaner.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        assigncleaner.setForeground(new java.awt.Color(255, 255, 255));
        assigncleaner.setText("Assign Cleaner");
        jPanel2.add(assigncleaner, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 170, 100, 30));

        customerdetails.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        customerdetails.setForeground(new java.awt.Color(153, 255, 255));
        customerdetails.setText("Customer Details");
        jPanel2.add(customerdetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 130, 30));

        statuspanel.setBackground(new java.awt.Color(0, 153, 0));
        statuspanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        statuspanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        status.setBackground(new java.awt.Color(153, 255, 255));
        status.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        status.setForeground(new java.awt.Color(153, 255, 255));
        status.setText("Status: Pending");
        statuspanel.add(status, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 0, 100, 20));

        jPanel2.add(statuspanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 130, 20));

        fullname.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        fullname.setForeground(new java.awt.Color(255, 255, 255));
        fullname.setText("Full Name");
        jPanel2.add(fullname, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 80, 30));

        jPanel5.setBackground(new java.awt.Color(28, 69, 100));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        reminder.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        reminder.setForeground(new java.awt.Color(255, 255, 255));
        reminder.setText("*All fields are required except Task Note");
        jPanel5.add(reminder, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 30));

        createpanel.setBackground(new java.awt.Color(153, 255, 255));
        createpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createpanelMouseClicked(evt);
            }
        });
        createpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        create.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        create.setText("Edit Booking");
        createpanel.add(create, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 100, 30));

        jPanel5.add(createpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, 130, -1));

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 340, 600, 50));

        contactnumber.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        contactnumber.setForeground(new java.awt.Color(255, 255, 255));
        contactnumber.setText("Contact Number");
        jPanel2.add(contactnumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 110, 30));

        serviceslist.setFont(new java.awt.Font("Bahnschrift", 0, 11)); // NOI18N
        serviceslist.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        serviceslist.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        serviceslist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serviceslistActionPerformed(evt);
            }
        });
        jPanel2.add(serviceslist, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 60, 260, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 410, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tasknotefieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tasknotefieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tasknotefieldActionPerformed

    private void addressfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addressfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addressfieldActionPerformed

    private void contactnumberfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contactnumberfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contactnumberfieldActionPerformed

    private void fullnamefieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fullnamefieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fullnamefieldActionPerformed

    private void assignemployeelistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignemployeelistActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_assignemployeelistActionPerformed

    private void emailaddressfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailaddressfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailaddressfieldActionPerformed

    private void bookingdatefieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookingdatefieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bookingdatefieldActionPerformed

    private void createpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createpanelMouseClicked
        String customer = fullnamefield.getText().trim();
        String addrVal  = addressfield.getText().trim();
        String contact  = contactnumberfield.getText().trim();
        String email    = emailaddressfield.getText().trim();
        String date     = bookingdatefield.getText().trim();
        String taskNote = tasknotefield.getText().trim();
        String service  = serviceslist.getSelectedItem() != null
                          ? serviceslist.getSelectedItem().toString() : "";
        String employee = assignemployeelist.getSelectedItem() != null
                          ? assignemployeelist.getSelectedItem().toString() : "";

        // Validation
        if (customer.isEmpty() || addrVal.isEmpty() || contact.isEmpty()
                || email.isEmpty() || date.isEmpty()
                || service.isEmpty() || service.equals("No services available")
                || employee.isEmpty() || employee.equals("No employees available")) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all required fields.",
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Look up the price for the chosen service
        double price = 0;
        try (java.sql.Connection conn = config.connectDB();
             java.sql.PreparedStatement ps = conn.prepareStatement(
                 "SELECT s_price FROM tbl_services WHERE s_name = ?")) {
            ps.setString(1, service);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) price = rs.getDouble("s_price");
            }
        } catch (SQLException e) {
            System.out.println("Price lookup error: " + e.getMessage());
        }

        // FIX: store username in b_staff (not full name)
        String staffUsername = Session.getInstance().getUsername();

        if (bookingId == 0) {
            // ── ADD MODE: INSERT a new booking ────────────────────────────────
            conf.addRecord(
                "INSERT INTO tbl_bookings " +
                "(b_customer, b_address, b_contact, b_email, b_date, " +
                " b_service, b_employee, b_staff, b_tasknote, b_price, b_status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'Pending')",
                customer, addrVal, contact, email, date,
                service, employee, staffUsername, taskNote, price);

            // Mark employee Busy
            conf.updateRecord(
                "UPDATE tbl_users SET work_status='Busy' WHERE " +
                "(firstname||' '||lastname)=? AND type='Employee'", employee);

            JOptionPane.showMessageDialog(this,
                "Booking created successfully!", "Created", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // ── UPDATE MODE: update existing booking ──────────────────────────
            conf.updateRecord(
                "UPDATE tbl_bookings " +
                "SET b_customer=?, b_address=?, b_contact=?, b_email=?, " +
                "    b_date=?, b_service=?, b_employee=?, b_tasknote=?, b_price=? " +
                "WHERE b_id=?",
                customer, addrVal, contact, email,
                date, service, employee, taskNote, price,
                bookingId);

            JOptionPane.showMessageDialog(this,
                "Booking updated successfully!", "Saved", JOptionPane.INFORMATION_MESSAGE);
        }

        if (parentTable != null) {
            parentTable.loadTable("");
        }
        this.dispose();
    }//GEN-LAST:event_createpanelMouseClicked

    private void serviceslistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serviceslistActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serviceslistActionPerformed

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
                new StaffDash().setVisible(true);
            }
        }); 
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel address;
    private javax.swing.JTextField addressfield;
    private javax.swing.JLabel assigncleaner;
    private javax.swing.JComboBox<String> assignemployeelist;
    private javax.swing.JLabel bookingdate;
    private javax.swing.JTextField bookingdatefield;
    private javax.swing.JLabel bookingdetails;
    private javax.swing.JLabel chooseservices;
    private javax.swing.JLabel contactnumber;
    private javax.swing.JTextField contactnumberfield;
    private javax.swing.JLabel create;
    private javax.swing.JPanel createpanel;
    private javax.swing.JLabel customerdetails;
    private javax.swing.JLabel emailaddress;
    private javax.swing.JTextField emailaddressfield;
    private javax.swing.JLabel fullname;
    private javax.swing.JTextField fullnamefield;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel reminder;
    private javax.swing.JComboBox<String> serviceslist;
    private javax.swing.JLabel status;
    private javax.swing.JPanel statuspanel;
    private javax.swing.JLabel tasknote;
    private javax.swing.JTextField tasknotefield;
    // End of variables declaration//GEN-END:variables
    private final config conf       = new config();
    private int          bookingId  = 0;
    private MyBookings   parentTable = null;}




