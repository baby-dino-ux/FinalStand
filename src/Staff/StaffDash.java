/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Staff;

import AdminInternalPage.Profile;
import LoginandRegister.LoginForm;
import Session.Session;
import javax.swing.JOptionPane;
import config.config;

/**
 *
 * @author ashlaran
 */
public class StaffDash extends javax.swing.JFrame {
 private String loggedInUsername;
 
    public StaffDash() {
        initComponents();
        this.loggedInUsername = "Staff";
        loadSessionInfo();
        loadDashboard();
        setupCombobox();
    }
 
    public StaffDash(String username) {
        initComponents();
        this.loggedInUsername = username;
        loadSessionInfo();
        loadDashboard();
        setupCombobox();
    }
 
    private void setupCombobox() {
        reporttypecombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{
            "All My Bookings",
            "Pending Only",
            "In Progress Only",
            "Done Only",
            "Cancelled Only"
        }));
        reporttypecombobox.addActionListener(e -> filterRecentBookings());
    }
 
    private void loadSessionInfo() {
        Session session = Session.getInstance();
        if (session.isLoggedIn()) {
            // FIX: show username (e.g. "john_staff"), not full name
            lblUsername.setText(session.getUsername() + "!");
        } else {
            lblUsername.setText("WELCOME!");
        }
    }
 
    private void loadDashboard() {
        config conf = new config();
        Session session = Session.getInstance();
        String staffName = session.getFullName();    // for receipt display
        // FIX: b_staff now stores username — filter by username for reliable matching
        String staffUser = session.getUsername();
        // Keep NULL fallback ONLY for StaffDash so old pre-migration bookings still show
        String sf = "(LOWER(TRIM(b_staff)) = LOWER(TRIM(?)) OR b_staff IS NULL OR TRIM(b_staff) = '')";

        Object total = conf.getValue("SELECT COUNT(*) FROM tbl_bookings WHERE " + sf, staffUser);
        totalbookingsnum.setText(total != null ? total.toString() : "0");

        Object pending = conf.getValue("SELECT COUNT(*) FROM tbl_bookings WHERE " + sf + " AND b_status = 'Pending'", staffUser);
        pendingnum.setText(pending != null ? pending.toString() : "0");

        Object completed = conf.getValue("SELECT COUNT(*) FROM tbl_bookings WHERE " + sf + " AND b_status = 'Done'", staffUser);
        completednum.setText(completed != null ? completed.toString() : "0");

        Object cancelled = conf.getValue("SELECT COUNT(*) FROM tbl_bookings WHERE " + sf + " AND b_status = 'Cancelled'", staffUser);
        cancellednum.setText(cancelled != null ? cancelled.toString() : "0");

        conf.displayData(
            "SELECT b_id AS 'ID', b_customer AS 'Customer', b_service AS 'Service', " +
            "b_employee AS 'Employee', b_date AS 'Date', b_status AS 'Status' " +
            "FROM tbl_bookings WHERE " + sf + " ORDER BY b_id DESC LIMIT 8",
            TableRecentBookings, staffUser);

        Object weekBookings = conf.getValue(
            "SELECT COUNT(*) FROM tbl_bookings WHERE " + sf + " AND b_date >= date('now', '-7 days')", staffUser);
        newbookingsweeknum.setText(weekBookings != null ? weekBookings.toString() : "0");

        Object weekRevenue = conf.getValue(
            "SELECT COALESCE(SUM(b_price), 0) FROM tbl_bookings WHERE " + sf +
            " AND b_status = 'Done' AND b_date >= date('now', '-7 days')", staffUser);
        revenueweeknum.setText("₱" + (weekRevenue != null ? weekRevenue.toString() : "0"));
    }
 
    private void filterRecentBookings() {
        String selected  = reporttypecombobox.getSelectedItem() != null
                           ? reporttypecombobox.getSelectedItem().toString() : "All My Bookings";
        // FIX: use username for b_staff matching
        String staffUser = Session.getInstance().getUsername();

        String statusClause;
        switch (selected) {
            case "Pending Only":     statusClause = " AND b_status = 'Pending'"; break;
            case "In Progress Only": statusClause = " AND b_status = 'In Progress'"; break;
            case "Done Only":        statusClause = " AND b_status = 'Done'"; break;
            case "Cancelled Only":   statusClause = " AND b_status = 'Cancelled'"; break;
            default:                 statusClause = ""; break;
        }

        // Keep NULL fallback for StaffDash (pre-migration old data)
        String sql = "SELECT b_id AS 'ID', b_customer AS 'Customer', b_service AS 'Service', " +
                     "b_employee AS 'Employee', b_date AS 'Date', b_status AS 'Status' " +
                     "FROM tbl_bookings WHERE (LOWER(TRIM(b_staff)) = LOWER(TRIM(?)) OR b_staff IS NULL OR TRIM(b_staff) = '')" +
                     statusClause + " ORDER BY b_id DESC LIMIT 8";
        new config().displayData(sql, TableRecentBookings, staffUser);
    }
 

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        panelbottom = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lg = new javax.swing.JLabel();
        exit = new javax.swing.JLabel();
        dashboardheader = new javax.swing.JLabel();
        border1 = new javax.swing.JPanel();
        border2 = new javax.swing.JPanel();
        totalbookingspanel = new javax.swing.JPanel();
        green1 = new javax.swing.JPanel();
        green = new javax.swing.JPanel();
        totalbookingsnum = new javax.swing.JLabel();
        totalbookings1 = new javax.swing.JLabel();
        pendingpanel = new javax.swing.JPanel();
        pending = new javax.swing.JLabel();
        yellow1 = new javax.swing.JPanel();
        yellow = new javax.swing.JPanel();
        pendingnum = new javax.swing.JLabel();
        completedpanel = new javax.swing.JPanel();
        completed = new javax.swing.JLabel();
        skyblue1 = new javax.swing.JPanel();
        skyblue = new javax.swing.JPanel();
        completednum = new javax.swing.JLabel();
        cancelledpanel = new javax.swing.JPanel();
        red1 = new javax.swing.JPanel();
        red = new javax.swing.JPanel();
        cancelled = new javax.swing.JLabel();
        cancellednum = new javax.swing.JLabel();
        recentbookingscrollpane = new javax.swing.JScrollPane();
        TableRecentBookings = new javax.swing.JTable();
        quickactionsborder = new javax.swing.JPanel();
        newbookingpanel = new javax.swing.JPanel();
        newbooking = new javax.swing.JLabel();
        viewallborder = new javax.swing.JPanel();
        viewall = new javax.swing.JLabel();
        quickactions = new javax.swing.JLabel();
        thisweekpanel = new javax.swing.JPanel();
        avgrating = new javax.swing.JLabel();
        rating = new javax.swing.JLabel();
        newbookingsweek = new javax.swing.JLabel();
        revenueweek = new javax.swing.JLabel();
        newbookingsweeknum = new javax.swing.JLabel();
        revenueweeknum = new javax.swing.JLabel();
        Thisweek = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        recentbookings = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        reporttypecombobox = new javax.swing.JComboBox<>();
        border = new javax.swing.JPanel();
        listadmin = new javax.swing.JPanel();
        settings = new javax.swing.JLabel();
        dashpanel = new javax.swing.JPanel();
        dashboard = new javax.swing.JLabel();
        availableemployeerpanel = new javax.swing.JPanel();
        availableemployee = new javax.swing.JLabel();
        createbookingpanel3 = new javax.swing.JPanel();
        createbooking3 = new javax.swing.JLabel();
        viewservicespanel = new javax.swing.JPanel();
        viewservices = new javax.swing.JLabel();
        createbookingpanel = new javax.swing.JPanel();
        createbooking = new javax.swing.JLabel();
        servicepanel = new javax.swing.JPanel();
        lblUsername = new javax.swing.JLabel();
        staff = new javax.swing.JLabel();
        mybookingspanel = new javax.swing.JPanel();
        mybookings = new javax.swing.JLabel();
        createbookingpanel5 = new javax.swing.JPanel();
        createbooking5 = new javax.swing.JLabel();
        feedbackpanel = new javax.swing.JPanel();
        feedback = new javax.swing.JLabel();
        createbookingpanel6 = new javax.swing.JPanel();
        createbooking6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(28, 69, 91));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelbottom.setBackground(new java.awt.Color(29, 45, 61));
        panelbottom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelbottomMouseClicked(evt);
            }
        });
        panelbottom.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(panelbottom, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, -1, 290));

        jPanel1.setBackground(new java.awt.Color(29, 45, 61));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lg.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        lg.setForeground(new java.awt.Color(239, 234, 234));
        lg.setText("Log Out");
        jPanel1.add(lg, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 10, -1, 20));

        exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout (2).png"))); // NOI18N
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitMouseClicked(evt);
            }
        });
        jPanel1.add(exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 10, 30, 20));

        dashboardheader.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        dashboardheader.setForeground(new java.awt.Color(255, 255, 255));
        dashboardheader.setText("Dashboard");
        jPanel1.add(dashboardheader, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 130, 50));

        jPanel4.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 0, 650, 50));

        border1.setBackground(new java.awt.Color(29, 45, 61));
        border1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                border1MouseClicked(evt);
            }
        });
        border1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(border1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 130, 180));

        border2.setBackground(new java.awt.Color(153, 255, 255));
        border2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        border2.setToolTipText("");
        border2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                border2MouseClicked(evt);
            }
        });
        border2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(border2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 650, 10));

        totalbookingspanel.setBackground(new java.awt.Color(29, 45, 61));
        totalbookingspanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        totalbookingspanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        green1.setBackground(new java.awt.Color(0, 204, 0));
        green1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        totalbookingspanel.add(green1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 90));

        green.setBackground(new java.awt.Color(0, 204, 0));
        green.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        totalbookingspanel.add(green, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 50, 40));

        totalbookingsnum.setFont(new java.awt.Font("Bahnschrift", 0, 24)); // NOI18N
        totalbookingsnum.setForeground(new java.awt.Color(255, 255, 255));
        totalbookingsnum.setText("0");
        totalbookingspanel.add(totalbookingsnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 60, -1));

        totalbookings1.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        totalbookings1.setForeground(new java.awt.Color(255, 255, 255));
        totalbookings1.setText("Total Bookings");
        totalbookingspanel.add(totalbookings1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 80, 30));

        jPanel4.add(totalbookingspanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 140, 90));

        pendingpanel.setBackground(new java.awt.Color(29, 45, 61));
        pendingpanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        pendingpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pending.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        pending.setForeground(new java.awt.Color(255, 255, 255));
        pending.setText("Pending");
        pendingpanel.add(pending, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 50, 30));

        yellow1.setBackground(new java.awt.Color(255, 255, 51));
        yellow1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        pendingpanel.add(yellow1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 90));

        yellow.setBackground(new java.awt.Color(255, 255, 51));
        yellow.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pendingpanel.add(yellow, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 50, 40));

        pendingnum.setFont(new java.awt.Font("Bahnschrift", 0, 24)); // NOI18N
        pendingnum.setForeground(new java.awt.Color(255, 255, 255));
        pendingnum.setText("0");
        pendingpanel.add(pendingnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 60, -1));

        jPanel4.add(pendingpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 70, 140, 90));

        completedpanel.setBackground(new java.awt.Color(29, 45, 61));
        completedpanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        completedpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        completed.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        completed.setForeground(new java.awt.Color(255, 255, 255));
        completed.setText("Completed");
        completedpanel.add(completed, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 80, 30));

        skyblue1.setBackground(new java.awt.Color(153, 255, 255));
        skyblue1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        completedpanel.add(skyblue1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 90));

        skyblue.setBackground(new java.awt.Color(153, 255, 255));
        skyblue.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        completedpanel.add(skyblue, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 50, 40));

        completednum.setFont(new java.awt.Font("Bahnschrift", 0, 24)); // NOI18N
        completednum.setForeground(new java.awt.Color(255, 255, 255));
        completednum.setText("0");
        completedpanel.add(completednum, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 60, 30));

        jPanel4.add(completedpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 70, 140, 90));

        cancelledpanel.setBackground(new java.awt.Color(29, 45, 61));
        cancelledpanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        cancelledpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        red1.setBackground(new java.awt.Color(255, 51, 51));
        red1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        cancelledpanel.add(red1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 90));

        red.setBackground(new java.awt.Color(255, 51, 51));
        red.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cancelledpanel.add(red, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 50, 40));

        cancelled.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        cancelled.setForeground(new java.awt.Color(255, 255, 255));
        cancelled.setText("Cancelled");
        cancelledpanel.add(cancelled, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 80, 30));

        cancellednum.setFont(new java.awt.Font("Bahnschrift", 0, 24)); // NOI18N
        cancellednum.setForeground(new java.awt.Color(255, 255, 255));
        cancellednum.setText("0");
        cancelledpanel.add(cancellednum, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 60, 30));

        jPanel4.add(cancelledpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 70, 140, 90));

        recentbookingscrollpane.setBackground(new java.awt.Color(29, 45, 61));
        recentbookingscrollpane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        recentbookingscrollpane.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N

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
                "Booking ID", "Customer", "Service", "Date ", "Status"
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
        recentbookingscrollpane.setViewportView(TableRecentBookings);

        jPanel4.add(recentbookingscrollpane, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 210, 600, 160));

        quickactionsborder.setBackground(new java.awt.Color(29, 45, 61));
        quickactionsborder.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        quickactionsborder.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        newbookingpanel.setBackground(new java.awt.Color(153, 255, 255));
        newbookingpanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        newbookingpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newbookingpanelMouseClicked(evt);
            }
        });
        newbookingpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        newbooking.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        newbooking.setText("+ New Booking");
        newbookingpanel.add(newbooking, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 100, 30));

        quickactionsborder.add(newbookingpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 110, 30));

        viewallborder.setBackground(new java.awt.Color(28, 69, 91));
        viewallborder.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        viewallborder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewallborderMouseClicked(evt);
            }
        });
        viewallborder.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        viewall.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        viewall.setForeground(new java.awt.Color(255, 255, 255));
        viewall.setText("View All");
        viewallborder.add(viewall, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 60, 30));

        quickactionsborder.add(viewallborder, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 110, 30));

        quickactions.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        quickactions.setForeground(new java.awt.Color(255, 255, 255));
        quickactions.setText("Quick Actions");
        quickactionsborder.add(quickactions, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 100, 30));

        jPanel4.add(quickactionsborder, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 390, 270, 80));

        thisweekpanel.setBackground(new java.awt.Color(29, 45, 61));
        thisweekpanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        thisweekpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        avgrating.setFont(new java.awt.Font("Bahnschrift", 0, 10)); // NOI18N
        avgrating.setForeground(new java.awt.Color(102, 102, 102));
        avgrating.setText("Avg Rating");
        thisweekpanel.add(avgrating, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 50, 30));

        rating.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        rating.setForeground(new java.awt.Color(255, 255, 51));
        rating.setText("9.9");
        thisweekpanel.add(rating, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 40, 30));

        newbookingsweek.setFont(new java.awt.Font("Bahnschrift", 0, 10)); // NOI18N
        newbookingsweek.setForeground(new java.awt.Color(102, 102, 102));
        newbookingsweek.setText("New Bookings");
        thisweekpanel.add(newbookingsweek, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 70, 30));

        revenueweek.setFont(new java.awt.Font("Bahnschrift", 0, 10)); // NOI18N
        revenueweek.setForeground(new java.awt.Color(102, 102, 102));
        revenueweek.setText("Revenue");
        thisweekpanel.add(revenueweek, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 50, 30));

        newbookingsweeknum.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        newbookingsweeknum.setForeground(new java.awt.Color(102, 255, 255));
        newbookingsweeknum.setText("0");
        thisweekpanel.add(newbookingsweeknum, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 40, 30));

        revenueweeknum.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        revenueweeknum.setForeground(new java.awt.Color(102, 255, 255));
        revenueweeknum.setText("0");
        thisweekpanel.add(revenueweeknum, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 80, 30));

        Thisweek.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        Thisweek.setForeground(new java.awt.Color(255, 255, 255));
        Thisweek.setText("This Week");
        thisweekpanel.add(Thisweek, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 100, 30));
        thisweekpanel.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, 0, -1, -1));

        jPanel4.add(thisweekpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 390, 270, 80));

        recentbookings.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        recentbookings.setForeground(new java.awt.Color(255, 255, 255));
        recentbookings.setText("Recent Bookings");
        jPanel4.add(recentbookings, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 170, 170, 30));

        jPanel2.setBackground(new java.awt.Color(29, 45, 61));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        reporttypecombobox.setBackground(new java.awt.Color(29, 45, 61));
        reporttypecombobox.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        reporttypecombobox.setForeground(new java.awt.Color(29, 45, 61));
        reporttypecombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        reporttypecombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reporttypecomboboxActionPerformed(evt);
            }
        });
        jPanel2.add(reporttypecombobox, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, 100, 20));

        jPanel4.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 170, 620, 210));

        border.setBackground(new java.awt.Color(153, 255, 255));
        border.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                borderMouseClicked(evt);
            }
        });
        border.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(border, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 490));

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

        dashpanel.setBackground(new java.awt.Color(29, 45, 61));
        dashpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashpanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                dashpanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                dashpanelMouseExited(evt);
            }
        });
        dashpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dashboard.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        dashboard.setForeground(new java.awt.Color(239, 234, 234));
        dashboard.setText("Dashboard");
        dashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                dashboardMouseEntered(evt);
            }
        });
        dashpanel.add(dashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 80, 20));

        listadmin.add(dashpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 130, 40));

        availableemployeerpanel.setBackground(new java.awt.Color(29, 45, 61));
        availableemployeerpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                availableemployeerpanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                availableemployeerpanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                availableemployeerpanelMouseExited(evt);
            }
        });
        availableemployeerpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        availableemployee.setBackground(new java.awt.Color(29, 45, 61));
        availableemployee.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        availableemployee.setForeground(new java.awt.Color(239, 234, 234));
        availableemployee.setText("Av. Employee");
        availableemployeerpanel.add(availableemployee, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 90, -1));

        createbookingpanel3.setBackground(new java.awt.Color(29, 45, 61));
        createbookingpanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createbookingpanel3MouseClicked(evt);
            }
        });
        createbookingpanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        createbooking3.setBackground(new java.awt.Color(29, 45, 61));
        createbooking3.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        createbooking3.setForeground(new java.awt.Color(239, 234, 234));
        createbooking3.setText("Create Booking");
        createbookingpanel3.add(createbooking3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 100, -1));

        availableemployeerpanel.add(createbookingpanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 160, 40));

        listadmin.add(availableemployeerpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 130, 40));

        viewservicespanel.setBackground(new java.awt.Color(29, 45, 61));
        viewservicespanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewservicespanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                viewservicespanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                viewservicespanelMouseExited(evt);
            }
        });
        viewservicespanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        viewservices.setBackground(new java.awt.Color(29, 45, 61));
        viewservices.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        viewservices.setForeground(new java.awt.Color(239, 234, 234));
        viewservices.setText("View Services");
        viewservicespanel.add(viewservices, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 100, -1));

        listadmin.add(viewservicespanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 130, 40));

        createbookingpanel.setBackground(new java.awt.Color(29, 45, 61));
        createbookingpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createbookingpanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                createbookingpanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                createbookingpanelMouseExited(evt);
            }
        });
        createbookingpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        createbooking.setBackground(new java.awt.Color(29, 45, 61));
        createbooking.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        createbooking.setForeground(new java.awt.Color(239, 234, 234));
        createbooking.setText("Create Booking");
        createbookingpanel.add(createbooking, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 100, -1));

        listadmin.add(createbookingpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 130, 40));

        servicepanel.setBackground(new java.awt.Color(29, 45, 61));
        servicepanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        listadmin.add(servicepanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, -1, 40));

        lblUsername.setBackground(new java.awt.Color(153, 255, 255));
        lblUsername.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        lblUsername.setText("Staff");
        listadmin.add(lblUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 70, 20));

        staff.setBackground(new java.awt.Color(153, 255, 255));
        staff.setFont(new java.awt.Font("Century Gothic", 1, 10)); // NOI18N
        staff.setForeground(new java.awt.Color(153, 255, 255));
        staff.setText("Staff");
        listadmin.add(staff, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 70, 20));

        mybookingspanel.setBackground(new java.awt.Color(29, 45, 61));
        mybookingspanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mybookingspanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mybookingspanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mybookingspanelMouseExited(evt);
            }
        });
        mybookingspanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mybookings.setBackground(new java.awt.Color(29, 45, 61));
        mybookings.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        mybookings.setForeground(new java.awt.Color(239, 234, 234));
        mybookings.setText("My Bookings");
        mybookingspanel.add(mybookings, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 90, -1));

        createbookingpanel5.setBackground(new java.awt.Color(29, 45, 61));
        createbookingpanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createbookingpanel5MouseClicked(evt);
            }
        });
        createbookingpanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        createbooking5.setBackground(new java.awt.Color(29, 45, 61));
        createbooking5.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        createbooking5.setForeground(new java.awt.Color(239, 234, 234));
        createbooking5.setText("Create Booking");
        createbookingpanel5.add(createbooking5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 100, -1));

        mybookingspanel.add(createbookingpanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 160, 40));

        listadmin.add(mybookingspanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 130, 40));

        feedbackpanel.setBackground(new java.awt.Color(29, 45, 61));
        feedbackpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                feedbackpanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                feedbackpanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                feedbackpanelMouseExited(evt);
            }
        });
        feedbackpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        feedback.setBackground(new java.awt.Color(29, 45, 61));
        feedback.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        feedback.setForeground(new java.awt.Color(239, 234, 234));
        feedback.setText("Feedback");
        feedbackpanel.add(feedback, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 90, -1));

        createbookingpanel6.setBackground(new java.awt.Color(29, 45, 61));
        createbookingpanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createbookingpanel6MouseClicked(evt);
            }
        });
        createbookingpanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        createbooking6.setBackground(new java.awt.Color(29, 45, 61));
        createbooking6.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        createbooking6.setForeground(new java.awt.Color(239, 234, 234));
        createbooking6.setText("Create Booking");
        createbookingpanel6.add(createbooking6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 100, -1));

        feedbackpanel.add(createbookingpanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 160, 40));

        listadmin.add(feedbackpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 130, 40));

        jPanel4.add(listadmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 130, 340));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 490));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void panelbottomMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelbottomMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelbottomMouseClicked

    private void exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMouseClicked
       
    int confirm = JOptionPane.showConfirmDialog(this,
        "Are you sure you want to log out?",
        "Log Out",
        JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        Session.getInstance().logout();
        new LoginForm().setVisible(true);
        this.dispose();
    }
    }//GEN-LAST:event_exitMouseClicked

    private void border1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_border1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_border1MouseClicked

    private void border2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_border2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_border2MouseClicked

    private void borderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_borderMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_borderMouseClicked

    private void newbookingpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newbookingpanelMouseClicked
        new CreateBooking().setVisible(true);
    this.dispose();
    }//GEN-LAST:event_newbookingpanelMouseClicked

    private void viewallborderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewallborderMouseClicked
           new MyBookings().setVisible(true);
    this.dispose();
    }//GEN-LAST:event_viewallborderMouseClicked

    private void reporttypecomboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reporttypecomboboxActionPerformed
        filterRecentBookings();
    }//GEN-LAST:event_reporttypecomboboxActionPerformed

    private void settingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsMouseClicked
        new Profile().setVisible(true); this.dispose();
    }//GEN-LAST:event_settingsMouseClicked

    private void dashboardMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashboardMouseEntered

    }//GEN-LAST:event_dashboardMouseEntered

    private void dashpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashpanelMouseClicked
        new StaffDash().setVisible(true); this.dispose();
    }//GEN-LAST:event_dashpanelMouseClicked

    private void dashpanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashpanelMouseEntered

    }//GEN-LAST:event_dashpanelMouseEntered

    private void dashpanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashpanelMouseExited

    }//GEN-LAST:event_dashpanelMouseExited

    private void createbookingpanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createbookingpanel3MouseClicked

    }//GEN-LAST:event_createbookingpanel3MouseClicked

    private void availableemployeerpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_availableemployeerpanelMouseClicked
        new AvailableEmployee().setVisible(true); this.dispose();
    }//GEN-LAST:event_availableemployeerpanelMouseClicked

    private void availableemployeerpanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_availableemployeerpanelMouseEntered

    }//GEN-LAST:event_availableemployeerpanelMouseEntered

    private void availableemployeerpanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_availableemployeerpanelMouseExited

    }//GEN-LAST:event_availableemployeerpanelMouseExited

    private void viewservicespanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewservicespanelMouseClicked
        new ViewServices().setVisible(true); this.dispose();
    }//GEN-LAST:event_viewservicespanelMouseClicked

    private void viewservicespanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewservicespanelMouseEntered

    }//GEN-LAST:event_viewservicespanelMouseEntered

    private void viewservicespanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewservicespanelMouseExited

    }//GEN-LAST:event_viewservicespanelMouseExited

    private void createbookingpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createbookingpanelMouseClicked
        new CreateBooking().setVisible(true); this.dispose();
    }//GEN-LAST:event_createbookingpanelMouseClicked

    private void createbookingpanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createbookingpanelMouseEntered

    }//GEN-LAST:event_createbookingpanelMouseEntered

    private void createbookingpanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createbookingpanelMouseExited

    }//GEN-LAST:event_createbookingpanelMouseExited

    private void createbookingpanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createbookingpanel5MouseClicked

    }//GEN-LAST:event_createbookingpanel5MouseClicked

    private void mybookingspanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mybookingspanelMouseClicked
        new MyBookings().setVisible(true); this.dispose();
    }//GEN-LAST:event_mybookingspanelMouseClicked

    private void mybookingspanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mybookingspanelMouseEntered

    }//GEN-LAST:event_mybookingspanelMouseEntered

    private void mybookingspanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mybookingspanelMouseExited

    }//GEN-LAST:event_mybookingspanelMouseExited

    private void createbookingpanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createbookingpanel6MouseClicked

    }//GEN-LAST:event_createbookingpanel6MouseClicked

    private void feedbackpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_feedbackpanelMouseClicked
        new Feedback().setVisible(true); this.dispose();
    }//GEN-LAST:event_feedbackpanelMouseClicked

    private void feedbackpanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_feedbackpanelMouseEntered

    }//GEN-LAST:event_feedbackpanelMouseEntered

    private void feedbackpanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_feedbackpanelMouseExited

    }//GEN-LAST:event_feedbackpanelMouseExited

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
    private javax.swing.JTable TableRecentBookings;
    private javax.swing.JLabel Thisweek;
    private javax.swing.JLabel availableemployee;
    private javax.swing.JPanel availableemployeerpanel;
    private javax.swing.JLabel avgrating;
    private javax.swing.JPanel border;
    private javax.swing.JPanel border1;
    private javax.swing.JPanel border2;
    private javax.swing.JLabel cancelled;
    private javax.swing.JLabel cancellednum;
    private javax.swing.JPanel cancelledpanel;
    private javax.swing.JLabel completed;
    private javax.swing.JLabel completednum;
    private javax.swing.JPanel completedpanel;
    private javax.swing.JLabel createbooking;
    private javax.swing.JLabel createbooking3;
    private javax.swing.JLabel createbooking5;
    private javax.swing.JLabel createbooking6;
    private javax.swing.JPanel createbookingpanel;
    private javax.swing.JPanel createbookingpanel3;
    private javax.swing.JPanel createbookingpanel5;
    private javax.swing.JPanel createbookingpanel6;
    private javax.swing.JLabel dashboard;
    private javax.swing.JLabel dashboardheader;
    private javax.swing.JPanel dashpanel;
    private javax.swing.JLabel exit;
    private javax.swing.JLabel feedback;
    private javax.swing.JPanel feedbackpanel;
    private javax.swing.JPanel green;
    private javax.swing.JPanel green1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lg;
    private javax.swing.JPanel listadmin;
    private javax.swing.JLabel mybookings;
    private javax.swing.JPanel mybookingspanel;
    private javax.swing.JLabel newbooking;
    private javax.swing.JPanel newbookingpanel;
    private javax.swing.JLabel newbookingsweek;
    private javax.swing.JLabel newbookingsweeknum;
    private javax.swing.JPanel panelbottom;
    private javax.swing.JLabel pending;
    private javax.swing.JLabel pendingnum;
    private javax.swing.JPanel pendingpanel;
    private javax.swing.JLabel quickactions;
    private javax.swing.JPanel quickactionsborder;
    private javax.swing.JLabel rating;
    private javax.swing.JLabel recentbookings;
    private javax.swing.JScrollPane recentbookingscrollpane;
    private javax.swing.JPanel red;
    private javax.swing.JPanel red1;
    private javax.swing.JComboBox<String> reporttypecombobox;
    private javax.swing.JLabel revenueweek;
    private javax.swing.JLabel revenueweeknum;
    private javax.swing.JPanel servicepanel;
    private javax.swing.JLabel settings;
    private javax.swing.JPanel skyblue;
    private javax.swing.JPanel skyblue1;
    private javax.swing.JLabel staff;
    private javax.swing.JPanel thisweekpanel;
    private javax.swing.JLabel totalbookings1;
    private javax.swing.JLabel totalbookingsnum;
    private javax.swing.JPanel totalbookingspanel;
    private javax.swing.JLabel viewall;
    private javax.swing.JPanel viewallborder;
    private javax.swing.JLabel viewservices;
    private javax.swing.JPanel viewservicespanel;
    private javax.swing.JPanel yellow;
    private javax.swing.JPanel yellow1;
    // End of variables declaration//GEN-END:variables
}
