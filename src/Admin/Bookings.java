/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import AdminInternalPage.Profile;
import LoginandRegister.LoginForm;
import Session.Session;
import Staff.GenerateReceipt;
import config.config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
public class Bookings extends javax.swing.JFrame {

     private final config conf = new config();
 
    public Bookings() {
        initComponents();
        loadBookingCounts();
        loadBookings("All");
        wireSearch();
        // Show session username
        if (Session.getInstance().isLoggedIn()) {
            lblUsername.setText(Session.getInstance().getUsername());
        }
    }
 
    // ── Load summary counts ───────────────────────────────────────────────────
    private void loadBookingCounts() {
        donenum.setText(countBookings("Done"));
        progressnum.setText(countBookings("In Progress"));
        cancellednum.setText(countBookings("Cancelled"));
        pendingnum.setText(countBookings("Pending"));
        Object total = conf.getValue("SELECT COUNT(*) FROM tbl_bookings");
        totalnum.setText(total != null ? total.toString() : "0");
    }
 
    private String countBookings(String status) {
        Object v = conf.getValue("SELECT COUNT(*) FROM tbl_bookings WHERE b_status = ?", status);
        return v != null ? v.toString() : "0";
    }
 
    // ── Load bookings table — pass "All" for no status filter ────────────────
    private void loadBookings(String statusFilter) {
        String sql;
        if ("All".equals(statusFilter)) {
            sql = "SELECT b_id AS ID, b_customer AS Customer, b_service AS Service, " +
                  "b_employee AS Employee, b_date AS Date, b_status AS Status " +
                  "FROM tbl_bookings ORDER BY b_id DESC";
            conf.displayData(sql, TableBookings);
        } else {
            sql = "SELECT b_id AS ID, b_customer AS Customer, b_service AS Service, " +
                  "b_employee AS Employee, b_date AS Date, b_status AS Status " +
                  "FROM tbl_bookings WHERE b_status = ? ORDER BY b_id DESC";
            conf.displayData(sql, TableBookings, statusFilter);
        }
    }
 
    // ── Search by customer name ───────────────────────────────────────────────
    private void wireSearch() {
        searchfield.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                String keyword = searchfield.getText().trim();
                if (keyword.isEmpty() || keyword.equals("Search ")) {
                    loadBookings("All");
                } else {
                    conf.displayData(
                        "SELECT b_id AS ID, b_customer AS Customer, b_service AS Service, " +
                        "b_employee AS Employee, b_date AS Date, b_status AS Status " +
                        "FROM tbl_bookings WHERE b_customer LIKE ? ORDER BY b_id DESC",
                        TableBookings, "%" + keyword + "%");
                }
            }
        });
    }
 
    // ── Open receipt for selected booking ─────────────────────────────────────
    private void openReceiptForSelected() {
        int row = TableBookings.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                "Please select a booking to view its receipt.",
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Object idObj = TableBookings.getValueAt(row, 0);
        if (idObj == null) return;
        int bookingId = Integer.parseInt(idObj.toString());
 
        String sql = "SELECT b_id, b_date, b_customer, b_address, b_contact, " +
                     "b_employee, b_service, b_price, b_tasknote, b_staff, " +
                     "s_description " +
                     "FROM tbl_bookings " +
                     "LEFT JOIN tbl_services ON tbl_bookings.b_service = tbl_services.s_name " +
                     "WHERE b_id = ?";
        try (Connection conn = config.connectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    GenerateReceipt gr = new GenerateReceipt(
                        rs.getInt("b_id"),
                        rs.getString("b_date"),
                        rs.getString("b_customer"),
                        rs.getString("b_address"),
                        rs.getString("b_contact"),
                        rs.getString("b_employee"),
                        rs.getString("b_service"),
                        String.valueOf(rs.getDouble("b_price")),
                        rs.getString("b_tasknote"),
                        rs.getString("b_staff"),
                        rs.getString("s_description")
                    );
                    gr.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                    gr.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Booking not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading receipt: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        green = new javax.swing.JPanel();
        pendingnum = new javax.swing.JLabel();
        pending = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lg = new javax.swing.JLabel();
        Bookingsheader = new javax.swing.JLabel();
        exit = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        border2 = new javax.swing.JPanel();
        border3 = new javax.swing.JPanel();
        bookingsscrollpane = new javax.swing.JScrollPane();
        TableBookings = new javax.swing.JTable();
        searchfield = new javax.swing.JTextField();
        red = new javax.swing.JPanel();
        cancellednum = new javax.swing.JLabel();
        cancelled = new javax.swing.JLabel();
        yellow = new javax.swing.JPanel();
        progressnum = new javax.swing.JLabel();
        progress = new javax.swing.JLabel();
        skyblue = new javax.swing.JPanel();
        donenum = new javax.swing.JLabel();
        done = new javax.swing.JLabel();
        black = new javax.swing.JPanel();
        totalnum = new javax.swing.JLabel();
        total = new javax.swing.JLabel();
        pendingpanel = new javax.swing.JPanel();
        pendingbutton = new javax.swing.JLabel();
        progresspanel = new javax.swing.JPanel();
        progressbutton = new javax.swing.JLabel();
        donepanel = new javax.swing.JPanel();
        donebutton = new javax.swing.JLabel();
        cancelledpanel = new javax.swing.JPanel();
        cancelledbutton = new javax.swing.JLabel();
        seereceiptpanel = new javax.swing.JPanel();
        seereceipt = new javax.swing.JLabel();
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

        green.setBackground(new java.awt.Color(0, 153, 0));
        green.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        green.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pendingnum.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        pendingnum.setForeground(new java.awt.Color(51, 255, 51));
        pendingnum.setText("0");
        green.add(pendingnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 20, 30));

        pending.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        pending.setForeground(new java.awt.Color(51, 255, 51));
        pending.setText("Pending");
        green.add(pending, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 60, 30));

        jPanel4.add(green, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 80, 90, 30));

        jPanel1.setBackground(new java.awt.Color(29, 45, 61));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lg.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        lg.setForeground(new java.awt.Color(239, 234, 234));
        lg.setText("Log Out");
        jPanel1.add(lg, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, -1, 20));

        Bookingsheader.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        Bookingsheader.setForeground(new java.awt.Color(239, 234, 234));
        Bookingsheader.setText("Bookings");
        jPanel1.add(Bookingsheader, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 190, 50));

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

        border2.setBackground(new java.awt.Color(153, 255, 255));
        border2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        border2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(border2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 630, 10));

        border3.setBackground(new java.awt.Color(153, 255, 255));
        border3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(border3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 490));

        bookingsscrollpane.setBackground(new java.awt.Color(29, 45, 61));
        bookingsscrollpane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        bookingsscrollpane.setForeground(new java.awt.Color(255, 255, 255));

        TableBookings.setBackground(new java.awt.Color(29, 45, 61));
        TableBookings.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        TableBookings.setForeground(new java.awt.Color(255, 255, 255));
        TableBookings.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Employee ID", "Name ", "Email", "Contact No.", "Status"
            }
        ));
        bookingsscrollpane.setViewportView(TableBookings);

        jPanel4.add(bookingsscrollpane, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 130, 610, 350));

        searchfield.setFont(new java.awt.Font("Bahnschrift", 0, 11)); // NOI18N
        searchfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchfieldActionPerformed(evt);
            }
        });
        jPanel4.add(searchfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 110, 320, 20));

        red.setBackground(new java.awt.Color(153, 0, 51));
        red.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        red.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cancellednum.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        cancellednum.setForeground(new java.awt.Color(255, 102, 102));
        cancellednum.setText("0");
        red.add(cancellednum, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 20, 30));

        cancelled.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        cancelled.setForeground(new java.awt.Color(255, 102, 102));
        cancelled.setText("Cancelled");
        red.add(cancelled, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 70, 30));

        jPanel4.add(red, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 80, 100, 30));

        yellow.setBackground(new java.awt.Color(153, 153, 0));
        yellow.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        yellow.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        progressnum.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        progressnum.setForeground(new java.awt.Color(255, 204, 102));
        progressnum.setText("0");
        yellow.add(progressnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 20, 30));

        progress.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        progress.setForeground(new java.awt.Color(255, 204, 102));
        progress.setText("Progress");
        yellow.add(progress, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 60, 30));

        jPanel4.add(yellow, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 80, 100, 30));

        skyblue.setBackground(new java.awt.Color(0, 153, 153));
        skyblue.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        skyblue.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        donenum.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        donenum.setForeground(new java.awt.Color(0, 255, 255));
        donenum.setText("0");
        skyblue.add(donenum, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 20, 30));

        done.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        done.setForeground(new java.awt.Color(0, 255, 255));
        done.setText("Done");
        skyblue.add(done, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 40, 30));

        jPanel4.add(skyblue, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, 70, 30));

        black.setBackground(new java.awt.Color(29, 45, 61));
        black.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        black.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        totalnum.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        totalnum.setForeground(new java.awt.Color(239, 234, 234));
        totalnum.setText("0");
        black.add(totalnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 40, 30));

        total.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        total.setForeground(new java.awt.Color(239, 234, 234));
        total.setText("Total");
        black.add(total, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 5, -1, 20));

        jPanel4.add(black, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 80, 80, 30));

        pendingpanel.setBackground(new java.awt.Color(29, 45, 61));
        pendingpanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        pendingpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pendingpanelMouseClicked(evt);
            }
        });

        pendingbutton.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        pendingbutton.setForeground(new java.awt.Color(239, 234, 234));
        pendingbutton.setText("PENDING");
        pendingpanel.add(pendingbutton);

        jPanel4.add(pendingpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 110, 60, 20));

        progresspanel.setBackground(new java.awt.Color(29, 45, 61));
        progresspanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        progresspanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                progresspanelMouseClicked(evt);
            }
        });

        progressbutton.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        progressbutton.setForeground(new java.awt.Color(239, 234, 234));
        progressbutton.setText("PROGRESS");
        progresspanel.add(progressbutton);

        jPanel4.add(progresspanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 110, 70, 20));

        donepanel.setBackground(new java.awt.Color(29, 45, 61));
        donepanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        donepanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                donepanelMouseClicked(evt);
            }
        });

        donebutton.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        donebutton.setForeground(new java.awt.Color(239, 234, 234));
        donebutton.setText("DONE");
        donepanel.add(donebutton);

        jPanel4.add(donepanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 110, 50, 20));

        cancelledpanel.setBackground(new java.awt.Color(29, 45, 61));
        cancelledpanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        cancelledpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelledpanelMouseClicked(evt);
            }
        });

        cancelledbutton.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        cancelledbutton.setForeground(new java.awt.Color(239, 234, 234));
        cancelledbutton.setText("CANCELLED");
        cancelledpanel.add(cancelledbutton);

        jPanel4.add(cancelledpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 110, 80, 20));

        seereceiptpanel.setBackground(new java.awt.Color(29, 45, 61));
        seereceiptpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                seereceiptpanelMouseClicked(evt);
            }
        });
        seereceiptpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        seereceipt.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        seereceipt.setForeground(new java.awt.Color(255, 255, 255));
        seereceipt.setText("See Receipt");
        seereceipt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                seereceiptMouseClicked(evt);
            }
        });
        seereceiptpanel.add(seereceipt, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 90, 30));

        jPanel4.add(seereceiptpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 80, -1, 30));

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
            .addGap(0, 800, Short.MAX_VALUE)
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

    private void pendingpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pendingpanelMouseClicked
        loadBookings("Pending");
    }//GEN-LAST:event_pendingpanelMouseClicked

    private void progresspanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_progresspanelMouseClicked
           loadBookings("In Progress");
    }//GEN-LAST:event_progresspanelMouseClicked

    private void donepanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_donepanelMouseClicked
          loadBookings("Done");
    }//GEN-LAST:event_donepanelMouseClicked

    private void cancelledpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelledpanelMouseClicked
        loadBookings("Cancelled");
    }//GEN-LAST:event_cancelledpanelMouseClicked

    private void seereceiptMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_seereceiptMouseClicked
              openReceiptForSelected();
    }//GEN-LAST:event_seereceiptMouseClicked

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

    private void seereceiptpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_seereceiptpanelMouseClicked
            openReceiptForSelected();
    }//GEN-LAST:event_seereceiptpanelMouseClicked

    private void exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMouseClicked
        int c = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);
        if (c == JOptionPane.YES_OPTION) {
            Session.getInstance().logout();
            new LoginForm().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_exitMouseClicked

    private void searchfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchfieldActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        Session session = Session.getInstance();
        if (!session.isLoggedIn()) {
            JOptionPane.showMessageDialog(null, "Please login first!", "Unauthorized Access", JOptionPane.WARNING_MESSAGE);
            java.awt.EventQueue.invokeLater(() -> new LoginForm().setVisible(true)); return;
        }
        java.awt.EventQueue.invokeLater(() -> new Bookings().setVisible(true));
    
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Bookingsheader;
    private javax.swing.JTable TableBookings;
    private javax.swing.JLabel administrator;
    private javax.swing.JPanel black;
    private javax.swing.JLabel bookings;
    private javax.swing.JPanel bookingspanel;
    private javax.swing.JScrollPane bookingsscrollpane;
    private javax.swing.JPanel border2;
    private javax.swing.JPanel border3;
    private javax.swing.JLabel cancelled;
    private javax.swing.JLabel cancelledbutton;
    private javax.swing.JLabel cancellednum;
    private javax.swing.JPanel cancelledpanel;
    private javax.swing.JLabel dashboard1;
    private javax.swing.JPanel dashpanel;
    private javax.swing.JLabel done;
    private javax.swing.JLabel donebutton;
    private javax.swing.JLabel donenum;
    private javax.swing.JPanel donepanel;
    private javax.swing.JLabel employee;
    private javax.swing.JPanel employeepanel;
    private javax.swing.JLabel exit;
    private javax.swing.JLabel feedback;
    private javax.swing.JPanel feedbackpanel;
    private javax.swing.JPanel green;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lg;
    private javax.swing.JPanel listadmin;
    private javax.swing.JLabel pending;
    private javax.swing.JLabel pendingbutton;
    private javax.swing.JLabel pendingnum;
    private javax.swing.JPanel pendingpanel;
    private javax.swing.JLabel progress;
    private javax.swing.JLabel progressbutton;
    private javax.swing.JLabel progressnum;
    private javax.swing.JPanel progresspanel;
    private javax.swing.JPanel red;
    private javax.swing.JLabel reports;
    private javax.swing.JPanel reportspanel;
    private javax.swing.JLabel review;
    private javax.swing.JTextField searchfield;
    private javax.swing.JLabel seereceipt;
    private javax.swing.JPanel seereceiptpanel;
    private javax.swing.JLabel services;
    private javax.swing.JPanel servicespanel;
    private javax.swing.JLabel settings;
    private javax.swing.JPanel skyblue;
    private javax.swing.JLabel total;
    private javax.swing.JLabel totalnum;
    private javax.swing.JPanel userpanel;
    private javax.swing.JLabel users;
    private javax.swing.JPanel yellow;
    // End of variables declaration//GEN-END:variables
}
