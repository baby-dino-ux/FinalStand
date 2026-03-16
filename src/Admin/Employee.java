package Admin;

import AdminInternalPage.Form;
import AdminInternalPage.Profile;
import Admin.AdminDash;
import Admin.Bookings;
import Admin.Reports;
import Admin.Services;
import Staff.Feedback;
import Session.Session;
import config.config;
import LoginandRegister.LoginForm;
import javax.swing.JOptionPane;

public class Employee extends javax.swing.JFrame {

public Employee() {
    initComponents();
    loadSessionInfo();
    loadStats();

    reporttypecombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{
        "All Employees",
        "Available Only",
        "Busy Only",
        "Off Duty Only",
        "Active Accounts",
        "Inactive Accounts"
    }));

    reporttypecombobox.addActionListener(e -> filterEmployees());
    filterEmployees();

    searchfield.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
        public void insertUpdate(javax.swing.event.DocumentEvent e)  { filterEmployees(); }
        public void removeUpdate(javax.swing.event.DocumentEvent e)  { filterEmployees(); }
        public void changedUpdate(javax.swing.event.DocumentEvent e) { filterEmployees(); }
    });

    refreshlistpanel.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            searchfield.setText("");
            reporttypecombobox.setSelectedIndex(0);
            loadStats();
        }
    });
}

private void loadSessionInfo() {
    Session session = Session.getInstance();
    lblUsername.setText(session.isLoggedIn() ? session.getFullName() : "Admin");
}

private void loadStats() {
    config conf = new config();
    Object total = conf.getValue("SELECT COUNT(*) FROM tbl_users WHERE type = 'Employee'");
    totalnum.setText(total != null ? total.toString() : "0");

    Object avail = conf.getValue("SELECT COUNT(*) FROM tbl_users WHERE type = 'Employee' AND work_status = 'Available'");
    availablenum.setText(avail != null ? avail.toString() : "0");

    Object busy = conf.getValue("SELECT COUNT(*) FROM tbl_users WHERE type = 'Employee' AND work_status = 'Busy'");
    busynum.setText(busy != null ? busy.toString() : "0");

    Object off = conf.getValue("SELECT COUNT(*) FROM tbl_users WHERE type = 'Employee' AND work_status = 'Off Duty'");
    offdutynum.setText(off != null ? off.toString() : "0");
}

private void filterEmployees() {
    String selected = reporttypecombobox.getSelectedItem() != null
                      ? reporttypecombobox.getSelectedItem().toString() : "All Employees";
    String kw = searchfield.getText().trim();

    String whereClause;
    switch (selected) {
        case "Available Only":    whereClause = "type = 'Employee' AND work_status = 'Available'"; break;
        case "Busy Only":         whereClause = "type = 'Employee' AND work_status = 'Busy'"; break;
        case "Off Duty Only":     whereClause = "type = 'Employee' AND work_status = 'Off Duty'"; break;
        case "Active Accounts":   whereClause = "type = 'Employee' AND status = 'Active'"; break;
        case "Inactive Accounts": whereClause = "type = 'Employee' AND status = 'Inactive'"; break;
        default:                  whereClause = "type = 'Employee'"; break;
    }

    String sql = "SELECT id AS 'ID', (firstname || ' ' || lastname) AS 'Name', " +
                 "email AS 'Email', username AS 'Username', " +
                 "status AS 'Account Status', work_status AS 'Work Status' " +
                 "FROM tbl_users WHERE " + whereClause;
    if (!kw.isEmpty()) {
        sql += " AND (firstname LIKE '%" + kw + "%' OR lastname LIKE '%" + kw + "%' " +
               "OR email LIKE '%" + kw + "%')";
    }
    sql += " ORDER BY id DESC";
    new config().displayData(sql, TableCleaners);
}

private String getSelectedId() {
    int row = TableCleaners.getSelectedRow();
    if (row < 0) {
        JOptionPane.showMessageDialog(this, "Please select an employee first.",
            "No Selection", JOptionPane.WARNING_MESSAGE);
        return null;
    }
    return TableCleaners.getValueAt(row, 0).toString();
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lg = new javax.swing.JLabel();
        exit = new javax.swing.JLabel();
        employeeheader = new javax.swing.JLabel();
        border2 = new javax.swing.JPanel();
        border3 = new javax.swing.JPanel();
        cleanersscrollpane = new javax.swing.JScrollPane();
        TableCleaners = new javax.swing.JTable();
        searchfield = new javax.swing.JTextField();
        black = new javax.swing.JPanel();
        totalnum = new javax.swing.JLabel();
        total = new javax.swing.JLabel();
        skyblue = new javax.swing.JPanel();
        availablenum = new javax.swing.JLabel();
        available = new javax.swing.JLabel();
        red = new javax.swing.JPanel();
        busynum = new javax.swing.JLabel();
        busy = new javax.swing.JLabel();
        yellow = new javax.swing.JPanel();
        offdutynum = new javax.swing.JLabel();
        offduty = new javax.swing.JLabel();
        addpanel = new javax.swing.JPanel();
        add = new javax.swing.JLabel();
        updatepanel = new javax.swing.JPanel();
        update = new javax.swing.JLabel();
        deletepanel = new javax.swing.JPanel();
        delete = new javax.swing.JLabel();
        approvepanel = new javax.swing.JPanel();
        approve = new javax.swing.JLabel();
        refreshlistpanel = new javax.swing.JPanel();
        refreshlist = new javax.swing.JLabel();
        reporttypecombobox = new javax.swing.JComboBox<>();
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
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(28, 69, 91));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(29, 45, 61));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        lg.setFont(new java.awt.Font("Bahnschrift", 0, 14));
        lg.setForeground(new java.awt.Color(239, 234, 234));
        lg.setText("Log Out");
        jPanel1.add(lg, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, -1, 20));
        exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout (2).png")));
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) { exitMouseClicked(evt); }
        });
        jPanel1.add(exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, 30, 20));
        employeeheader.setFont(new java.awt.Font("Bahnschrift", 0, 18));
        employeeheader.setForeground(new java.awt.Color(239, 234, 234));
        employeeheader.setText("Employee");
        jPanel1.add(employeeheader, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 190, 50));
        jPanel4.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 630, 50));

        border2.setBackground(new java.awt.Color(153, 255, 255));
        border2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        border2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(border2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 630, 10));

        border3.setBackground(new java.awt.Color(153, 255, 255));
        border3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(border3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 490));

        // Stat panels
        black = new javax.swing.JPanel();
        black.setBackground(new java.awt.Color(29, 45, 61));
        black.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        black.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        totalnum.setFont(new java.awt.Font("Bahnschrift", 0, 24)); totalnum.setForeground(new java.awt.Color(255,255,255)); totalnum.setText("0");
        black.add(totalnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 60, -1));
        total.setFont(new java.awt.Font("Bahnschrift", 0, 12)); total.setForeground(new java.awt.Color(255,255,255)); total.setText("Total");
        black.add(total, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 80, 30));
        jPanel4.add(black, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 110, 80));

        skyblue = new javax.swing.JPanel();
        skyblue.setBackground(new java.awt.Color(29, 45, 61));
        skyblue.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        skyblue.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        availablenum.setFont(new java.awt.Font("Bahnschrift", 0, 24)); availablenum.setForeground(new java.awt.Color(255,255,255)); availablenum.setText("0");
        skyblue.add(availablenum, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 60, -1));
        available.setFont(new java.awt.Font("Bahnschrift", 0, 12)); available.setForeground(new java.awt.Color(255,255,255)); available.setText("Available");
        skyblue.add(available, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 80, 30));
        jPanel4.add(skyblue, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 70, 110, 80));

        red = new javax.swing.JPanel();
        red.setBackground(new java.awt.Color(29, 45, 61));
        red.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        red.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        busynum.setFont(new java.awt.Font("Bahnschrift", 0, 24)); busynum.setForeground(new java.awt.Color(255,255,255)); busynum.setText("0");
        red.add(busynum, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 60, -1));
        busy.setFont(new java.awt.Font("Bahnschrift", 0, 12)); busy.setForeground(new java.awt.Color(255,255,255)); busy.setText("Busy");
        red.add(busy, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 80, 30));
        jPanel4.add(red, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 70, 110, 80));

        yellow = new javax.swing.JPanel();
        yellow.setBackground(new java.awt.Color(29, 45, 61));
        yellow.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        yellow.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        offdutynum.setFont(new java.awt.Font("Bahnschrift", 0, 24)); offdutynum.setForeground(new java.awt.Color(255,255,255)); offdutynum.setText("0");
        yellow.add(offdutynum, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 60, -1));
        offduty.setFont(new java.awt.Font("Bahnschrift", 0, 12)); offduty.setForeground(new java.awt.Color(255,255,255)); offduty.setText("Off Duty");
        yellow.add(offduty, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 80, 30));
        jPanel4.add(yellow, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 70, 110, 80));

        // Search, filter, CRUD buttons
        searchfield.setFont(new java.awt.Font("Bahnschrift", 0, 11));
        searchfield.setText("Search ");
        jPanel4.add(searchfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 160, 250, 20));

        reporttypecombobox.setFont(new java.awt.Font("Bahnschrift", 0, 12));
        reporttypecombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { reporttypecomboboxActionPerformed(evt); }
        });
        jPanel4.add(reporttypecombobox, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 160, 130, 20));

        addpanel.setBackground(new java.awt.Color(29, 45, 61));
        addpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) { addpanelMouseClicked(evt); }
        });
        add.setFont(new java.awt.Font("Bahnschrift", 1, 12)); add.setForeground(new java.awt.Color(239,234,234)); add.setText("ADD");
        addpanel.add(add);
        jPanel4.add(addpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 160, 50, 20));

        updatepanel.setBackground(new java.awt.Color(29, 45, 61));
        updatepanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) { updatepanelMouseClicked(evt); }
        });
        update.setFont(new java.awt.Font("Bahnschrift", 1, 12)); update.setForeground(new java.awt.Color(239,234,234)); update.setText("UPDATE");
        updatepanel.add(update);
        jPanel4.add(updatepanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(625, 160, 65, 20));

        deletepanel.setBackground(new java.awt.Color(29, 45, 61));
        deletepanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) { deletepanelMouseClicked(evt); }
        });
        delete.setFont(new java.awt.Font("Bahnschrift", 1, 12)); delete.setForeground(new java.awt.Color(239,234,234)); delete.setText("DELETE");
        deletepanel.add(delete);
        jPanel4.add(deletepanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(695, 160, 65, 20));

        approvepanel.setBackground(new java.awt.Color(0, 153, 76));
        approvepanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) { approvepanelMouseClicked(evt); }
        });
        approve.setFont(new java.awt.Font("Bahnschrift", 1, 12)); approve.setForeground(new java.awt.Color(255,255,255)); approve.setText("TOGGLE STATUS");
        approvepanel.add(approve);
        jPanel4.add(approvepanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 130, 120, 20));

        refreshlistpanel.setBackground(new java.awt.Color(29, 45, 61));
        refreshlistpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        refreshlist.setFont(new java.awt.Font("Bahnschrift", 0, 14)); refreshlist.setForeground(new java.awt.Color(255,255,255)); refreshlist.setText("Refresh List");
        refreshlistpanel.add(refreshlist, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 90, 30));
        jPanel4.add(refreshlistpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 60, -1, 30));

        // Table
        TableCleaners.setBackground(new java.awt.Color(29, 45, 61));
        TableCleaners.setFont(new java.awt.Font("Bahnschrift", 0, 12));
        TableCleaners.setForeground(new java.awt.Color(255, 255, 255));
        TableCleaners.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{}, new String[]{"ID","Name","Email","Username","Account Status","Work Status"}
        ));
        cleanersscrollpane.setViewportView(TableCleaners);
        jPanel4.add(cleanersscrollpane, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, 610, 280));

        // Sidebar
        listadmin.setBackground(new java.awt.Color(55, 86, 93));
        listadmin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        settings.setBackground(new java.awt.Color(255, 255, 255));
        settings.setFont(new java.awt.Font("Arial Black", 1, 14));
        settings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user (1).png")));
        settings.setText("ACCOUNT");
        settings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) { settingsMouseClicked(evt); }
        });
        listadmin.add(settings, new org.netbeans.lib.awtextra.AbsoluteConstraints(-220, 10, 280, 50));
        lblUsername.setFont(new java.awt.Font("Century Gothic", 1, 12)); lblUsername.setText("Admin");
        listadmin.add(lblUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 90, 20));

        dashpanel.setBackground(new java.awt.Color(29, 45, 61));
        dashpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) { dashpanelMouseClicked(evt); }
        });
        dashpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        dashboard1.setFont(new java.awt.Font("Bahnschrift", 0, 14)); dashboard1.setForeground(new java.awt.Color(239,234,234)); dashboard1.setText("Dashboard");
        dashpanel.add(dashboard1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 70, 20));
        listadmin.add(dashpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 150, 40));

        userpanel.setBackground(new java.awt.Color(29, 45, 61));
        userpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) { userpanelMouseClicked(evt); }
        });
        userpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        users.setFont(new java.awt.Font("Bahnschrift", 0, 14)); users.setForeground(new java.awt.Color(239,234,234)); users.setText("Users");
        userpanel.add(users, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 70, -1));
        listadmin.add(userpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 150, 40));

        employeepanel.setBackground(new java.awt.Color(29, 45, 61));
        employeepanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) { employeepanelMouseClicked(evt); }
        });
        employeepanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        employee.setFont(new java.awt.Font("Bahnschrift", 0, 14)); employee.setForeground(new java.awt.Color(239,234,234)); employee.setText("Employee");
        employeepanel.add(employee, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 100, -1));
        listadmin.add(employeepanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 150, 40));

        servicespanel.setBackground(new java.awt.Color(29, 45, 61));
        servicespanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) { servicespanelMouseClicked(evt); }
        });
        servicespanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        services.setFont(new java.awt.Font("Bahnschrift", 0, 14)); services.setForeground(new java.awt.Color(239,234,234)); services.setText("Services");
        servicespanel.add(services, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 110, -1));
        listadmin.add(servicespanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 150, 40));

        bookingspanel.setBackground(new java.awt.Color(29, 45, 61));
        bookingspanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) { bookingspanelMouseClicked(evt); }
        });
        bookingspanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        bookings.setFont(new java.awt.Font("Bahnschrift", 0, 14)); bookings.setForeground(new java.awt.Color(239,234,234)); bookings.setText("Bookings");
        bookingspanel.add(bookings, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 60, -1));
        listadmin.add(bookingspanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 150, 40));

        reportspanel.setBackground(new java.awt.Color(29, 45, 61));
        reportspanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) { reportspanelMouseClicked(evt); }
        });
        reportspanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        reports.setFont(new java.awt.Font("Bahnschrift", 0, 14)); reports.setForeground(new java.awt.Color(239,234,234)); reports.setText("Reports");
        reportspanel.add(reports, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, -1));
        listadmin.add(reportspanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 150, 40));

        feedbackpanel.setBackground(new java.awt.Color(29, 45, 61));
        feedbackpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) { feedbackpanelMouseClicked(evt); }
        });
        feedbackpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        review.setFont(new java.awt.Font("Arial Black", 1, 14));
        feedbackpanel.add(review, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 90, -1));
        feedback.setFont(new java.awt.Font("Bahnschrift", 0, 14)); feedback.setForeground(new java.awt.Color(239,234,234)); feedback.setText("Feedback");
        feedbackpanel.add(feedback, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 80, -1));
        listadmin.add(feedbackpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 150, 40));

        administrator.setFont(new java.awt.Font("Century Gothic", 1, 10));
        administrator.setForeground(new java.awt.Color(153, 255, 255));
        administrator.setText("ADMINISTRATOR");
        listadmin.add(administrator, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 90, 20));

        jPanel4.add(listadmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 150, -1));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 490));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void addpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addpanelMouseClicked
        Form f = new Form("Add", "", "", "", "", "", "Employee", "Inactive");
        f.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent e) { filterEmployees(); loadStats(); }
        });
        f.setVisible(true);
    }//GEN-LAST:event_addpanelMouseClicked

    private void updatepanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updatepanelMouseClicked
        int row = TableCleaners.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select an employee.", "No Selection", JOptionPane.WARNING_MESSAGE); return; }
        String id     = TableCleaners.getValueAt(row, 0).toString();
        String name   = TableCleaners.getValueAt(row, 1).toString();
        String email  = TableCleaners.getValueAt(row, 2).toString();
        String accStatus = TableCleaners.getValueAt(row, 4).toString();
        String[] parts = name.split(" ", 2);
        String fn = parts[0], ln = parts.length > 1 ? parts[1] : "";
        Form f = new Form("Update", id, fn, ln, email, "", "Employee", accStatus);
        f.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent e) { filterEmployees(); loadStats(); }
        });
        f.setVisible(true);
    }//GEN-LAST:event_updatepanelMouseClicked

    private void deletepanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deletepanelMouseClicked
        String id = getSelectedId();
        if (id == null) return;
        int c = JOptionPane.showConfirmDialog(this, "Delete this employee?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (c == JOptionPane.YES_OPTION) {
            new config().deleteRecord("DELETE FROM tbl_users WHERE id = ?", Integer.parseInt(id));
            JOptionPane.showMessageDialog(this, "Employee deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);
            filterEmployees(); loadStats();
        }
    }//GEN-LAST:event_deletepanelMouseClicked

    private void approvepanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_approvepanelMouseClicked
        String id = getSelectedId();
        if (id == null) return;
        int row = TableCleaners.getSelectedRow();
        String currentAccStatus = TableCleaners.getValueAt(row, 4).toString();
        String newAccStatus = "Active".equalsIgnoreCase(currentAccStatus) ? "Inactive" : "Active";
        int c = JOptionPane.showConfirmDialog(this,
            "Set account status to: " + newAccStatus + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (c == JOptionPane.YES_OPTION) {
            new config().updateRecord("UPDATE tbl_users SET status = ? WHERE id = ?", newAccStatus, Integer.parseInt(id));
            JOptionPane.showMessageDialog(this, "Account status set to: " + newAccStatus, "Updated", JOptionPane.INFORMATION_MESSAGE);
            filterEmployees(); loadStats();
        }
    }//GEN-LAST:event_approvepanelMouseClicked

    private void exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMouseClicked
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?",
            "Log Out", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Session.getInstance().logout(); new LoginForm().setVisible(true); this.dispose();
        }
    }//GEN-LAST:event_exitMouseClicked

    private void border2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_border2MouseClicked
    }//GEN-LAST:event_border2MouseClicked
    private void border3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_border3MouseClicked
    }//GEN-LAST:event_border3MouseClicked

    private void reporttypecomboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reporttypecomboboxActionPerformed
        filterEmployees();
    }//GEN-LAST:event_reporttypecomboboxActionPerformed

    private void settingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsMouseClicked
        new Profile().setVisible(true); this.dispose();
    }//GEN-LAST:event_settingsMouseClicked
    private void dashpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashpanelMouseClicked
        new AdminDash().setVisible(true); this.dispose();
    }//GEN-LAST:event_dashpanelMouseClicked
    private void userpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userpanelMouseClicked
        new Users().setVisible(true); this.dispose();
    }//GEN-LAST:event_userpanelMouseClicked
    private void employeepanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employeepanelMouseClicked
        new Employee().setVisible(true); this.dispose();
    }//GEN-LAST:event_employeepanelMouseClicked
    private void servicespanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_servicespanelMouseClicked
        new Services().setVisible(true); this.dispose();
    }//GEN-LAST:event_servicespanelMouseClicked
    private void bookingspanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookingspanelMouseClicked
        new Bookings().setVisible(true); this.dispose();
    }//GEN-LAST:event_bookingspanelMouseClicked
    private void reportspanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportspanelMouseClicked
        new Reports().setVisible(true); this.dispose();
    }//GEN-LAST:event_reportspanelMouseClicked
    private void feedbackpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_feedbackpanelMouseClicked
        new Feedback().setVisible(true); this.dispose();
    }//GEN-LAST:event_feedbackpanelMouseClicked

    public static void main(String args[]) {
        Session session = Session.getInstance();
        if (!session.isLoggedIn()) {
            JOptionPane.showMessageDialog(null, "Please login first!", "Unauthorized Access", JOptionPane.WARNING_MESSAGE);
            java.awt.EventQueue.invokeLater(() -> new LoginForm().setVisible(true)); return;
        }
        java.awt.EventQueue.invokeLater(() -> new AdminDash().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TableCleaners;
    private javax.swing.JLabel add;
    private javax.swing.JPanel addpanel;
    private javax.swing.JLabel administrator;
    private javax.swing.JLabel approve;
    private javax.swing.JPanel approvepanel;
    private javax.swing.JLabel available;
    private javax.swing.JLabel availablenum;
    private javax.swing.JPanel black;
    private javax.swing.JLabel bookings;
    private javax.swing.JPanel bookingspanel;
    private javax.swing.JPanel border2;
    private javax.swing.JPanel border3;
    private javax.swing.JLabel busy;
    private javax.swing.JLabel busynum;
    private javax.swing.JScrollPane cleanersscrollpane;
    private javax.swing.JLabel dashboard1;
    private javax.swing.JPanel dashpanel;
    private javax.swing.JLabel delete;
    private javax.swing.JPanel deletepanel;
    private javax.swing.JLabel employee;
    private javax.swing.JLabel employeeheader;
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
    private javax.swing.JLabel offduty;
    private javax.swing.JLabel offdutynum;
    private javax.swing.JPanel red;
    private javax.swing.JLabel refreshlist;
    private javax.swing.JPanel refreshlistpanel;
    private javax.swing.JLabel reports;
    private javax.swing.JPanel reportspanel;
    private javax.swing.JComboBox<String> reporttypecombobox;
    private javax.swing.JLabel review;
    private javax.swing.JTextField searchfield;
    private javax.swing.JLabel services;
    private javax.swing.JPanel servicespanel;
    private javax.swing.JLabel settings;
    private javax.swing.JPanel skyblue;
    private javax.swing.JLabel total;
    private javax.swing.JLabel totalnum;
    private javax.swing.JLabel update;
    private javax.swing.JPanel updatepanel;
    private javax.swing.JPanel userpanel;
    private javax.swing.JLabel users;
    private javax.swing.JPanel yellow;
    // End of variables declaration//GEN-END:variables

}
