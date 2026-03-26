/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import AdminInternalPage.Profile;
import LoginandRegister.LoginForm;
import Session.Session;
import config.config;
import javax.swing.JOptionPane;

/**
 *
 * @author ashlaran
 */
public class Feedback extends javax.swing.JFrame {

    private final config conf = new config();
 
    public Feedback() {
        initComponents();
        if (Session.getInstance().isLoggedIn()) {
            lblUsername.setText(Session.getInstance().getUsername());
        }
        loadFeedbackCounts();
        loadFeedback("All");
        wireSearch();
    }
 
    // ── Load summary counts ───────────────────────────────────────────────────
    private void loadFeedbackCounts() {
        // VG / G combined → sky blue panel
        Object vg = conf.getValue("SELECT COUNT(*) FROM tbl_feedback WHERE f_rating IN ('Very Good','Good')");
        verygoodnum.setText(vg != null ? vg.toString() : "0");
        // Bad → yellow panel
        Object bad = conf.getValue("SELECT COUNT(*) FROM tbl_feedback WHERE f_rating = 'Bad'");
        badnum.setText(bad != null ? bad.toString() : "0");
        // Very Bad → red panel
        Object vb = conf.getValue("SELECT COUNT(*) FROM tbl_feedback WHERE f_rating = 'Very Bad'");
        verybadnum.setText(vb != null ? vb.toString() : "0");
        // Total
        Object total = conf.getValue("SELECT COUNT(*) FROM tbl_feedback");
        totalnum.setText(total != null ? total.toString() : "0");
    }
 
    // ── Load feedback table ───────────────────────────────────────────────────
    // Only shows ratings that staff have already submitted.
    // INNER JOIN ensures every row has a real booking → real customer name.
    private void loadFeedback(String ratingFilter) {
        String baseSql =
            "SELECT f.f_id AS 'ID', b.b_customer AS 'Customer', " +
            "f.f_employee AS 'Employee', f.f_rating AS 'Rating', " +
            "f.f_comment AS 'Comment', f.f_date AS 'Date' " +
            "FROM tbl_feedback f " +
            "INNER JOIN tbl_bookings b ON f.f_booking_id = b.b_id ";
        if ("All".equals(ratingFilter)) {
            conf.displayData(baseSql + "ORDER BY f.f_id DESC", TableFeedback);
        } else if ("VG/G".equals(ratingFilter)) {
            conf.displayData(baseSql +
                "WHERE f.f_rating IN ('Very Good','Good') ORDER BY f.f_id DESC", TableFeedback);
        } else {
            conf.displayData(baseSql +
                "WHERE f.f_rating = ? ORDER BY f.f_id DESC", TableFeedback, ratingFilter);
        }
    }

    private void wireSearch() {
        searchfield.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override public void keyReleased(java.awt.event.KeyEvent e) {
                String kw = searchfield.getText().trim();
                if (kw.isEmpty() || kw.equals("Search ")) { loadFeedback("All"); return; }
                String k = kw.replace("'", "''");
                conf.displayData(
                    "SELECT f.f_id AS 'ID', b.b_customer AS 'Customer', " +
                    "f.f_employee AS 'Employee', f.f_rating AS 'Rating', " +
                    "f.f_comment AS 'Comment', f.f_date AS 'Date' " +
                    "FROM tbl_feedback f " +
                    "INNER JOIN tbl_bookings b ON f.f_booking_id = b.b_id " +
                    "WHERE f.f_employee LIKE ? OR f.f_comment LIKE ? " +
                    "OR b.b_customer LIKE ? ORDER BY f.f_id DESC",
                    TableFeedback,
                    "%" + kw + "%", "%" + kw + "%", "%" + kw + "%");
            }
        });
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lg1 = new javax.swing.JLabel();
        Feedbackheader = new javax.swing.JLabel();
        exit = new javax.swing.JLabel();
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

        jPanel5.setBackground(new java.awt.Color(28, 69, 91));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(29, 45, 61));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lg1.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        lg1.setForeground(new java.awt.Color(239, 234, 234));
        lg1.setText("Log Out");
        jPanel3.add(lg1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, -1, 20));

        Feedbackheader.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        Feedbackheader.setForeground(new java.awt.Color(239, 234, 234));
        Feedbackheader.setText("Feedback");
        jPanel3.add(Feedbackheader, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 190, 50));

        exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout (2).png"))); // NOI18N
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitMouseClicked(evt);
            }
        });
        jPanel3.add(exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, 30, 20));

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
        TableFeedback.setForeground(new java.awt.Color(255, 255, 255));
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

        jPanel5.add(listadmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 150, -1));

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
        loadFeedback("All"); 
    }//GEN-LAST:event_blackMouseClicked

    private void searchfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchfieldActionPerformed

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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
       Session session = Session.getInstance();
        if (!session.isLoggedIn()) {
            JOptionPane.showMessageDialog(null, "Please login first!", "Unauthorized Access", JOptionPane.WARNING_MESSAGE);
            java.awt.EventQueue.invokeLater(() -> new LoginForm().setVisible(true)); return;
        }
        java.awt.EventQueue.invokeLater(() -> new AdminDash().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Feedbackheader;
    private javax.swing.JTable TableFeedback;
    private javax.swing.JLabel administrator;
    private javax.swing.JLabel bad;
    private javax.swing.JLabel badbutton;
    private javax.swing.JLabel badnum;
    private javax.swing.JPanel badpanel;
    private javax.swing.JPanel black;
    private javax.swing.JLabel bookings;
    private javax.swing.JPanel bookingspanel;
    private javax.swing.JPanel border4;
    private javax.swing.JPanel border5;
    private javax.swing.JLabel dashboard1;
    private javax.swing.JPanel dashpanel;
    private javax.swing.JLabel employee;
    private javax.swing.JPanel employeepanel;
    private javax.swing.JLabel exit;
    private javax.swing.JLabel feedback;
    private javax.swing.JPanel feedbackpanel;
    private javax.swing.JScrollPane feedbackscrollpane;
    private javax.swing.JLabel goodbutton;
    private javax.swing.JPanel goodpanel;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lg1;
    private javax.swing.JPanel listadmin;
    private javax.swing.JPanel red;
    private javax.swing.JLabel reports;
    private javax.swing.JPanel reportspanel;
    private javax.swing.JLabel review;
    private javax.swing.JTextField searchfield;
    private javax.swing.JLabel services;
    private javax.swing.JPanel servicespanel;
    private javax.swing.JLabel settings;
    private javax.swing.JPanel skyblue;
    private javax.swing.JLabel total;
    private javax.swing.JLabel totalnum;
    private javax.swing.JPanel userpanel;
    private javax.swing.JLabel users;
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
