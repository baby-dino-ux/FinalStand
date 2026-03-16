/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Employee;

import AdminInternalPage.Profile;
import LoginandRegister.LoginForm;
import Session.Session;
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
public class EmployeeDash extends javax.swing.JFrame {

    private final config conf = new config();
 
    public EmployeeDash() {
        initComponents();
        loadSessionInfo();
        wireStatusBox();
        loadDashboard();
        setupCombobox();
    }
 
    public EmployeeDash(String username) {
        initComponents();
        loadSessionInfo();
        wireStatusBox();
        loadDashboard();
        setupCombobox();
    }
 
    private void setupCombobox() {
        reporttypecombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{
            "All My Tasks",
            "Pending Only",
            "In Progress Only",
            "Done Only",
            "Cancelled Only"
        }));
        reporttypecombobox.addActionListener(e -> filterRecentTasks());
    }
 
    private void loadSessionInfo() {
        Session session = Session.getInstance();
        lblUsername.setText(session.isLoggedIn() ? session.getFullName() : "Employee");
    }
 
    private void wireStatusBox() {
        Session session = Session.getInstance();
        String myName = session.getFullName();
        String currentStatus = "Available";
        String sql = "SELECT work_status FROM tbl_users WHERE (firstname||' '||lastname) = ? AND type = 'Employee'";
        try (Connection conn = config.connectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, myName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) currentStatus = rs.getString("work_status");
            }
        } catch (SQLException e) { System.out.println("Status load: " + e.getMessage()); }
 
        for (java.awt.event.ActionListener al : StatusBox.getActionListeners())
            StatusBox.removeActionListener(al);
        for (java.awt.event.ActionListener al : jComboBox1.getActionListeners())
            jComboBox1.removeActionListener(al);
 
        String[] statuses = {"Available", "Busy", "Off Duty"};
        StatusBox.setModel(new javax.swing.DefaultComboBoxModel<>(statuses));
        StatusBox.setSelectedItem(currentStatus);
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(statuses));
        jComboBox1.setSelectedItem(currentStatus);
 
        StatusBox.addActionListener(e -> saveWorkStatus(StatusBox.getSelectedItem().toString()));
        jComboBox1.addActionListener(e -> saveWorkStatus(jComboBox1.getSelectedItem().toString()));
    }
 
    private void saveWorkStatus(String newStatus) {
        String myName = Session.getInstance().getFullName();
        conf.updateRecord(
            "UPDATE tbl_users SET work_status = ? WHERE (firstname||' '||lastname) = ? AND type = 'Employee'",
            newStatus, myName);
    }
 
    private void loadDashboard() {
        String myName = Session.getInstance().getFullName();
 
        Object total = conf.getValue("SELECT COUNT(*) FROM tbl_bookings WHERE b_employee = ?", myName);
        tasksnum.setText(total != null ? total.toString() : "0");
 
        Object completed = conf.getValue("SELECT COUNT(*) FROM tbl_bookings WHERE b_employee = ? AND b_status = 'Done'", myName);
        completednum.setText(completed != null ? completed.toString() : "0");
 
        Object pending = conf.getValue("SELECT COUNT(*) FROM tbl_bookings WHERE b_employee = ? AND b_status = 'Pending'", myName);
        pendingnum.setText(pending != null ? pending.toString() : "0");
 
        Object inprogress = conf.getValue("SELECT COUNT(*) FROM tbl_bookings WHERE b_employee = ? AND b_status = 'In Progress'", myName);
        inprogressnum.setText(inprogress != null ? inprogress.toString() : "0");
 
        conf.displayData(
            "SELECT b_id AS 'ID', b_customer AS 'Customer', b_service AS 'Service', " +
            "b_date AS 'Date', b_status AS 'Status' " +
            "FROM tbl_bookings WHERE b_employee = ? ORDER BY b_id DESC LIMIT 6",
            TableMyRecentTasks, myName);
 
        loadUpcomingTasks(myName);
    }
 
    private void loadUpcomingTasks(String myName) {
        String sql = "SELECT b_customer, b_service, b_date, b_tasknote " +
                     "FROM tbl_bookings WHERE b_employee = ? AND b_status = 'Pending' ORDER BY b_date ASC LIMIT 3";
        try (Connection conn = config.connectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, myName);
            try (ResultSet rs = ps.executeQuery()) {
                javax.swing.JTextArea[] areas = { upcomingtaskslist1, upcomingtaskslist2, upcomingtaskslist3 };
                int idx = 0;
                while (rs.next() && idx < 3) {
                    String text = "Customer: " + rs.getString("b_customer") + "\n" +
                                  "Service: "  + rs.getString("b_service")  + "\n" +
                                  "Date: "     + rs.getString("b_date")     + "\n" +
                                  (rs.getString("b_tasknote") != null && !rs.getString("b_tasknote").isEmpty()
                                   ? "Note: " + rs.getString("b_tasknote") : "");
                    areas[idx].setText(text);
                    idx++;
                }
                for (int i = idx; i < 3; i++) areas[i].setText("No upcoming task");
            }
        } catch (SQLException e) { System.out.println("Upcoming tasks error: " + e.getMessage()); }
    }
 
    // ── Filter the Recent Tasks table by combobox selection ──────────────────
    private void filterRecentTasks() {
        String selected = reporttypecombobox.getSelectedItem() != null
                          ? reporttypecombobox.getSelectedItem().toString() : "All My Tasks";
        String myName = Session.getInstance().getFullName();
 
        String statusClause;
        switch (selected) {
            case "Pending Only":     statusClause = " AND b_status = 'Pending'"; break;
            case "In Progress Only": statusClause = " AND b_status = 'In Progress'"; break;
            case "Done Only":        statusClause = " AND b_status = 'Done'"; break;
            case "Cancelled Only":   statusClause = " AND b_status = 'Cancelled'"; break;
            default:                 statusClause = ""; break;
        }
 
        String sql = "SELECT b_id AS 'ID', b_customer AS 'Customer', b_service AS 'Service', " +
                     "b_date AS 'Date', b_status AS 'Status' " +
                     "FROM tbl_bookings WHERE b_employee = ?" + statusClause +
                     " ORDER BY b_id DESC LIMIT 6";
        conf.displayData(sql, TableMyRecentTasks, myName);
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
        cleanerdashboard = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        totaltaskspanel = new javax.swing.JPanel();
        tasksborder = new javax.swing.JPanel();
        blue = new javax.swing.JPanel();
        tasksnum = new javax.swing.JLabel();
        totaltasks = new javax.swing.JLabel();
        myrecenttasksscrollpane = new javax.swing.JScrollPane();
        TableMyRecentTasks = new javax.swing.JTable();
        myrecenttasks = new javax.swing.JLabel();
        border2 = new javax.swing.JPanel();
        border3 = new javax.swing.JPanel();
        list = new javax.swing.JPanel();
        settings = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        dashpanel = new javax.swing.JPanel();
        dashboard = new javax.swing.JLabel();
        mytaskspanel = new javax.swing.JPanel();
        mytasks = new javax.swing.JLabel();
        memployeepanel = new javax.swing.JPanel();
        reportspanel1 = new javax.swing.JPanel();
        review1 = new javax.swing.JLabel();
        mystatuspanel = new javax.swing.JPanel();
        StatusBox = new javax.swing.JComboBox<>();
        mystatus = new javax.swing.JLabel();
        Employee = new javax.swing.JLabel();
        completedpanel = new javax.swing.JPanel();
        completedborder = new javax.swing.JPanel();
        green = new javax.swing.JPanel();
        completednum = new javax.swing.JLabel();
        completed = new javax.swing.JLabel();
        pendingpanel = new javax.swing.JPanel();
        pendingborder = new javax.swing.JPanel();
        yellow = new javax.swing.JPanel();
        reportspanel = new javax.swing.JPanel();
        review = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        users = new javax.swing.JLabel();
        pendingnum = new javax.swing.JLabel();
        pending = new javax.swing.JLabel();
        inprogresspanel = new javax.swing.JPanel();
        totalbookingsborder1 = new javax.swing.JPanel();
        yellowgreen = new javax.swing.JPanel();
        inprogressnum = new javax.swing.JLabel();
        inprogress = new javax.swing.JLabel();
        upcomingtaskspanel = new javax.swing.JPanel();
        review2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        upcomingtaskslist3 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        upcomingtaskslist2 = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        upcomingtaskslist1 = new javax.swing.JTextArea();
        upcomingtasks = new javax.swing.JLabel();
        reporttypecombobox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        cleanerdashboard.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        cleanerdashboard.setForeground(new java.awt.Color(239, 234, 234));
        cleanerdashboard.setText("Cleaner Dashboard");
        jPanel1.add(cleanerdashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 190, 50));

        jPanel5.setBackground(new java.awt.Color(29, 45, 61));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(-90, 420, 110, 60));

        jPanel4.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 640, 50));

        totaltaskspanel.setBackground(new java.awt.Color(29, 45, 61));
        totaltaskspanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        totaltaskspanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tasksborder.setBackground(new java.awt.Color(0, 102, 255));
        tasksborder.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        totaltaskspanel.add(tasksborder, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 90));

        blue.setBackground(new java.awt.Color(0, 102, 255));
        blue.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        totaltaskspanel.add(blue, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 40, 40));

        tasksnum.setFont(new java.awt.Font("Bahnschrift", 0, 24)); // NOI18N
        tasksnum.setForeground(new java.awt.Color(255, 255, 255));
        tasksnum.setText("0");
        totaltaskspanel.add(tasksnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 50, -1));

        totaltasks.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        totaltasks.setForeground(new java.awt.Color(255, 255, 255));
        totaltasks.setText("Total Tasks");
        totaltaskspanel.add(totaltasks, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 80, 30));

        jPanel4.add(totaltaskspanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 140, 80));

        myrecenttasksscrollpane.setBackground(new java.awt.Color(29, 45, 61));
        myrecenttasksscrollpane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        myrecenttasksscrollpane.setForeground(new java.awt.Color(255, 255, 255));
        myrecenttasksscrollpane.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N

        TableMyRecentTasks.setBackground(new java.awt.Color(29, 45, 61));
        TableMyRecentTasks.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        TableMyRecentTasks.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        TableMyRecentTasks.setForeground(new java.awt.Color(255, 255, 255));
        TableMyRecentTasks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Customer", "Service", "Date", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        TableMyRecentTasks.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        myrecenttasksscrollpane.setViewportView(TableMyRecentTasks);

        jPanel4.add(myrecenttasksscrollpane, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 180, 340, 290));

        myrecenttasks.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        myrecenttasks.setForeground(new java.awt.Color(153, 255, 255));
        myrecenttasks.setText("MY RECENT TASKS");
        jPanel4.add(myrecenttasks, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, 110, 30));

        border2.setBackground(new java.awt.Color(153, 255, 255));
        border2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        border2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                border2MouseClicked(evt);
            }
        });
        border2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(border2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 630, 10));

        border3.setBackground(new java.awt.Color(153, 255, 255));
        border3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                border3MouseClicked(evt);
            }
        });
        border3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(border3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 490));

        list.setBackground(new java.awt.Color(55, 86, 93));
        list.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        settings.setBackground(new java.awt.Color(255, 255, 255));
        settings.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        settings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user (1).png"))); // NOI18N
        settings.setText("ACCOUNT");
        settings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                settingsMouseClicked(evt);
            }
        });
        list.add(settings, new org.netbeans.lib.awtextra.AbsoluteConstraints(-220, 10, 280, 50));

        lblUsername.setBackground(new java.awt.Color(153, 255, 255));
        lblUsername.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        lblUsername.setText("Cleaner");
        list.add(lblUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 90, 20));

        dashpanel.setBackground(new java.awt.Color(29, 45, 61));
        dashpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashpanelMouseClicked(evt);
            }
        });
        dashpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dashboard.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        dashboard.setForeground(new java.awt.Color(239, 234, 234));
        dashboard.setText("Dashboard");
        dashpanel.add(dashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 70, 20));

        list.add(dashpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 150, 40));

        mytaskspanel.setBackground(new java.awt.Color(29, 45, 61));
        mytaskspanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mytaskspanelMouseClicked(evt);
            }
        });
        mytaskspanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mytasks.setBackground(new java.awt.Color(29, 45, 61));
        mytasks.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        mytasks.setForeground(new java.awt.Color(239, 234, 234));
        mytasks.setText("My Tasks");
        mytaskspanel.add(mytasks, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 70, -1));

        list.add(mytaskspanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 150, 40));

        memployeepanel.setBackground(new java.awt.Color(29, 45, 61));
        memployeepanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        list.add(memployeepanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, -1, 40));

        reportspanel1.setBackground(new java.awt.Color(29, 45, 61));
        reportspanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        review1.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        reportspanel1.add(review1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 90, -1));

        mystatuspanel.setBackground(new java.awt.Color(29, 45, 61));
        mystatuspanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        mystatuspanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        StatusBox.setBackground(new java.awt.Color(102, 255, 255));
        StatusBox.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        StatusBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Available" }));
        mystatuspanel.add(StatusBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 90, -1));

        mystatus.setBackground(new java.awt.Color(29, 45, 61));
        mystatus.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        mystatus.setForeground(new java.awt.Color(239, 234, 234));
        mystatus.setText("My Status");
        mystatuspanel.add(mystatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 70, -1));

        reportspanel1.add(mystatuspanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 130, 60));

        list.add(reportspanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 150, 350));

        Employee.setBackground(new java.awt.Color(153, 255, 255));
        Employee.setFont(new java.awt.Font("Century Gothic", 1, 10)); // NOI18N
        Employee.setForeground(new java.awt.Color(153, 255, 255));
        Employee.setText("Employee");
        list.add(Employee, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 90, 20));

        jPanel4.add(list, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 150, 490));

        completedpanel.setBackground(new java.awt.Color(29, 45, 61));
        completedpanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        completedpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        completedborder.setBackground(new java.awt.Color(0, 255, 153));
        completedborder.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        completedpanel.add(completedborder, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 90));

        green.setBackground(new java.awt.Color(0, 255, 153));
        green.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        completedpanel.add(green, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 40, 40));

        completednum.setFont(new java.awt.Font("Bahnschrift", 0, 24)); // NOI18N
        completednum.setForeground(new java.awt.Color(255, 255, 255));
        completednum.setText("0");
        completedpanel.add(completednum, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 50, -1));

        completed.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        completed.setForeground(new java.awt.Color(255, 255, 255));
        completed.setText("Completed");
        completedpanel.add(completed, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 80, 30));

        jPanel4.add(completedpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 70, 140, 80));

        pendingpanel.setBackground(new java.awt.Color(29, 45, 61));
        pendingpanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        pendingpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pendingborder.setBackground(new java.awt.Color(255, 255, 51));
        pendingborder.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        pendingpanel.add(pendingborder, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 90));

        yellow.setBackground(new java.awt.Color(255, 255, 51));
        yellow.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pendingpanel.add(yellow, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 40, 40));

        reportspanel.setBackground(new java.awt.Color(29, 45, 61));
        reportspanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        review.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        reportspanel.add(review, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 90, -1));

        jPanel2.setBackground(new java.awt.Color(29, 45, 61));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBox1.setBackground(new java.awt.Color(102, 255, 255));
        jComboBox1.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Available" }));
        jPanel2.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 90, -1));

        users.setBackground(new java.awt.Color(29, 45, 61));
        users.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        users.setForeground(new java.awt.Color(239, 234, 234));
        users.setText("My Status");
        jPanel2.add(users, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 70, -1));

        reportspanel.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 130, 60));

        pendingpanel.add(reportspanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 140, 60, 200));

        pendingnum.setFont(new java.awt.Font("Bahnschrift", 0, 24)); // NOI18N
        pendingnum.setForeground(new java.awt.Color(255, 255, 255));
        pendingnum.setText("0");
        pendingpanel.add(pendingnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 50, -1));

        pending.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        pending.setForeground(new java.awt.Color(255, 255, 255));
        pending.setText("Pending");
        pendingpanel.add(pending, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 80, 30));

        jPanel4.add(pendingpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 70, 140, 80));

        inprogresspanel.setBackground(new java.awt.Color(29, 45, 61));
        inprogresspanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        inprogresspanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        totalbookingsborder1.setBackground(new java.awt.Color(204, 255, 0));
        totalbookingsborder1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        inprogresspanel.add(totalbookingsborder1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 90));

        yellowgreen.setBackground(new java.awt.Color(204, 255, 0));
        yellowgreen.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        inprogresspanel.add(yellowgreen, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 40, 40));

        inprogressnum.setFont(new java.awt.Font("Bahnschrift", 0, 24)); // NOI18N
        inprogressnum.setForeground(new java.awt.Color(255, 255, 255));
        inprogressnum.setText("0");
        inprogresspanel.add(inprogressnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 50, -1));

        inprogress.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        inprogress.setForeground(new java.awt.Color(255, 255, 255));
        inprogress.setText("In Progress");
        inprogresspanel.add(inprogress, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 80, 30));

        jPanel4.add(inprogresspanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 70, 140, 80));

        upcomingtaskspanel.setBackground(new java.awt.Color(29, 45, 61));
        upcomingtaskspanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        review2.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        upcomingtaskspanel.add(review2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 90, -1));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        upcomingtaskslist3.setBackground(new java.awt.Color(29, 45, 61));
        upcomingtaskslist3.setColumns(20);
        upcomingtaskslist3.setRows(5);
        jScrollPane1.setViewportView(upcomingtaskslist3);

        upcomingtaskspanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 220, 70));

        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        upcomingtaskslist2.setBackground(new java.awt.Color(29, 45, 61));
        upcomingtaskslist2.setColumns(20);
        upcomingtaskslist2.setRows(5);
        jScrollPane2.setViewportView(upcomingtaskslist2);

        upcomingtaskspanel.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 220, 70));

        jScrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        upcomingtaskslist1.setBackground(new java.awt.Color(29, 45, 61));
        upcomingtaskslist1.setColumns(20);
        upcomingtaskslist1.setRows(5);
        jScrollPane4.setViewportView(upcomingtaskslist1);

        upcomingtaskspanel.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 220, 70));

        upcomingtasks.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        upcomingtasks.setForeground(new java.awt.Color(153, 255, 255));
        upcomingtasks.setText("UPCOMING TASKS");
        upcomingtaskspanel.add(upcomingtasks, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 110, 30));

        jPanel4.add(upcomingtaskspanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 160, 240, 290));

        reporttypecombobox.setBackground(new java.awt.Color(29, 45, 61));
        reporttypecombobox.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        reporttypecombobox.setForeground(new java.awt.Color(29, 45, 61));
        reporttypecombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        reporttypecombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reporttypecomboboxActionPerformed(evt);
            }
        });
        jPanel4.add(reporttypecombobox, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 160, 100, 20));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void settingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsMouseClicked
       new Profile().setVisible(true); this.dispose();
    }//GEN-LAST:event_settingsMouseClicked

    private void mytaskspanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mytaskspanelMouseClicked
       new MyTasks().setVisible(true); this.dispose();
    }//GEN-LAST:event_mytaskspanelMouseClicked

    private void exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMouseClicked
        int c = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);
        if (c == JOptionPane.YES_OPTION) {
            Session.getInstance().logout();
            new LoginForm().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_exitMouseClicked

    private void border2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_border2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_border2MouseClicked

    private void border3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_border3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_border3MouseClicked

    private void dashpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashpanelMouseClicked
           loadDashboard();
    }//GEN-LAST:event_dashpanelMouseClicked

    private void reporttypecomboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reporttypecomboboxActionPerformed
        filterRecentTasks();
    }//GEN-LAST:event_reporttypecomboboxActionPerformed

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
                new EmployeeDash().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Employee;
    private javax.swing.JComboBox<String> StatusBox;
    private javax.swing.JTable TableMyRecentTasks;
    private javax.swing.JPanel blue;
    private javax.swing.JPanel border2;
    private javax.swing.JPanel border3;
    private javax.swing.JLabel cleanerdashboard;
    private javax.swing.JLabel completed;
    private javax.swing.JPanel completedborder;
    private javax.swing.JLabel completednum;
    private javax.swing.JPanel completedpanel;
    private javax.swing.JLabel dashboard;
    private javax.swing.JPanel dashpanel;
    private javax.swing.JLabel exit;
    private javax.swing.JPanel green;
    private javax.swing.JLabel inprogress;
    private javax.swing.JLabel inprogressnum;
    private javax.swing.JPanel inprogresspanel;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lg;
    private javax.swing.JPanel list;
    private javax.swing.JPanel memployeepanel;
    private javax.swing.JLabel myrecenttasks;
    private javax.swing.JScrollPane myrecenttasksscrollpane;
    private javax.swing.JLabel mystatus;
    private javax.swing.JPanel mystatuspanel;
    private javax.swing.JLabel mytasks;
    private javax.swing.JPanel mytaskspanel;
    private javax.swing.JLabel pending;
    private javax.swing.JPanel pendingborder;
    private javax.swing.JLabel pendingnum;
    private javax.swing.JPanel pendingpanel;
    private javax.swing.JPanel reportspanel;
    private javax.swing.JPanel reportspanel1;
    private javax.swing.JComboBox<String> reporttypecombobox;
    private javax.swing.JLabel review;
    private javax.swing.JLabel review1;
    private javax.swing.JLabel review2;
    private javax.swing.JLabel settings;
    private javax.swing.JPanel tasksborder;
    private javax.swing.JLabel tasksnum;
    private javax.swing.JPanel totalbookingsborder1;
    private javax.swing.JLabel totaltasks;
    private javax.swing.JPanel totaltaskspanel;
    private javax.swing.JLabel upcomingtasks;
    private javax.swing.JTextArea upcomingtaskslist1;
    private javax.swing.JTextArea upcomingtaskslist2;
    private javax.swing.JTextArea upcomingtaskslist3;
    private javax.swing.JPanel upcomingtaskspanel;
    private javax.swing.JLabel users;
    private javax.swing.JPanel yellow;
    private javax.swing.JPanel yellowgreen;
    // End of variables declaration//GEN-END:variables
}
