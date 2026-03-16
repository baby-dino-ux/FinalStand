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
import Session.Session;
import config.config;
import LoginandRegister.LoginForm;
import javax.swing.JOptionPane;
public class Feedback extends javax.swing.JFrame {

  
// ── Constructor ──────────────────────────────────────────────────────────
public Feedback() {
    initComponents();
    loadSessionInfo();
    loadFeedback(null);
    loadStats();
    // Wire live search
    searchfield.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
        public void insertUpdate(javax.swing.event.DocumentEvent e)  { doSearch(); }
        public void removeUpdate(javax.swing.event.DocumentEvent e)  { doSearch(); }
        public void changedUpdate(javax.swing.event.DocumentEvent e) { doSearch(); }
        private void doSearch() {
            String kw = searchfield.getText().trim();
            if (kw.isEmpty() || kw.equals("Search ")) { loadFeedback(null); return; }
            new config().displayData(
                "SELECT f_id AS 'ID', f_employee AS 'Employee', f_rating AS 'Rating', " +
                "f_comment AS 'Comment', f_date AS 'Date' FROM tbl_feedback " +
                "WHERE f_employee LIKE '%" + kw + "%' OR f_rating LIKE '%" + kw + "%' " +
                "OR f_comment LIKE '%" + kw + "%' ORDER BY f_id DESC",
                TableFeedback);
        }
    });
}

private void loadSessionInfo() {
    Session session = Session.getInstance();
    lblUsername.setText(session.isLoggedIn() ? session.getFullName() : "Admin");
  
}

private void loadStats() {
    config conf = new config();

    // ── Feedback rating counts → correct labels in jPanel5 ───────────────
    Object total = conf.getValue("SELECT COUNT(*) FROM tbl_feedback");
    Object vg    = conf.getValue("SELECT COUNT(*) FROM tbl_feedback WHERE f_rating = 'Very Good'");
    Object good  = conf.getValue("SELECT COUNT(*) FROM tbl_feedback WHERE f_rating = 'Good'");
    Object bad   = conf.getValue("SELECT COUNT(*) FROM tbl_feedback WHERE f_rating = 'Bad'");
    Object vb    = conf.getValue("SELECT COUNT(*) FROM tbl_feedback WHERE f_rating = 'Very Bad'");

    // totalnum  = black panel  = Total feedback count
    totalnum.setText(total != null ? total.toString() : "0");

    // verygoodnum = skyblue panel = Very Good + Good combined (label says "VG/G")
    long vgCount   = vg   != null ? Long.parseLong(vg.toString())   : 0;
    long goodCount = good != null ? Long.parseLong(good.toString())  : 0;
    verygoodnum.setText(String.valueOf(vgCount + goodCount));

    // badnum = yellow panel = Bad count
    badnum.setText(bad != null ? bad.toString() : "0");

    // verybadnum = red panel = Very Bad count
    verybadnum.setText(vb != null ? vb.toString() : "0");

    // ── Admin dashboard stat cards in jPanel4 ────────────────────────────
    Object users    = conf.getValue("SELECT COUNT(*) FROM tbl_users WHERE type != 'Admin'");
    Object emps     = conf.getValue("SELECT COUNT(*) FROM tbl_users WHERE type = 'Employee'");
    Object bookings = conf.getValue("SELECT COUNT(*) FROM tbl_bookings");
    Object pending  = conf.getValue("SELECT COUNT(*) FROM tbl_bookings WHERE b_status = 'Pending'");
    Object services = conf.getValue("SELECT COUNT(*) FROM tbl_services");

    totalbookingsnum.setText (users    != null ? users.toString()    : "0"); // Total Users  (blue)
    totalbookingsnum4.setText(emps     != null ? emps.toString()     : "0"); // Employees    (skyblue)
    totalbookingsnum3.setText(bookings != null ? bookings.toString() : "0"); // Bookings     (green)
    totalbookingsnum2.setText(pending  != null ? pending.toString()  : "0"); // Pending      (yellow)
    totalbookingsnum1.setText(services != null ? services.toString() : "0"); // Services     (red)

    // ── Recent bookings table (jPanel4) ──────────────────────────────────
    conf.displayData(
        "SELECT b_id AS 'ID', b_customer AS 'Customer', b_service AS 'Service', " +
        "b_date AS 'Date', b_status AS 'Status' " +
        "FROM tbl_bookings ORDER BY b_id DESC LIMIT 8",
        TableRecentBookings);

    // ── Employee work status table (jPanel4) ─────────────────────────────
    conf.displayData(
        "SELECT (firstname||' '||lastname) AS 'Name', work_status AS 'Status' " +
        "FROM tbl_users WHERE type = 'Employee' ORDER BY work_status ASC",
        TableRecentBookings1);
}

private void loadFeedback(String ratingFilter) {
    config conf = new config();
    String sql;
    if (ratingFilter == null) {
        sql = "SELECT f_id AS 'ID', f_employee AS 'Employee', f_rating AS 'Rating', " +
              "f_comment AS 'Comment', f_date AS 'Date' FROM tbl_feedback ORDER BY f_id DESC";
    } else {
        sql = "SELECT f_id AS 'ID', f_employee AS 'Employee', f_rating AS 'Rating', " +
              "f_comment AS 'Comment', f_date AS 'Date' FROM tbl_feedback " +
              "WHERE f_rating = '" + ratingFilter + "' ORDER BY f_id DESC";
    }
    conf.displayData(sql, TableFeedback);
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        listadmin = new javax.swing.JPanel();
        settings = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        dashpanel = new javax.swing.JPanel();
        dashboard1 = new javax.swing.JLabel();
        userpanel = new javax.swing.JPanel();
        users = new javax.swing.JLabel();
        mstaffpanel = new javax.swing.JPanel();
        mstaff = new javax.swing.JLabel();
        memployeepanel = new javax.swing.JPanel();
        memployee = new javax.swing.JLabel();
        servicepanel = new javax.swing.JPanel();
        service = new javax.swing.JLabel();
        bookingpanel = new javax.swing.JPanel();
        booking = new javax.swing.JLabel();
        reportspanel = new javax.swing.JPanel();
        review = new javax.swing.JLabel();
        reports = new javax.swing.JLabel();
        lblUsername1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lg = new javax.swing.JLabel();
        exit = new javax.swing.JLabel();
        admindashboard = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        totalbookingspanel4 = new javax.swing.JPanel();
        totalbookingsborder4 = new javax.swing.JPanel();
        color5 = new javax.swing.JPanel();
        totalbookingsnum4 = new javax.swing.JLabel();
        totalbookings5 = new javax.swing.JLabel();
        totalbookingspanel3 = new javax.swing.JPanel();
        totalbookingsborder3 = new javax.swing.JPanel();
        color4 = new javax.swing.JPanel();
        totalbookingsnum3 = new javax.swing.JLabel();
        totalbookings4 = new javax.swing.JLabel();
        totalbookingspanel2 = new javax.swing.JPanel();
        totalbookingsborder2 = new javax.swing.JPanel();
        color3 = new javax.swing.JPanel();
        totalbookingsnum2 = new javax.swing.JLabel();
        totalbookings6 = new javax.swing.JLabel();
        totalbookingspanel1 = new javax.swing.JPanel();
        totalbookingsborder1 = new javax.swing.JPanel();
        color2 = new javax.swing.JPanel();
        totalbookingsnum1 = new javax.swing.JLabel();
        totalbookings2 = new javax.swing.JLabel();
        totalbookingspanel = new javax.swing.JPanel();
        totalbookingsborder = new javax.swing.JPanel();
        color1 = new javax.swing.JPanel();
        totalbookingsnum = new javax.swing.JLabel();
        totalbookings1 = new javax.swing.JLabel();
        totalbookings3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableRecentBookings = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableRecentBookings1 = new javax.swing.JTable();
        totalbookings7 = new javax.swing.JLabel();
        border2 = new javax.swing.JPanel();
        border3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lg1 = new javax.swing.JLabel();
        exit1 = new javax.swing.JLabel();
        Feedbackheader = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        border4 = new javax.swing.JPanel();
        border5 = new javax.swing.JPanel();
        verybadpanel = new javax.swing.JPanel();
        verybadbutton = new javax.swing.JLabel();
        badpanel = new javax.swing.JPanel();
        badbutton = new javax.swing.JLabel();
        goodpanel = new javax.swing.JPanel();
        goodbutton = new javax.swing.JLabel();
        verygoodpanel = new javax.swing.JPanel();
        verygoodbutton = new javax.swing.JLabel();
        black = new javax.swing.JPanel();
        totalnum = new javax.swing.JLabel();
        total = new javax.swing.JLabel();
        red = new javax.swing.JPanel();
        verybadnum = new javax.swing.JLabel();
        verybad = new javax.swing.JLabel();
        yellow = new javax.swing.JPanel();
        badnum = new javax.swing.JLabel();
        bad = new javax.swing.JLabel();
        skyblue = new javax.swing.JPanel();
        verygoodnum = new javax.swing.JLabel();
        verygood = new javax.swing.JLabel();
        searchfield = new javax.swing.JTextField();
        feedbackscrollpane = new javax.swing.JScrollPane();
        TableFeedback = new javax.swing.JTable();
        listadmin2 = new javax.swing.JPanel();
        settings2 = new javax.swing.JLabel();
        lblUsername3 = new javax.swing.JLabel();
        dashpanel2 = new javax.swing.JPanel();
        dashboard3 = new javax.swing.JLabel();
        userpanel2 = new javax.swing.JPanel();
        users2 = new javax.swing.JLabel();
        employeepanel = new javax.swing.JPanel();
        employee = new javax.swing.JLabel();
        servicespanel = new javax.swing.JPanel();
        services = new javax.swing.JLabel();
        bookingspanel = new javax.swing.JPanel();
        bookings = new javax.swing.JLabel();
        reportspanel2 = new javax.swing.JPanel();
        reports2 = new javax.swing.JLabel();
        feedbackpanel = new javax.swing.JPanel();
        review2 = new javax.swing.JLabel();
        feedback = new javax.swing.JLabel();
        administrator = new javax.swing.JLabel();
        reporttypecombobox = new javax.swing.JComboBox<>();

        jFrame1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jFrame1.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        dashpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dashboard1.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        dashboard1.setForeground(new java.awt.Color(239, 234, 234));
        dashboard1.setText("Dashboard");
        dashpanel.add(dashboard1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 70, 20));

        listadmin.add(dashpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 160, 40));

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
        mstaff.setText("Employee");
        mstaffpanel.add(mstaff, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 100, -1));

        listadmin.add(mstaffpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 160, 40));

        memployeepanel.setBackground(new java.awt.Color(29, 45, 61));
        memployeepanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        memployee.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        memployee.setForeground(new java.awt.Color(239, 234, 234));
        memployee.setText("Services");
        memployeepanel.add(memployee, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 110, -1));

        listadmin.add(memployeepanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, -1, 40));

        servicepanel.setBackground(new java.awt.Color(29, 45, 61));
        servicepanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                servicepanelMouseClicked(evt);
            }
        });
        servicepanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        service.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        service.setForeground(new java.awt.Color(239, 234, 234));
        service.setText("Bookings");
        servicepanel.add(service, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 60, -1));

        listadmin.add(servicepanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 160, 40));

        bookingpanel.setBackground(new java.awt.Color(29, 45, 61));
        bookingpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        booking.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        booking.setForeground(new java.awt.Color(239, 234, 234));
        booking.setText("Reports");
        bookingpanel.add(booking, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, -1));

        listadmin.add(bookingpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 160, 40));

        reportspanel.setBackground(new java.awt.Color(29, 45, 61));
        reportspanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        review.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        reportspanel.add(review, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 90, -1));

        reports.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        reports.setForeground(new java.awt.Color(239, 234, 234));
        reports.setText("Schedule");
        reportspanel.add(reports, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 60, -1));

        listadmin.add(reportspanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 160, 40));

        lblUsername1.setBackground(new java.awt.Color(153, 255, 255));
        lblUsername1.setFont(new java.awt.Font("Century Gothic", 1, 10)); // NOI18N
        lblUsername1.setForeground(new java.awt.Color(153, 255, 255));
        lblUsername1.setText("ADMINISTRATOR");
        listadmin.add(lblUsername1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 90, 20));

        jFrame1.getContentPane().add(listadmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 150, -1));

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

        admindashboard.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        admindashboard.setForeground(new java.awt.Color(239, 234, 234));
        admindashboard.setText("Admin Dashboard");
        jPanel1.add(admindashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 190, 50));

        jPanel4.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 640, 50));

        jPanel2.setBackground(new java.awt.Color(29, 45, 61));
        jPanel4.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 150, 140));

        totalbookingspanel4.setBackground(new java.awt.Color(29, 45, 61));
        totalbookingspanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        totalbookingspanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        totalbookingsborder4.setBackground(new java.awt.Color(153, 255, 255));
        totalbookingsborder4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        totalbookingspanel4.add(totalbookingsborder4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 90));

        color5.setBackground(new java.awt.Color(153, 255, 255));
        color5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        totalbookingspanel4.add(color5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 40, 40));

        totalbookingsnum4.setFont(new java.awt.Font("Bahnschrift", 0, 24)); // NOI18N
        totalbookingsnum4.setForeground(new java.awt.Color(255, 255, 255));
        totalbookingsnum4.setText("0");
        totalbookingspanel4.add(totalbookingsnum4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 20, -1));

        totalbookings5.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        totalbookings5.setForeground(new java.awt.Color(255, 255, 255));
        totalbookings5.setText("Employees");
        totalbookingspanel4.add(totalbookings5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 80, 30));

        jPanel4.add(totalbookingspanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 70, 130, 80));

        totalbookingspanel3.setBackground(new java.awt.Color(29, 45, 61));
        totalbookingspanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        totalbookingspanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        totalbookingsborder3.setBackground(new java.awt.Color(0, 255, 153));
        totalbookingsborder3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        totalbookingspanel3.add(totalbookingsborder3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 90));

        color4.setBackground(new java.awt.Color(0, 255, 153));
        color4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        totalbookingspanel3.add(color4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 40, 40));

        totalbookingsnum3.setFont(new java.awt.Font("Bahnschrift", 0, 24)); // NOI18N
        totalbookingsnum3.setForeground(new java.awt.Color(255, 255, 255));
        totalbookingsnum3.setText("0");
        totalbookingspanel3.add(totalbookingsnum3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 20, -1));

        totalbookings4.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        totalbookings4.setForeground(new java.awt.Color(255, 255, 255));
        totalbookings4.setText("Total Bookings");
        totalbookingspanel3.add(totalbookings4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 80, 30));

        jPanel4.add(totalbookingspanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 70, 120, 80));

        totalbookingspanel2.setBackground(new java.awt.Color(29, 45, 61));
        totalbookingspanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        totalbookingspanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        totalbookingsborder2.setBackground(new java.awt.Color(255, 255, 51));
        totalbookingsborder2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        totalbookingspanel2.add(totalbookingsborder2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 90));

        color3.setBackground(new java.awt.Color(255, 255, 51));
        color3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        totalbookingspanel2.add(color3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 40, 40));

        totalbookingsnum2.setFont(new java.awt.Font("Bahnschrift", 0, 24)); // NOI18N
        totalbookingsnum2.setForeground(new java.awt.Color(255, 255, 255));
        totalbookingsnum2.setText("0");
        totalbookingspanel2.add(totalbookingsnum2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 20, -1));

        totalbookings6.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        totalbookings6.setForeground(new java.awt.Color(255, 255, 255));
        totalbookings6.setText("Pending");
        totalbookingspanel2.add(totalbookings6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 80, 30));

        jPanel4.add(totalbookingspanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 70, 120, 80));

        totalbookingspanel1.setBackground(new java.awt.Color(29, 45, 61));
        totalbookingspanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        totalbookingspanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        totalbookingsborder1.setBackground(new java.awt.Color(255, 51, 51));
        totalbookingsborder1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        totalbookingspanel1.add(totalbookingsborder1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 90));

        color2.setBackground(new java.awt.Color(255, 51, 51));
        color2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        totalbookingspanel1.add(color2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 40, 40));

        totalbookingsnum1.setFont(new java.awt.Font("Bahnschrift", 0, 24)); // NOI18N
        totalbookingsnum1.setForeground(new java.awt.Color(255, 255, 255));
        totalbookingsnum1.setText("0");
        totalbookingspanel1.add(totalbookingsnum1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 20, -1));

        totalbookings2.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        totalbookings2.setForeground(new java.awt.Color(255, 255, 255));
        totalbookings2.setText("Services");
        totalbookingspanel1.add(totalbookings2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 80, 30));

        jPanel4.add(totalbookingspanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 70, 120, 80));

        totalbookingspanel.setBackground(new java.awt.Color(29, 45, 61));
        totalbookingspanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        totalbookingspanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        totalbookingsborder.setBackground(new java.awt.Color(0, 102, 255));
        totalbookingsborder.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        totalbookingspanel.add(totalbookingsborder, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 90));

        color1.setBackground(new java.awt.Color(0, 102, 255));
        color1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        totalbookingspanel.add(color1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 40, 40));

        totalbookingsnum.setFont(new java.awt.Font("Bahnschrift", 0, 24)); // NOI18N
        totalbookingsnum.setForeground(new java.awt.Color(255, 255, 255));
        totalbookingsnum.setText("0");
        totalbookingspanel.add(totalbookingsnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 20, -1));

        totalbookings1.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        totalbookings1.setForeground(new java.awt.Color(255, 255, 255));
        totalbookings1.setText("Total Users");
        totalbookingspanel.add(totalbookings1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 80, 30));

        jPanel4.add(totalbookingspanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 120, 80));

        totalbookings3.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        totalbookings3.setForeground(new java.awt.Color(255, 255, 255));
        totalbookings3.setText("Employee Status");
        jPanel4.add(totalbookings3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 150, 110, 30));

        jScrollPane3.setBackground(new java.awt.Color(29, 45, 61));
        jScrollPane3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane3.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane3.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N

        TableRecentBookings.setBackground(new java.awt.Color(29, 45, 61));
        TableRecentBookings.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        TableRecentBookings.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        TableRecentBookings.setForeground(new java.awt.Color(255, 255, 255));
        TableRecentBookings.setModel(new javax.swing.table.DefaultTableModel(
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
        TableRecentBookings.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane3.setViewportView(TableRecentBookings);

        jPanel4.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 180, 340, 290));

        jScrollPane4.setBackground(new java.awt.Color(29, 45, 61));
        jScrollPane4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane4.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane4.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N

        TableRecentBookings1.setBackground(new java.awt.Color(29, 45, 61));
        TableRecentBookings1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        TableRecentBookings1.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        TableRecentBookings1.setForeground(new java.awt.Color(255, 255, 255));
        TableRecentBookings1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Name", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        TableRecentBookings1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane4.setViewportView(TableRecentBookings1);

        jPanel4.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 180, 260, 290));

        totalbookings7.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        totalbookings7.setForeground(new java.awt.Color(255, 255, 255));
        totalbookings7.setText("Recent Bookings");
        jPanel4.add(totalbookings7, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 150, 110, 30));

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

        jFrame1.getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 490));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel5.setBackground(new java.awt.Color(28, 69, 91));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(29, 45, 61));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lg1.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        lg1.setForeground(new java.awt.Color(239, 234, 234));
        lg1.setText("Log Out");
        jPanel3.add(lg1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, -1, 20));

        exit1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout (2).png"))); // NOI18N
        exit1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exit1MouseClicked(evt);
            }
        });
        jPanel3.add(exit1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, 30, 20));

        Feedbackheader.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        Feedbackheader.setForeground(new java.awt.Color(239, 234, 234));
        Feedbackheader.setText("Feedback");
        jPanel3.add(Feedbackheader, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 190, 50));

        jPanel5.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 630, 50));

        jPanel6.setBackground(new java.awt.Color(29, 45, 61));
        jPanel5.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 150, 140));

        border4.setBackground(new java.awt.Color(153, 255, 255));
        border4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        border4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                border4MouseClicked(evt);
            }
        });
        border4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel5.add(border4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 630, 10));

        border5.setBackground(new java.awt.Color(153, 255, 255));
        border5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                border5MouseClicked(evt);
            }
        });
        border5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel5.add(border5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 490));

        verybadpanel.setBackground(new java.awt.Color(29, 45, 61));
        verybadpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verybadpanelMouseClicked(evt);
            }
        });

        verybadbutton.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        verybadbutton.setForeground(new java.awt.Color(239, 234, 234));
        verybadbutton.setText("VERY BAD");
        verybadpanel.add(verybadbutton);

        jPanel5.add(verybadpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 100, 60, 20));

        badpanel.setBackground(new java.awt.Color(29, 45, 61));
        badpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                badpanelMouseClicked(evt);
            }
        });

        badbutton.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        badbutton.setForeground(new java.awt.Color(239, 234, 234));
        badbutton.setText("BAD");
        badpanel.add(badbutton);

        jPanel5.add(badpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 100, 60, 20));

        goodpanel.setBackground(new java.awt.Color(29, 45, 61));
        goodpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                goodpanelMouseClicked(evt);
            }
        });

        goodbutton.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        goodbutton.setForeground(new java.awt.Color(239, 234, 234));
        goodbutton.setText("GOOD");
        goodpanel.add(goodbutton);

        jPanel5.add(goodpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 100, 60, 20));

        verygoodpanel.setBackground(new java.awt.Color(29, 45, 61));
        verygoodpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verygoodpanelMouseClicked(evt);
            }
        });

        verygoodbutton.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        verygoodbutton.setForeground(new java.awt.Color(239, 234, 234));
        verygoodbutton.setText("VERY GOOD");
        verygoodpanel.add(verygoodbutton);

        jPanel5.add(verygoodpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 100, 70, 20));

        black.setBackground(new java.awt.Color(29, 45, 61));
        black.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        black.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                blackMouseClicked(evt);
            }
        });
        black.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        totalnum.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        totalnum.setForeground(new java.awt.Color(239, 234, 234));
        totalnum.setText("0");
        black.add(totalnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 30, 30));

        total.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        total.setForeground(new java.awt.Color(239, 234, 234));
        total.setText("Total");
        black.add(total, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 5, -1, 20));

        jPanel5.add(black, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 70, 80, 30));

        red.setBackground(new java.awt.Color(153, 0, 51));
        red.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        red.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        verybadnum.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        verybadnum.setForeground(new java.awt.Color(255, 102, 102));
        verybadnum.setText("0");
        red.add(verybadnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 20, 30));

        verybad.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        verybad.setForeground(new java.awt.Color(255, 102, 102));
        verybad.setText("VB");
        red.add(verybad, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 50, 30));

        jPanel5.add(red, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, 90, 30));

        yellow.setBackground(new java.awt.Color(153, 153, 0));
        yellow.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        yellow.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        badnum.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        badnum.setForeground(new java.awt.Color(255, 204, 102));
        badnum.setText("0");
        yellow.add(badnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 30, 30));

        bad.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        bad.setForeground(new java.awt.Color(255, 204, 102));
        bad.setText("Bad");
        yellow.add(bad, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 50, 30));

        jPanel5.add(yellow, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 90, 30));

        skyblue.setBackground(new java.awt.Color(0, 153, 153));
        skyblue.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        skyblue.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        verygoodnum.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        verygoodnum.setForeground(new java.awt.Color(0, 255, 255));
        verygoodnum.setText("0");
        skyblue.add(verygoodnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 30, 30));

        verygood.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        verygood.setForeground(new java.awt.Color(0, 255, 255));
        verygood.setText("VG/G");
        skyblue.add(verygood, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 50, 30));

        jPanel5.add(skyblue, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 90, 30));

        searchfield.setFont(new java.awt.Font("Bahnschrift", 0, 11)); // NOI18N
        searchfield.setText("Search ");
        searchfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchfieldActionPerformed(evt);
            }
        });
        jPanel5.add(searchfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 100, 320, 20));

        feedbackscrollpane.setBackground(new java.awt.Color(29, 45, 61));
        feedbackscrollpane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        feedbackscrollpane.setForeground(new java.awt.Color(255, 255, 255));

        TableFeedback.setBackground(new java.awt.Color(29, 45, 61));
        TableFeedback.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        TableFeedback.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Booking ID", "Customer", "Cleaner", "Service", "Rating", "Comment"
            }
        ));
        feedbackscrollpane.setViewportView(TableFeedback);

        jPanel5.add(feedbackscrollpane, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 120, 610, 350));

        listadmin2.setBackground(new java.awt.Color(55, 86, 93));
        listadmin2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        settings2.setBackground(new java.awt.Color(255, 255, 255));
        settings2.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        settings2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user (1).png"))); // NOI18N
        settings2.setText("ACCOUNT");
        settings2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                settings2MouseClicked(evt);
            }
        });
        listadmin2.add(settings2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-220, 10, 280, 50));

        lblUsername3.setBackground(new java.awt.Color(153, 255, 255));
        lblUsername3.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        lblUsername3.setText("Admin");
        listadmin2.add(lblUsername3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 90, 20));

        dashpanel2.setBackground(new java.awt.Color(29, 45, 61));
        dashpanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashpanel2MouseClicked(evt);
            }
        });
        dashpanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dashboard3.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        dashboard3.setForeground(new java.awt.Color(239, 234, 234));
        dashboard3.setText("Dashboard");
        dashpanel2.add(dashboard3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 70, 20));

        listadmin2.add(dashpanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 150, 40));

        userpanel2.setBackground(new java.awt.Color(29, 45, 61));
        userpanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userpanel2MouseClicked(evt);
            }
        });
        userpanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        users2.setBackground(new java.awt.Color(29, 45, 61));
        users2.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        users2.setForeground(new java.awt.Color(239, 234, 234));
        users2.setText("Users");
        userpanel2.add(users2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 70, -1));

        listadmin2.add(userpanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 150, 40));

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

        listadmin2.add(employeepanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 150, 40));

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

        listadmin2.add(servicespanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, -1, 40));

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

        listadmin2.add(bookingspanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 150, 40));

        reportspanel2.setBackground(new java.awt.Color(29, 45, 61));
        reportspanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reportspanel2MouseClicked(evt);
            }
        });
        reportspanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        reports2.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        reports2.setForeground(new java.awt.Color(239, 234, 234));
        reports2.setText("Reports");
        reportspanel2.add(reports2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, -1));

        listadmin2.add(reportspanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 150, 40));

        feedbackpanel.setBackground(new java.awt.Color(29, 45, 61));
        feedbackpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                feedbackpanelMouseClicked(evt);
            }
        });
        feedbackpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        review2.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        feedbackpanel.add(review2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 90, -1));

        feedback.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        feedback.setForeground(new java.awt.Color(239, 234, 234));
        feedback.setText("Feedback");
        feedbackpanel.add(feedback, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 80, -1));

        listadmin2.add(feedbackpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 150, 40));

        administrator.setBackground(new java.awt.Color(153, 255, 255));
        administrator.setFont(new java.awt.Font("Century Gothic", 1, 10)); // NOI18N
        administrator.setForeground(new java.awt.Color(153, 255, 255));
        administrator.setText("ADMINISTRATOR");
        listadmin2.add(administrator, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 90, 20));

        jPanel5.add(listadmin2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 150, -1));

        reporttypecombobox.setBackground(new java.awt.Color(29, 45, 61));
        reporttypecombobox.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        reporttypecombobox.setForeground(new java.awt.Color(29, 45, 61));
        reporttypecombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        reporttypecombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reporttypecomboboxActionPerformed(evt);
            }
        });
        jPanel5.add(reporttypecombobox, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 80, 100, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 790, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 790, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 490, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void settingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsMouseClicked
       
    }//GEN-LAST:event_settingsMouseClicked

    private void userpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userpanelMouseClicked
        // Open List of Users
        Users lou = new Users();
        lou.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_userpanelMouseClicked

    private void servicepanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_servicepanelMouseClicked
        Services s = new Services();
        s.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_servicepanelMouseClicked

    private void exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMouseClicked
      
        // TODO add your handling code here:
    }//GEN-LAST:event_exitMouseClicked

    private void border2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_border2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_border2MouseClicked

    private void border3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_border3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_border3MouseClicked

    private void exit1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exit1MouseClicked
       int c = JOptionPane.showConfirmDialog(this, "Log out?", "Log Out", JOptionPane.YES_NO_OPTION);
    if (c == JOptionPane.YES_OPTION) { Session.getInstance().logout(); new LoginForm().setVisible(true); this.dispose(); }

    }//GEN-LAST:event_exit1MouseClicked

    private void border4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_border4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_border4MouseClicked

    private void border5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_border5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_border5MouseClicked

    private void verybadpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verybadpanelMouseClicked
      loadFeedback("Very Bad");
    }//GEN-LAST:event_verybadpanelMouseClicked

    private void badpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_badpanelMouseClicked
        loadFeedback("Bad");
    }//GEN-LAST:event_badpanelMouseClicked

    private void goodpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_goodpanelMouseClicked
      loadFeedback("Good");
    }//GEN-LAST:event_goodpanelMouseClicked

    private void verygoodpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verygoodpanelMouseClicked
        loadFeedback("Very Good");
    }//GEN-LAST:event_verygoodpanelMouseClicked

    private void blackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_blackMouseClicked
        loadFeedback(null); // show all
    }//GEN-LAST:event_blackMouseClicked

    private void searchfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchfieldActionPerformed
        String kw = searchfield.getText().trim();
        if (kw.isEmpty() || kw.equals("Search ")) { loadFeedback(null); return; }
        new config().displayData(
            "SELECT f_id AS 'ID', f_employee AS 'Employee', f_rating AS 'Rating', " +
            "f_comment AS 'Comment', f_date AS 'Date' FROM tbl_feedback " +
            "WHERE f_employee LIKE '%" + kw + "%' OR f_rating LIKE '%" + kw + "%' " +
            "OR f_comment LIKE '%" + kw + "%' ORDER BY f_id DESC",
            TableFeedback);
    }//GEN-LAST:event_searchfieldActionPerformed

    private void settings2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settings2MouseClicked
        Profile a = new Profile();
        a.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_settings2MouseClicked

    private void dashpanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashpanel2MouseClicked
        AdminDash a = new AdminDash();
        a.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_dashpanel2MouseClicked

    private void userpanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userpanel2MouseClicked
        // Open List of Users
        Users lou = new Users();
        lou.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_userpanel2MouseClicked

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

    private void reportspanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportspanel2MouseClicked
        Reports r = new Reports();
        r.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_reportspanel2MouseClicked

    private void feedbackpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_feedbackpanelMouseClicked
        Feedback f = new Feedback();
        f.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_feedbackpanelMouseClicked

    private void reporttypecomboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reporttypecomboboxActionPerformed
        // TODO add your handling code here:
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
                new AdminDash().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Feedbackheader;
    private javax.swing.JTable TableFeedback;
    private javax.swing.JTable TableRecentBookings;
    private javax.swing.JTable TableRecentBookings1;
    private javax.swing.JLabel admindashboard;
    private javax.swing.JLabel administrator;
    private javax.swing.JLabel bad;
    private javax.swing.JLabel badbutton;
    private javax.swing.JLabel badnum;
    private javax.swing.JPanel badpanel;
    private javax.swing.JPanel black;
    private javax.swing.JLabel booking;
    private javax.swing.JPanel bookingpanel;
    private javax.swing.JLabel bookings;
    private javax.swing.JPanel bookingspanel;
    private javax.swing.JPanel border2;
    private javax.swing.JPanel border3;
    private javax.swing.JPanel border4;
    private javax.swing.JPanel border5;
    private javax.swing.JPanel color1;
    private javax.swing.JPanel color2;
    private javax.swing.JPanel color3;
    private javax.swing.JPanel color4;
    private javax.swing.JPanel color5;
    private javax.swing.JLabel dashboard1;
    private javax.swing.JLabel dashboard3;
    private javax.swing.JPanel dashpanel;
    private javax.swing.JPanel dashpanel2;
    private javax.swing.JLabel employee;
    private javax.swing.JPanel employeepanel;
    private javax.swing.JLabel exit;
    private javax.swing.JLabel exit1;
    private javax.swing.JLabel feedback;
    private javax.swing.JPanel feedbackpanel;
    private javax.swing.JScrollPane feedbackscrollpane;
    private javax.swing.JLabel goodbutton;
    private javax.swing.JPanel goodpanel;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lblUsername1;
    private javax.swing.JLabel lblUsername3;
    private javax.swing.JLabel lg;
    private javax.swing.JLabel lg1;
    private javax.swing.JPanel listadmin;
    private javax.swing.JPanel listadmin2;
    private javax.swing.JLabel memployee;
    private javax.swing.JPanel memployeepanel;
    private javax.swing.JLabel mstaff;
    private javax.swing.JPanel mstaffpanel;
    private javax.swing.JPanel red;
    private javax.swing.JLabel reports;
    private javax.swing.JLabel reports2;
    private javax.swing.JPanel reportspanel;
    private javax.swing.JPanel reportspanel2;
    private javax.swing.JComboBox<String> reporttypecombobox;
    private javax.swing.JLabel review;
    private javax.swing.JLabel review2;
    private javax.swing.JTextField searchfield;
    private javax.swing.JLabel service;
    private javax.swing.JPanel servicepanel;
    private javax.swing.JLabel services;
    private javax.swing.JPanel servicespanel;
    private javax.swing.JLabel settings;
    private javax.swing.JLabel settings2;
    private javax.swing.JPanel skyblue;
    private javax.swing.JLabel total;
    private javax.swing.JLabel totalbookings1;
    private javax.swing.JLabel totalbookings2;
    private javax.swing.JLabel totalbookings3;
    private javax.swing.JLabel totalbookings4;
    private javax.swing.JLabel totalbookings5;
    private javax.swing.JLabel totalbookings6;
    private javax.swing.JLabel totalbookings7;
    private javax.swing.JPanel totalbookingsborder;
    private javax.swing.JPanel totalbookingsborder1;
    private javax.swing.JPanel totalbookingsborder2;
    private javax.swing.JPanel totalbookingsborder3;
    private javax.swing.JPanel totalbookingsborder4;
    private javax.swing.JLabel totalbookingsnum;
    private javax.swing.JLabel totalbookingsnum1;
    private javax.swing.JLabel totalbookingsnum2;
    private javax.swing.JLabel totalbookingsnum3;
    private javax.swing.JLabel totalbookingsnum4;
    private javax.swing.JPanel totalbookingspanel;
    private javax.swing.JPanel totalbookingspanel1;
    private javax.swing.JPanel totalbookingspanel2;
    private javax.swing.JPanel totalbookingspanel3;
    private javax.swing.JPanel totalbookingspanel4;
    private javax.swing.JLabel totalnum;
    private javax.swing.JPanel userpanel;
    private javax.swing.JPanel userpanel2;
    private javax.swing.JLabel users;
    private javax.swing.JLabel users2;
    private javax.swing.JLabel verybad;
    private javax.swing.JLabel verybadbutton;
    private javax.swing.JLabel verybadnum;
    private javax.swing.JPanel verybadpanel;
    private javax.swing.JLabel verygood;
    private javax.swing.JLabel verygoodbutton;
    private javax.swing.JLabel verygoodnum;
    private javax.swing.JPanel verygoodpanel;
    private javax.swing.JPanel yellow;
    // End of variables declaration//GEN-END:variables
}
