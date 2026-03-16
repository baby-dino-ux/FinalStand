/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

// ── Imports ──────────────────────────────────────────────────────────────
import AdminInternalPage.Profile;
import Admin.Services;
import Admin.Users;
import Admin.Employee;
import Admin.Bookings;
import Staff.Feedback;
import Session.Session;
import config.config;
import LoginandRegister.LoginForm;
import javax.swing.JOptionPane;
public class Reports extends javax.swing.JFrame {

   
// ── Constructor ──────────────────────────────────────────────────────────
public Reports() {
    initComponents();
    loadSessionInfo();
    loadStats();
    loadTopCleaners();

    // ── Populate report type combobox with real options ───────────────────
    reporttypecombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{
        "All Bookings",
        "Completed Only",
        "Pending Only",
        "Cancelled Only",
        "In Progress Only",
        "By Cleaner",
        "By Service",
        "Revenue Summary"
    }));

    // ── Date field placeholders ───────────────────────────────────────────
    setPlaceholder(datefromfield, "YYYY-MM-DD");
    setPlaceholder(datetofield,   "YYYY-MM-DD");

    // ── Auto-generate report when combobox changes ────────────────────────
    reporttypecombobox.addActionListener(e -> generateReport());

    // ── Load default report on open ───────────────────────────────────────
    generateReport();
}

private void loadSessionInfo() {
    Session session = Session.getInstance();
    lblUsername.setText(session.isLoggedIn() ? session.getFullName() : "Admin");
}

private void loadStats() {
    config conf = new config();
    Object revenue   = conf.getValue("SELECT COALESCE(SUM(b_price),0) FROM tbl_bookings WHERE b_status = 'Done'");
    Object total     = conf.getValue("SELECT COUNT(*) FROM tbl_bookings");
    Object completed = conf.getValue("SELECT COUNT(*) FROM tbl_bookings WHERE b_status = 'Done'");
    Object cancelled = conf.getValue("SELECT COUNT(*) FROM tbl_bookings WHERE b_status = 'Cancelled'");
    Object pending   = conf.getValue("SELECT COUNT(*) FROM tbl_bookings WHERE b_status = 'Pending'");
    Object progress  = conf.getValue("SELECT COUNT(*) FROM tbl_bookings WHERE b_status = 'In Progress'");

    totalrevenuenum.setText  ("₱" + (revenue   != null ? revenue.toString()   : "0"));
    totalbookingsnum.setText  (total     != null ? total.toString()     : "0");
    totalcompletednum.setText (completed != null ? completed.toString() : "0");
    totalcancellednum.setText (cancelled != null ? cancelled.toString() : "0");
    totalpendingnum.setText   (pending   != null ? pending.toString()   : "0");

    // Summary section
    summarytotalbookingsnum.setText(total     != null ? total.toString()     : "0");
    summarycompletednum.setText    (completed != null ? completed.toString() : "0");
    summaryinprogressnum.setText   (progress  != null ? progress.toString()  : "0");
    summarypendingnum.setText      (pending   != null ? pending.toString()   : "0");
    summarycancellednum.setText    (cancelled != null ? cancelled.toString() : "0");
    summaryrevenuenum.setText      ("₱" + (revenue != null ? revenue.toString() : "0"));
}

private void loadTopCleaners() {
    config conf = new config();
    // Query top 5 employees by number of completed bookings
    String sql = "SELECT b_employee, COUNT(*) as cnt FROM tbl_bookings " +
                 "WHERE b_status = 'Done' GROUP BY b_employee ORDER BY cnt DESC LIMIT 5";
    java.util.List<String[]> rows = new java.util.ArrayList<>();
    try (java.sql.Connection conn = config.connectDB();
         java.sql.PreparedStatement ps = conn.prepareStatement(sql);
         java.sql.ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            rows.add(new String[]{ rs.getString("b_employee"), rs.getString("cnt") });
        }
    } catch (java.sql.SQLException e) {
        System.out.println("Top cleaners error: " + e.getMessage());
    }

    javax.swing.JLabel[] names = { firstname, secondname, thirdname, fourthname, fifthname };
    javax.swing.JLabel[] counts = { firstnamenumoftask, secondnamenumoftask, thirdnamenumoftask, fourthnamenumoftask, fifthnamenumoftask };

    for (int i = 0; i < 5; i++) {
        if (i < rows.size()) {
            names[i].setText(rows.get(i)[0]);
            counts[i].setText(rows.get(i)[1] + " jobs");
        } else {
            names[i].setText("—");
            counts[i].setText("—");
        }
    }
}

// ── Placeholder helper for date fields ───────────────────────────────────
private void setPlaceholder(javax.swing.JTextField field, String placeholder) {
    field.setText(placeholder);
    field.setForeground(java.awt.Color.GRAY);
    field.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(java.awt.event.FocusEvent e) {
            if (field.getText().equals(placeholder)) {
                field.setText("");
                field.setForeground(java.awt.Color.WHITE);
            }
        }
        public void focusLost(java.awt.event.FocusEvent e) {
            if (field.getText().isEmpty()) {
                field.setText(placeholder);
                field.setForeground(java.awt.Color.GRAY);
            }
        }
    });
}

// ── Generate button ───────────────────────────────────────────────────────
private void generateReport() {
    String type     = reporttypecombobox.getSelectedItem() != null
                      ? reporttypecombobox.getSelectedItem().toString() : "All Bookings";
    String dateFrom = datefromfield.getText().trim();
    String dateTo   = datetofield.getText().trim();

    // Ignore placeholder text
    boolean hasDateFilter = !dateFrom.isEmpty() && !dateFrom.equals("YYYY-MM-DD")
                         && !dateTo.isEmpty()   && !dateTo.equals("YYYY-MM-DD");
    String whereDate = hasDateFilter
        ? " AND b_date BETWEEN '" + dateFrom + "' AND '" + dateTo + "'"
        : "";

    String sql;
    switch (type) {
        case "Completed Only":
            sql = "SELECT b_id AS 'ID', b_customer AS 'Customer', b_service AS 'Service', " +
                  "b_employee AS 'Cleaner', b_date AS 'Date', b_price AS 'Price', b_status AS 'Status' " +
                  "FROM tbl_bookings WHERE b_status = 'Done'" + whereDate + " ORDER BY b_date DESC";
            break;
        case "Pending Only":
            sql = "SELECT b_id AS 'ID', b_customer AS 'Customer', b_service AS 'Service', " +
                  "b_employee AS 'Cleaner', b_date AS 'Date', b_price AS 'Price', b_status AS 'Status' " +
                  "FROM tbl_bookings WHERE b_status = 'Pending'" + whereDate + " ORDER BY b_date DESC";
            break;
        case "Cancelled Only":
            sql = "SELECT b_id AS 'ID', b_customer AS 'Customer', b_service AS 'Service', " +
                  "b_employee AS 'Cleaner', b_date AS 'Date', b_price AS 'Price', b_status AS 'Status' " +
                  "FROM tbl_bookings WHERE b_status = 'Cancelled'" + whereDate + " ORDER BY b_date DESC";
            break;
        case "In Progress Only":
            sql = "SELECT b_id AS 'ID', b_customer AS 'Customer', b_service AS 'Service', " +
                  "b_employee AS 'Cleaner', b_date AS 'Date', b_price AS 'Price', b_status AS 'Status' " +
                  "FROM tbl_bookings WHERE b_status = 'In Progress'" + whereDate + " ORDER BY b_date DESC";
            break;
        case "By Cleaner":
            sql = "SELECT b_employee AS 'Cleaner', " +
                  "COUNT(*) AS 'Total Jobs', " +
                  "SUM(CASE WHEN b_status='Done' THEN 1 ELSE 0 END) AS 'Completed', " +
                  "SUM(CASE WHEN b_status='Cancelled' THEN 1 ELSE 0 END) AS 'Cancelled', " +
                  "SUM(CASE WHEN b_status='Done' THEN b_price ELSE 0 END) AS 'Revenue' " +
                  "FROM tbl_bookings WHERE 1=1" + whereDate +
                  " GROUP BY b_employee ORDER BY Revenue DESC";
            break;
        case "By Service":
            sql = "SELECT b_service AS 'Service', " +
                  "COUNT(*) AS 'Total Bookings', " +
                  "SUM(CASE WHEN b_status='Done' THEN 1 ELSE 0 END) AS 'Completed', " +
                  "SUM(CASE WHEN b_status='Done' THEN b_price ELSE 0 END) AS 'Revenue' " +
                  "FROM tbl_bookings WHERE 1=1" + whereDate +
                  " GROUP BY b_service ORDER BY Revenue DESC";
            break;
        case "Revenue Summary":
            sql = "SELECT b_date AS 'Date', " +
                  "COUNT(*) AS 'Bookings', " +
                  "SUM(CASE WHEN b_status='Done' THEN b_price ELSE 0 END) AS 'Revenue', " +
                  "SUM(CASE WHEN b_status='Done' THEN 1 ELSE 0 END) AS 'Completed', " +
                  "SUM(CASE WHEN b_status='Pending' THEN 1 ELSE 0 END) AS 'Pending' " +
                  "FROM tbl_bookings WHERE 1=1" + whereDate +
                  " GROUP BY b_date ORDER BY b_date DESC";
            break;
        default: // All Bookings
            sql = "SELECT b_id AS 'ID', b_customer AS 'Customer', b_service AS 'Service', " +
                  "b_employee AS 'Cleaner', b_date AS 'Date', b_price AS 'Price', b_status AS 'Status' " +
                  "FROM tbl_bookings WHERE 1=1" + whereDate + " ORDER BY b_date DESC";
    }
    new config().displayData(sql, TableRecentType);
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        border3 = new javax.swing.JPanel();
        totalcancelledpanel = new javax.swing.JPanel();
        totalbookingsborder1 = new javax.swing.JPanel();
        red = new javax.swing.JPanel();
        totalcancellednum = new javax.swing.JLabel();
        cancelled = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lg = new javax.swing.JLabel();
        Reportsheader = new javax.swing.JLabel();
        exit1 = new javax.swing.JLabel();
        totalrevenuepanel = new javax.swing.JPanel();
        blue1 = new javax.swing.JPanel();
        blue = new javax.swing.JPanel();
        totalrevenuenum = new javax.swing.JLabel();
        totalrevenue = new javax.swing.JLabel();
        datefromfield = new javax.swing.JTextField();
        totalbookingspanel = new javax.swing.JPanel();
        green1 = new javax.swing.JPanel();
        green = new javax.swing.JPanel();
        totalbookingsnum = new javax.swing.JLabel();
        totalbookings = new javax.swing.JLabel();
        totalcompletedpanel = new javax.swing.JPanel();
        skyblue1 = new javax.swing.JPanel();
        skyblue = new javax.swing.JPanel();
        totalcompletednum = new javax.swing.JLabel();
        completed = new javax.swing.JLabel();
        border2 = new javax.swing.JPanel();
        generatepanel = new javax.swing.JPanel();
        generate = new javax.swing.JLabel();
        reporttypecombobox = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        topcleaners = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        five = new javax.swing.JLabel();
        one = new javax.swing.JLabel();
        two = new javax.swing.JLabel();
        three = new javax.swing.JLabel();
        fifthname = new javax.swing.JLabel();
        firstname = new javax.swing.JLabel();
        secondname = new javax.swing.JLabel();
        thirdname = new javax.swing.JLabel();
        four = new javax.swing.JLabel();
        fourthname = new javax.swing.JLabel();
        fifthnamenumoftask = new javax.swing.JLabel();
        firstnamenumoftask = new javax.swing.JLabel();
        secondnamenumoftask = new javax.swing.JLabel();
        thirdnamenumoftask = new javax.swing.JLabel();
        fourthnamenumoftask = new javax.swing.JLabel();
        reporttype = new javax.swing.JLabel();
        totalpendingpanel = new javax.swing.JPanel();
        yellow1 = new javax.swing.JPanel();
        yellow = new javax.swing.JPanel();
        totalpendingnum = new javax.swing.JLabel();
        pending = new javax.swing.JLabel();
        dateto = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        summary = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        summaryrevenuenum = new javax.swing.JLabel();
        summaryrevenue = new javax.swing.JLabel();
        summarycancellednum = new javax.swing.JLabel();
        summarycompleted = new javax.swing.JLabel();
        summaryinprogress = new javax.swing.JLabel();
        summarypending = new javax.swing.JLabel();
        summarycompletednum = new javax.swing.JLabel();
        summaryinprogressnum = new javax.swing.JLabel();
        summarypendingnum = new javax.swing.JLabel();
        summarytotalbookings = new javax.swing.JLabel();
        summarytotalbookingsnum = new javax.swing.JLabel();
        summarycancelled = new javax.swing.JLabel();
        datefrom = new javax.swing.JLabel();
        datetofield = new javax.swing.JTextField();
        reportypescrollpane = new javax.swing.JScrollPane();
        TableRecentType = new javax.swing.JTable();
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

        border3.setBackground(new java.awt.Color(153, 255, 255));
        border3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                border3MouseClicked(evt);
            }
        });
        border3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(border3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 490));

        totalcancelledpanel.setBackground(new java.awt.Color(29, 45, 61));
        totalcancelledpanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        totalcancelledpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        totalbookingsborder1.setBackground(new java.awt.Color(255, 51, 51));
        totalbookingsborder1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        totalcancelledpanel.add(totalbookingsborder1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 80));

        red.setBackground(new java.awt.Color(255, 51, 51));
        red.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        totalcancelledpanel.add(red, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 40, 40));

        totalcancellednum.setFont(new java.awt.Font("Bahnschrift", 0, 24)); // NOI18N
        totalcancellednum.setForeground(new java.awt.Color(255, 255, 255));
        totalcancellednum.setText("0");
        totalcancelledpanel.add(totalcancellednum, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 60, 30));

        cancelled.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        cancelled.setForeground(new java.awt.Color(255, 255, 255));
        cancelled.setText("Cancelled");
        totalcancelledpanel.add(cancelled, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 80, 30));

        jPanel4.add(totalcancelledpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 70, 120, 80));

        jPanel1.setBackground(new java.awt.Color(29, 45, 61));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lg.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        lg.setForeground(new java.awt.Color(239, 234, 234));
        lg.setText("Log Out");
        jPanel1.add(lg, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, -1, 20));

        Reportsheader.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        Reportsheader.setForeground(new java.awt.Color(239, 234, 234));
        Reportsheader.setText("Reports");
        jPanel1.add(Reportsheader, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 190, 50));

        exit1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout (2).png"))); // NOI18N
        exit1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exit1MouseClicked(evt);
            }
        });
        jPanel1.add(exit1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, 30, 20));

        jPanel4.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 630, 50));

        totalrevenuepanel.setBackground(new java.awt.Color(29, 45, 61));
        totalrevenuepanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        totalrevenuepanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        blue1.setBackground(new java.awt.Color(0, 102, 255));
        blue1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        totalrevenuepanel.add(blue1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 80));

        blue.setBackground(new java.awt.Color(0, 102, 255));
        blue.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        totalrevenuepanel.add(blue, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 40, 40));

        totalrevenuenum.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        totalrevenuenum.setForeground(new java.awt.Color(255, 255, 255));
        totalrevenuenum.setText("0");
        totalrevenuepanel.add(totalrevenuenum, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 70, -1));

        totalrevenue.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        totalrevenue.setForeground(new java.awt.Color(255, 255, 255));
        totalrevenue.setText("Total Revenue");
        totalrevenuepanel.add(totalrevenue, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 80, 30));

        jPanel4.add(totalrevenuepanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 120, 80));

        datefromfield.setBackground(new java.awt.Color(29, 45, 61));
        datefromfield.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        datefromfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                datefromfieldActionPerformed(evt);
            }
        });
        jPanel4.add(datefromfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 170, 110, -1));

        totalbookingspanel.setBackground(new java.awt.Color(29, 45, 61));
        totalbookingspanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        totalbookingspanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        green1.setBackground(new java.awt.Color(0, 255, 153));
        green1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        totalbookingspanel.add(green1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 80));

        green.setBackground(new java.awt.Color(0, 255, 153));
        green.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        totalbookingspanel.add(green, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 40, 40));

        totalbookingsnum.setFont(new java.awt.Font("Bahnschrift", 0, 24)); // NOI18N
        totalbookingsnum.setForeground(new java.awt.Color(255, 255, 255));
        totalbookingsnum.setText("0");
        totalbookingspanel.add(totalbookingsnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 60, -1));

        totalbookings.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        totalbookings.setForeground(new java.awt.Color(255, 255, 255));
        totalbookings.setText("Total Bookings");
        totalbookingspanel.add(totalbookings, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 80, 30));

        jPanel4.add(totalbookingspanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 70, 120, 80));

        totalcompletedpanel.setBackground(new java.awt.Color(29, 45, 61));
        totalcompletedpanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        totalcompletedpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        skyblue1.setBackground(new java.awt.Color(153, 255, 255));
        skyblue1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        totalcompletedpanel.add(skyblue1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 80));

        skyblue.setBackground(new java.awt.Color(153, 255, 255));
        skyblue.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        totalcompletedpanel.add(skyblue, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 40, 40));

        totalcompletednum.setFont(new java.awt.Font("Bahnschrift", 0, 24)); // NOI18N
        totalcompletednum.setForeground(new java.awt.Color(255, 255, 255));
        totalcompletednum.setText("0");
        totalcompletedpanel.add(totalcompletednum, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 60, -1));

        completed.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        completed.setForeground(new java.awt.Color(255, 255, 255));
        completed.setText("Completed");
        totalcompletedpanel.add(completed, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 80, 30));

        jPanel4.add(totalcompletedpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 70, 130, 80));

        border2.setBackground(new java.awt.Color(153, 255, 255));
        border2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        border2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                border2MouseClicked(evt);
            }
        });
        border2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(border2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 630, 10));

        generatepanel.setBackground(new java.awt.Color(153, 255, 255));
        generatepanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                generatepanelMouseClicked(evt);
            }
        });
        generatepanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        generate.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        generate.setText("Generate");
        generatepanel.add(generate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 60, 20));

        jPanel4.add(generatepanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 170, 90, 20));

        reporttypecombobox.setBackground(new java.awt.Color(29, 45, 61));
        reporttypecombobox.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        reporttypecombobox.setForeground(new java.awt.Color(29, 45, 61));
        reporttypecombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        reporttypecombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reporttypecomboboxActionPerformed(evt);
            }
        });
        jPanel4.add(reporttypecombobox, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 170, 100, -1));

        jPanel8.setBackground(new java.awt.Color(28, 69, 91));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel9.setBackground(new java.awt.Color(28, 69, 91));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        topcleaners.setBackground(new java.awt.Color(153, 255, 255));
        topcleaners.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        topcleaners.setForeground(new java.awt.Color(153, 255, 255));
        topcleaners.setText("TOP CLEANERS");
        jPanel9.add(topcleaners, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 100, 30));

        jPanel8.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 30));

        jPanel10.setBackground(new java.awt.Color(28, 69, 91));
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel8.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 170, 20));

        five.setBackground(new java.awt.Color(0, 255, 153));
        five.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        five.setForeground(new java.awt.Color(153, 255, 255));
        five.setText("5");
        five.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel8.add(five, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 10, 20));

        one.setBackground(new java.awt.Color(0, 255, 153));
        one.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        one.setForeground(new java.awt.Color(153, 255, 255));
        one.setText("1");
        one.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel8.add(one, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 10, 20));

        two.setBackground(new java.awt.Color(0, 255, 153));
        two.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        two.setForeground(new java.awt.Color(153, 255, 255));
        two.setText("2");
        two.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel8.add(two, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 10, 20));

        three.setBackground(new java.awt.Color(0, 255, 153));
        three.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        three.setForeground(new java.awt.Color(153, 255, 255));
        three.setText("3");
        three.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel8.add(three, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 10, 20));

        fifthname.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        fifthname.setForeground(new java.awt.Color(255, 255, 255));
        fifthname.setText("Name");
        jPanel8.add(fifthname, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 80, 20));

        firstname.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        firstname.setForeground(new java.awt.Color(255, 255, 255));
        firstname.setText("Name");
        jPanel8.add(firstname, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 80, 20));

        secondname.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        secondname.setForeground(new java.awt.Color(255, 255, 255));
        secondname.setText("Name");
        jPanel8.add(secondname, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 80, 20));

        thirdname.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        thirdname.setForeground(new java.awt.Color(255, 255, 255));
        thirdname.setText("Name");
        jPanel8.add(thirdname, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 80, 20));

        four.setBackground(new java.awt.Color(0, 255, 153));
        four.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        four.setForeground(new java.awt.Color(153, 255, 255));
        four.setText("4");
        four.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel8.add(four, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 10, 20));

        fourthname.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        fourthname.setForeground(new java.awt.Color(255, 255, 255));
        fourthname.setText("Name");
        jPanel8.add(fourthname, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 80, 20));

        fifthnamenumoftask.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        fifthnamenumoftask.setText("No. Task");
        jPanel8.add(fifthnamenumoftask, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, 50, 20));

        firstnamenumoftask.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        firstnamenumoftask.setText("No. Task");
        jPanel8.add(firstnamenumoftask, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 50, 20));

        secondnamenumoftask.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        secondnamenumoftask.setText("No. Task");
        jPanel8.add(secondnamenumoftask, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 50, 20));

        thirdnamenumoftask.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        thirdnamenumoftask.setText("No. Task");
        jPanel8.add(thirdnamenumoftask, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 50, 20));

        fourthnamenumoftask.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        fourthnamenumoftask.setText("No. Task");
        jPanel8.add(fourthnamenumoftask, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 50, 20));

        jPanel4.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 320, 170, 160));

        reporttype.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        reporttype.setForeground(new java.awt.Color(255, 255, 255));
        reporttype.setText("Report Type");
        jPanel4.add(reporttype, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 150, 110, 20));

        totalpendingpanel.setBackground(new java.awt.Color(29, 45, 61));
        totalpendingpanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        totalpendingpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        yellow1.setBackground(new java.awt.Color(255, 255, 51));
        yellow1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        totalpendingpanel.add(yellow1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 80));

        yellow.setBackground(new java.awt.Color(255, 255, 51));
        yellow.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        totalpendingpanel.add(yellow, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 40, 40));

        totalpendingnum.setFont(new java.awt.Font("Bahnschrift", 0, 24)); // NOI18N
        totalpendingnum.setForeground(new java.awt.Color(255, 255, 255));
        totalpendingnum.setText("0");
        totalpendingpanel.add(totalpendingnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 60, -1));

        pending.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        pending.setForeground(new java.awt.Color(255, 255, 255));
        pending.setText("Pending");
        totalpendingpanel.add(pending, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 80, 30));

        jPanel4.add(totalpendingpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 70, 120, 80));

        dateto.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        dateto.setForeground(new java.awt.Color(255, 255, 255));
        dateto.setText("Date To");
        jPanel4.add(dateto, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 150, 110, 20));

        jPanel2.setBackground(new java.awt.Color(29, 45, 61));
        jPanel4.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 150, 140));

        jPanel5.setBackground(new java.awt.Color(28, 69, 91));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(28, 69, 91));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        summary.setBackground(new java.awt.Color(153, 255, 255));
        summary.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        summary.setForeground(new java.awt.Color(153, 255, 255));
        summary.setText("SUMMARY");
        jPanel6.add(summary, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 60, 30));

        jPanel5.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 30));

        jPanel7.setBackground(new java.awt.Color(28, 69, 91));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        summaryrevenuenum.setBackground(new java.awt.Color(0, 255, 153));
        summaryrevenuenum.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        summaryrevenuenum.setForeground(new java.awt.Color(240, 240, 240));
        summaryrevenuenum.setText("0");
        summaryrevenuenum.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel7.add(summaryrevenuenum, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, 70, 30));

        summaryrevenue.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        summaryrevenue.setForeground(new java.awt.Color(255, 255, 255));
        summaryrevenue.setText("Revenue");
        jPanel7.add(summaryrevenue, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 80, 30));

        jPanel5.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 170, 30));

        summarycancellednum.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        summarycancellednum.setForeground(new java.awt.Color(255, 51, 51));
        summarycancellednum.setText("0");
        summarycancellednum.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel5.add(summarycancellednum, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, 70, 20));

        summarycompleted.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        summarycompleted.setText("Completed");
        jPanel5.add(summarycompleted, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 90, 20));

        summaryinprogress.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        summaryinprogress.setText("In Progress");
        jPanel5.add(summaryinprogress, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 90, 20));

        summarypending.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        summarypending.setText("Pending");
        jPanel5.add(summarypending, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 90, 20));

        summarycompletednum.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        summarycompletednum.setForeground(new java.awt.Color(153, 255, 255));
        summarycompletednum.setText("0");
        summarycompletednum.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel5.add(summarycompletednum, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 70, 20));

        summaryinprogressnum.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        summaryinprogressnum.setForeground(new java.awt.Color(255, 51, 255));
        summaryinprogressnum.setText("0");
        summaryinprogressnum.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel5.add(summaryinprogressnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 70, 20));

        summarypendingnum.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        summarypendingnum.setForeground(new java.awt.Color(255, 255, 51));
        summarypendingnum.setText("0");
        summarypendingnum.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel5.add(summarypendingnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 70, 20));

        summarytotalbookings.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        summarytotalbookings.setText("Total Bookings");
        jPanel5.add(summarytotalbookings, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 90, 20));

        summarytotalbookingsnum.setBackground(new java.awt.Color(0, 255, 153));
        summarytotalbookingsnum.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        summarytotalbookingsnum.setForeground(new java.awt.Color(0, 255, 153));
        summarytotalbookingsnum.setText("0");
        summarytotalbookingsnum.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel5.add(summarytotalbookingsnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 70, 20));

        summarycancelled.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        summarycancelled.setText("Cancelled");
        jPanel5.add(summarycancelled, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 90, 20));

        jPanel4.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 150, 170, 170));

        datefrom.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        datefrom.setForeground(new java.awt.Color(255, 255, 255));
        datefrom.setText("Date From");
        jPanel4.add(datefrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 150, 110, 20));

        datetofield.setBackground(new java.awt.Color(29, 45, 61));
        datetofield.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        datetofield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                datetofieldActionPerformed(evt);
            }
        });
        jPanel4.add(datetofield, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 170, 110, -1));

        reportypescrollpane.setBackground(new java.awt.Color(29, 45, 61));
        reportypescrollpane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        reportypescrollpane.setForeground(new java.awt.Color(255, 255, 255));
        reportypescrollpane.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N

        TableRecentType.setBackground(new java.awt.Color(29, 45, 61));
        TableRecentType.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        TableRecentType.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        TableRecentType.setForeground(new java.awt.Color(255, 255, 255));
        TableRecentType.setModel(new javax.swing.table.DefaultTableModel(
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
        TableRecentType.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        reportypescrollpane.setViewportView(TableRecentType);

        jPanel4.add(reportypescrollpane, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, 420, 290));

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

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 490));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void border3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_border3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_border3MouseClicked

    private void exit1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exit1MouseClicked
 int c = JOptionPane.showConfirmDialog(this, "Log out?", "Log Out", JOptionPane.YES_NO_OPTION);
    if (c == JOptionPane.YES_OPTION) { Session.getInstance().logout(); new LoginForm().setVisible(true); this.dispose(); }

    }//GEN-LAST:event_exit1MouseClicked

    private void datefromfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_datefromfieldActionPerformed
        generateReport();
    }//GEN-LAST:event_datefromfieldActionPerformed

    private void border2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_border2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_border2MouseClicked

    private void reporttypecomboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reporttypecomboboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_reporttypecomboboxActionPerformed

    private void datetofieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_datetofieldActionPerformed
        generateReport();
    }//GEN-LAST:event_datetofieldActionPerformed

    private void generatepanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_generatepanelMouseClicked
  generateReport();
    }//GEN-LAST:event_generatepanelMouseClicked

    private void settingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsMouseClicked
        Profile a = new Profile();
        a.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_settingsMouseClicked

    private void dashpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashpanelMouseClicked
        AdminDash a = new AdminDash();
        a.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_dashpanelMouseClicked

    private void userpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userpanelMouseClicked
        // Open List of Users
        Users lou = new Users();
        lou.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_userpanelMouseClicked

    private void employeepanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employeepanelMouseClicked
        Employee e = new Employee();
        e.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_employeepanelMouseClicked

    private void servicespanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_servicespanelMouseClicked
        Services s = new Services();
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
                new AdminDash().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Reportsheader;
    private javax.swing.JTable TableRecentType;
    private javax.swing.JLabel administrator;
    private javax.swing.JPanel blue;
    private javax.swing.JPanel blue1;
    private javax.swing.JLabel bookings;
    private javax.swing.JPanel bookingspanel;
    private javax.swing.JPanel border2;
    private javax.swing.JPanel border3;
    private javax.swing.JLabel cancelled;
    private javax.swing.JLabel completed;
    private javax.swing.JLabel dashboard1;
    private javax.swing.JPanel dashpanel;
    private javax.swing.JLabel datefrom;
    private javax.swing.JTextField datefromfield;
    private javax.swing.JLabel dateto;
    private javax.swing.JTextField datetofield;
    private javax.swing.JLabel employee;
    private javax.swing.JPanel employeepanel;
    private javax.swing.JLabel exit1;
    private javax.swing.JLabel feedback;
    private javax.swing.JPanel feedbackpanel;
    private javax.swing.JLabel fifthname;
    private javax.swing.JLabel fifthnamenumoftask;
    private javax.swing.JLabel firstname;
    private javax.swing.JLabel firstnamenumoftask;
    private javax.swing.JLabel five;
    private javax.swing.JLabel four;
    private javax.swing.JLabel fourthname;
    private javax.swing.JLabel fourthnamenumoftask;
    private javax.swing.JLabel generate;
    private javax.swing.JPanel generatepanel;
    private javax.swing.JPanel green;
    private javax.swing.JPanel green1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lg;
    private javax.swing.JPanel listadmin;
    private javax.swing.JLabel one;
    private javax.swing.JLabel pending;
    private javax.swing.JPanel red;
    private javax.swing.JLabel reports;
    private javax.swing.JPanel reportspanel;
    private javax.swing.JLabel reporttype;
    private javax.swing.JComboBox<String> reporttypecombobox;
    private javax.swing.JScrollPane reportypescrollpane;
    private javax.swing.JLabel review;
    private javax.swing.JLabel secondname;
    private javax.swing.JLabel secondnamenumoftask;
    private javax.swing.JLabel services;
    private javax.swing.JPanel servicespanel;
    private javax.swing.JLabel settings;
    private javax.swing.JPanel skyblue;
    private javax.swing.JPanel skyblue1;
    private javax.swing.JLabel summary;
    private javax.swing.JLabel summarycancelled;
    private javax.swing.JLabel summarycancellednum;
    private javax.swing.JLabel summarycompleted;
    private javax.swing.JLabel summarycompletednum;
    private javax.swing.JLabel summaryinprogress;
    private javax.swing.JLabel summaryinprogressnum;
    private javax.swing.JLabel summarypending;
    private javax.swing.JLabel summarypendingnum;
    private javax.swing.JLabel summaryrevenue;
    private javax.swing.JLabel summaryrevenuenum;
    private javax.swing.JLabel summarytotalbookings;
    private javax.swing.JLabel summarytotalbookingsnum;
    private javax.swing.JLabel thirdname;
    private javax.swing.JLabel thirdnamenumoftask;
    private javax.swing.JLabel three;
    private javax.swing.JLabel topcleaners;
    private javax.swing.JLabel totalbookings;
    private javax.swing.JPanel totalbookingsborder1;
    private javax.swing.JLabel totalbookingsnum;
    private javax.swing.JPanel totalbookingspanel;
    private javax.swing.JLabel totalcancellednum;
    private javax.swing.JPanel totalcancelledpanel;
    private javax.swing.JLabel totalcompletednum;
    private javax.swing.JPanel totalcompletedpanel;
    private javax.swing.JLabel totalpendingnum;
    private javax.swing.JPanel totalpendingpanel;
    private javax.swing.JLabel totalrevenue;
    private javax.swing.JLabel totalrevenuenum;
    private javax.swing.JPanel totalrevenuepanel;
    private javax.swing.JLabel two;
    private javax.swing.JPanel userpanel;
    private javax.swing.JLabel users;
    private javax.swing.JPanel yellow;
    private javax.swing.JPanel yellow1;
    // End of variables declaration//GEN-END:variables
}
