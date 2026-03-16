/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import AdminInternalPage.Form;
import LoginandRegister.LoginForm;
import Session.Session;
import config.config;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
public class Users extends javax.swing.JFrame {

    
    private final config conf = new config();
    private String currentFilter = "All"; // Track current filter

    /**
     * Creates new form Users
     */
    public Users() {
        initComponents();
        wireButtonGroups();
        loadAllUsers(); // Load all users on startup
        setupComboBox(); // Setup filter combobox
    }

    // ── Setup ComboBox with filter options ─────────────────────────────────
    private void setupComboBox() {
        reporttypecombobox1.removeAllItems();
        reporttypecombobox1.addItem("All Users");
        reporttypecombobox1.addItem("Staff");
        reporttypecombobox1.addItem("Employee");
        reporttypecombobox1.addItem("Pending");
        reporttypecombobox1.addItem("Active");
        reporttypecombobox1.addItem("Inactive");

        reporttypecombobox1.addActionListener(evt -> {
            String selected = (String) reporttypecombobox1.getSelectedItem();
            filterUsers(selected);
        });
    }

    // ── Load all users into the table ─────────────────────────────────────
    private void loadAllUsers() {
        String sql = "SELECT id, firstname, lastname, email, status, type FROM tbl_users ORDER BY id DESC";
        conf.displayData(sql, TableUsers);
        updateUserCounts();
    }

    // ── Filter users based on type or status ──────────────────────────────
    private void filterUsers(String filterType) {
        String sql;

        switch (filterType) {
            case "Staff":
                sql = "SELECT id, firstname, lastname, email, status, type FROM tbl_users WHERE type='Staff' ORDER BY id DESC";
                break;
            case "Employee":
                sql = "SELECT id, firstname, lastname, email, status, type FROM tbl_users WHERE type='Employee' ORDER BY id DESC";
                break;
            case "Pending":
                sql = "SELECT id, firstname, lastname, email, status, type FROM tbl_users WHERE status='Pending' ORDER BY id DESC";
                break;
            case "Active":
                sql = "SELECT id, firstname, lastname, email, status, type FROM tbl_users WHERE status='Active' ORDER BY id DESC";
                break;
            case "Inactive":
                sql = "SELECT id, firstname, lastname, email, status, type FROM tbl_users WHERE status='Inactive' ORDER BY id DESC";
                break;
            default: // "All Users"
                sql = "SELECT id, firstname, lastname, email, status, type FROM tbl_users ORDER BY id DESC";
        }

        conf.displayData(sql, TableUsers);
        updateUserCounts();
    }

    // ── Update summary panels with current counts ───────────────────────
    private void updateUserCounts() {
        try {
            // Total Users
            Object totalObj = conf.getValue("SELECT COUNT(*) FROM tbl_users WHERE type != 'Admin'");
            totalusersnum.setText(totalObj != null ? totalObj.toString() : "0");

            // Staff count
            Object staffObj = conf.getValue("SELECT COUNT(*) FROM tbl_users WHERE type='Staff'");
            staffnum.setText(staffObj != null ? staffObj.toString() : "0");

            // Employee count
            Object empObj = conf.getValue("SELECT COUNT(*) FROM tbl_users WHERE type='Employee'");
            cleanernum.setText(empObj != null ? empObj.toString() : "0");

        } catch (Exception e) {
            System.out.println("Error updating counts: " + e.getMessage());
        }
    }

    // ── Get selected user ID from table ───────────────────────────────────
    private int getSelectedUserId() {
        int selectedRow = TableUsers.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                "Please select a user from the table.",
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return -1;
        }

        try {
            return Integer.parseInt(TableUsers.getValueAt(selectedRow, 0).toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error reading user ID.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }

    // ── Get user data from table row ──────────────────────────────────────
    private Object[] getSelectedUserData() {
        int selectedRow = TableUsers.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                "Please select a user from the table.",
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        Object[] data = new Object[6];
        for (int i = 0; i < 6; i++) {
            data[i] = TableUsers.getValueAt(selectedRow, i);
        }
        return data;
    }

    // ── ADD: Open Form.java in Add mode ───────────────────────────────────
    private void handleAdd() {
        Form addForm = new Form();
        addForm.setVisible(true);

        // After form closes, refresh table
        this.loadAllUsers();
    }

    // ── UPDATE: Open Form.java with pre-filled data ──────────────────────
    private void handleUpdate() {
        int userId = getSelectedUserId();
        if (userId < 0) return;

        Object[] userData = getSelectedUserData();
        if (userData == null) return;

        // userData: [id, firstname, lastname, email, status, type]
        String id = userData[0].toString();
        String firstname = userData[1].toString();
        String lastname = userData[2].toString();
        String email = userData[3].toString();
        String status = userData[4].toString();
        String type = userData[5].toString();

        // Open Form in Update mode with pre-filled data
        Form updateForm = new Form("Update", id, firstname, lastname, email, "dummy_username", type, status);
        updateForm.setVisible(true);

        // After form closes, refresh table
        this.loadAllUsers();
    }

    // ── DELETE: Remove user from database ────────────────────────────────
    private void handleDelete() {
        int userId = getSelectedUserId();
        if (userId < 0) return;

        Object[] userData = getSelectedUserData();
        if (userData == null) return;

        String userName = userData[1] + " " + userData[2]; // firstname + lastname

        // Confirm deletion
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete user: " + userName + "?\n\nThis action cannot be undone.",
            "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm != JOptionPane.YES_OPTION) return;

        // Delete from database
        String sql = "DELETE FROM tbl_users WHERE id=?";
        conf.deleteRecord(sql, userId);

        JOptionPane.showMessageDialog(this,
            "User '" + userName + "' has been deleted successfully.",
            "Success", JOptionPane.INFORMATION_MESSAGE);

        // Refresh table
        this.loadAllUsers();
    }

    // ── APPROVE: Change status from Pending to Active ────────────────────
    private void handleApprove() {
        int userId = getSelectedUserId();
        if (userId < 0) return;

        Object[] userData = getSelectedUserData();
        if (userData == null) return;

        String status = userData[4].toString();
        String userName = userData[1] + " " + userData[2];

        // Check if user is already active
        if ("Active".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(this,
                "User '" + userName + "' is already active.",
                "Already Active", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Update status to Active
        String sql = "UPDATE tbl_users SET status='Active' WHERE id=?";
        conf.updateRecord(sql, userId);

        JOptionPane.showMessageDialog(this,
            "User '" + userName + "' has been approved and is now Active.",
            "User Approved", JOptionPane.INFORMATION_MESSAGE);

        // Refresh table
        this.loadAllUsers();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lg = new javax.swing.JLabel();
        exit = new javax.swing.JLabel();
        logout1 = new javax.swing.JLabel();
        admindashboard = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        deletepanel = new javax.swing.JPanel();
        delete = new javax.swing.JLabel();
        updatepanel = new javax.swing.JPanel();
        update = new javax.swing.JLabel();
        addpanel = new javax.swing.JPanel();
        add = new javax.swing.JLabel();
        approvepanel = new javax.swing.JPanel();
        approve = new javax.swing.JLabel();
        border2 = new javax.swing.JPanel();
        border3 = new javax.swing.JPanel();
        usersscrollpane = new javax.swing.JScrollPane();
        TableUsers = new javax.swing.JTable();
        searchfield = new javax.swing.JTextField();
        skyblue = new javax.swing.JPanel();
        totalusersnum = new javax.swing.JLabel();
        totalusers = new javax.swing.JLabel();
        yellow = new javax.swing.JPanel();
        staffnum = new javax.swing.JLabel();
        staff = new javax.swing.JLabel();
        red = new javax.swing.JPanel();
        cleanernum = new javax.swing.JLabel();
        Employee = new javax.swing.JLabel();
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
        reporttypecombobox1 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(28, 69, 91));
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

        admindashboard.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        admindashboard.setForeground(new java.awt.Color(239, 234, 234));
        admindashboard.setText("Users");
        jPanel1.add(admindashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 180, 50));

        jPanel4.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 630, 50));

        jPanel2.setBackground(new java.awt.Color(29, 45, 61));
        jPanel4.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 150, 140));

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
        border2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(border2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 630, 10));

        border3.setBackground(new java.awt.Color(153, 255, 255));
        border3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(border3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 490));

        usersscrollpane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        TableUsers.setBackground(new java.awt.Color(29, 45, 61));
        TableUsers.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        TableUsers.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        TableUsers.setForeground(new java.awt.Color(255, 255, 255));
        TableUsers.setModel(new javax.swing.table.DefaultTableModel(
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
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        usersscrollpane.setViewportView(TableUsers);

        jPanel4.add(usersscrollpane, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 120, 610, 350));

        searchfield.setFont(new java.awt.Font("Bahnschrift", 0, 11)); // NOI18N
        searchfield.setText("Search ");
        jPanel4.add(searchfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 100, 330, 20));

        skyblue.setBackground(new java.awt.Color(0, 153, 153));
        skyblue.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        skyblue.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        totalusersnum.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        totalusersnum.setForeground(new java.awt.Color(0, 255, 255));
        totalusersnum.setText("0");
        skyblue.add(totalusersnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 40, 30));

        totalusers.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        totalusers.setForeground(new java.awt.Color(0, 255, 255));
        totalusers.setText("Total Users");
        skyblue.add(totalusers, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 80, 30));

        jPanel4.add(skyblue, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 140, 30));

        yellow.setBackground(new java.awt.Color(153, 153, 0));
        yellow.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        yellow.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        staffnum.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        staffnum.setForeground(new java.awt.Color(255, 204, 102));
        staffnum.setText("0");
        yellow.add(staffnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 30, 30));

        staff.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        staff.setForeground(new java.awt.Color(255, 204, 102));
        staff.setText("Staff");
        yellow.add(staff, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 50, 30));

        jPanel4.add(yellow, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 70, 90, 30));

        red.setBackground(new java.awt.Color(153, 0, 51));
        red.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        red.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cleanernum.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        cleanernum.setForeground(new java.awt.Color(255, 102, 102));
        cleanernum.setText("0");
        red.add(cleanernum, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 20, 30));

        Employee.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        Employee.setForeground(new java.awt.Color(255, 102, 102));
        Employee.setText("Employee");
        red.add(Employee, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 70, 30));

        jPanel4.add(red, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 70, -1, 30));

        listadmin.setBackground(new java.awt.Color(55, 86, 93));
        listadmin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        settings.setBackground(new java.awt.Color(255, 255, 255));
        settings.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        settings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user (1).png"))); // NOI18N
        settings.setText("ACCOUNT");
        listadmin.add(settings, new org.netbeans.lib.awtextra.AbsoluteConstraints(-220, 10, 280, 50));

        lblUsername.setBackground(new java.awt.Color(153, 255, 255));
        lblUsername.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        lblUsername.setText("Admin");
        listadmin.add(lblUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 90, 20));

        dashpanel.setBackground(new java.awt.Color(29, 45, 61));
        dashpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dashboard1.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        dashboard1.setForeground(new java.awt.Color(239, 234, 234));
        dashboard1.setText("Dashboard");
        dashpanel.add(dashboard1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 70, 20));

        listadmin.add(dashpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 150, 40));

        userpanel.setBackground(new java.awt.Color(29, 45, 61));
        userpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        users.setBackground(new java.awt.Color(29, 45, 61));
        users.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        users.setForeground(new java.awt.Color(239, 234, 234));
        users.setText("Users");
        userpanel.add(users, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 70, -1));

        listadmin.add(userpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 150, 40));

        employeepanel.setBackground(new java.awt.Color(29, 45, 61));
        employeepanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        employee.setBackground(new java.awt.Color(212, 226, 240));
        employee.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        employee.setForeground(new java.awt.Color(239, 234, 234));
        employee.setText("Employee");
        employeepanel.add(employee, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 100, -1));

        listadmin.add(employeepanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 150, 40));

        servicespanel.setBackground(new java.awt.Color(29, 45, 61));
        servicespanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        services.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        services.setForeground(new java.awt.Color(239, 234, 234));
        services.setText("Services");
        servicespanel.add(services, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 110, -1));

        listadmin.add(servicespanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, -1, 40));

        bookingspanel.setBackground(new java.awt.Color(29, 45, 61));
        bookingspanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bookings.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        bookings.setForeground(new java.awt.Color(239, 234, 234));
        bookings.setText("Bookings");
        bookingspanel.add(bookings, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 60, -1));

        listadmin.add(bookingspanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 150, 40));

        reportspanel.setBackground(new java.awt.Color(29, 45, 61));
        reportspanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        reports.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        reports.setForeground(new java.awt.Color(239, 234, 234));
        reports.setText("Reports");
        reportspanel.add(reports, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, -1));

        listadmin.add(reportspanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 150, 40));

        feedbackpanel.setBackground(new java.awt.Color(29, 45, 61));
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

        reporttypecombobox1.setBackground(new java.awt.Color(29, 45, 61));
        reporttypecombobox1.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        reporttypecombobox1.setForeground(new java.awt.Color(29, 45, 61));
        reporttypecombobox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel4.add(reporttypecombobox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 60, 100, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 790, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void deletepanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deletepanelMouseClicked
        handleDelete();
    }//GEN-LAST:event_deletepanelMouseClicked

    private void updatepanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updatepanelMouseClicked
           handleUpdate();
    }//GEN-LAST:event_updatepanelMouseClicked

    private void addpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addpanelMouseClicked
        handleAdd();
    }//GEN-LAST:event_addpanelMouseClicked

    private void approvepanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_approvepanelMouseClicked
          handleApprove();
    }//GEN-LAST:event_approvepanelMouseClicked

    private void exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMouseClicked
        // TODO add your handling code here:z
    }//GEN-LAST:event_exitMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Users.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Users.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Users.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Users.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Users().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Employee;
    private javax.swing.JTable TableUsers;
    private javax.swing.JLabel add;
    private javax.swing.JPanel addpanel;
    private javax.swing.JLabel admindashboard;
    private javax.swing.JLabel administrator;
    private javax.swing.JLabel approve;
    private javax.swing.JPanel approvepanel;
    private javax.swing.JLabel bookings;
    private javax.swing.JPanel bookingspanel;
    private javax.swing.JPanel border2;
    private javax.swing.JPanel border3;
    private javax.swing.JLabel cleanernum;
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
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lg;
    private javax.swing.JPanel listadmin;
    private javax.swing.JLabel logout1;
    private javax.swing.JPanel red;
    private javax.swing.JLabel reports;
    private javax.swing.JPanel reportspanel;
    private javax.swing.JComboBox<String> reporttypecombobox1;
    private javax.swing.JLabel review;
    private javax.swing.JTextField searchfield;
    private javax.swing.JLabel services;
    private javax.swing.JPanel servicespanel;
    private javax.swing.JLabel settings;
    private javax.swing.JPanel skyblue;
    private javax.swing.JLabel staff;
    private javax.swing.JLabel staffnum;
    private javax.swing.JLabel totalusers;
    private javax.swing.JLabel totalusersnum;
    private javax.swing.JLabel update;
    private javax.swing.JPanel updatepanel;
    private javax.swing.JPanel userpanel;
    private javax.swing.JLabel users;
    private javax.swing.JScrollPane usersscrollpane;
    private javax.swing.JPanel yellow;
    // End of variables declaration//GEN-END:variables

    private void wireButtonGroups() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
