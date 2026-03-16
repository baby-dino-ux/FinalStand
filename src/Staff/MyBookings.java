package Staff;

import AdminInternalPage.Profile;
import LoginandRegister.LoginForm;
import Session.Session;
import StaffInternalPage.MyBookingForm;
import config.config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class MyBookings extends javax.swing.JFrame {

    private final config conf = new config();

    public MyBookings() {
        initComponents();
        loadSessionInfo();

        // ── Populate the filter combobox ──────────────────────────────────────
        reporttypecombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{
            "All My Bookings",
            "Pending Only",
            "In Progress Only",
            "Done Only",
            "Cancelled Only"
        }));

        reporttypecombobox.addActionListener(e -> filterMyBookings());
        filterMyBookings();

        searchfield.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e)  { filterMyBookings(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e)  { filterMyBookings(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filterMyBookings(); }
        });

        // ── Wire See Receipt button ───────────────────────────────────────────
        seereceiptpanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) { openReceipt(); }
        });
    }

    private void loadSessionInfo() {
        Session session = Session.getInstance();
        lblUsername.setText(session.isLoggedIn() ? session.getFullName() : "Staff");
    }

    private void filterMyBookings() {
        String selected  = reporttypecombobox.getSelectedItem() != null
                           ? reporttypecombobox.getSelectedItem().toString() : "All My Bookings";
        String kw        = searchfield.getText().trim();
        if (kw.equals("Search ")) kw = "";
        String staffName = Session.getInstance().getFullName();

        String statusClause;
        switch (selected) {
            case "Pending Only":     statusClause = " AND b_status = 'Pending'"; break;
            case "In Progress Only": statusClause = " AND b_status = 'In Progress'"; break;
            case "Done Only":        statusClause = " AND b_status = 'Done'"; break;
            case "Cancelled Only":   statusClause = " AND b_status = 'Cancelled'"; break;
            default:                 statusClause = ""; break;
        }

        String sql = "SELECT b_id AS 'ID', b_customer AS 'Customer', b_service AS 'Service', " +
                     "b_employee AS 'Employee', b_date AS 'Date', b_status AS 'Status' " +
                     "FROM tbl_bookings WHERE b_staff = ?" + statusClause;
        if (!kw.isEmpty()) {
            String k = kw.replace("'", "''");
            sql += " AND (b_customer LIKE '%" + k + "%' OR b_service LIKE '%" + k + "%' " +
                   "OR b_employee LIKE '%" + k + "%' OR b_status LIKE '%" + k + "%')";
        }
        sql += " ORDER BY b_id DESC";
        conf.displayData(sql, TableMyBooking, staffName);
    }

    public void loadTable(String keyword) {
        reporttypecombobox.setSelectedIndex(0);
        filterMyBookings();
    }

    private void openReceipt() {
        int row = TableMyBooking.getSelectedRow();
        if (row < 0) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Please select a booking to view its receipt.",
                "No Selection", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        int    bookingId = Integer.parseInt(TableMyBooking.getValueAt(row, 0).toString());
        String customer  = TableMyBooking.getValueAt(row, 1).toString();
        String service   = TableMyBooking.getValueAt(row, 2).toString();
        String employee  = TableMyBooking.getValueAt(row, 3).toString();
        String date      = TableMyBooking.getValueAt(row, 4).toString();
        String address = "", contact = "", staffName = "", taskNote = "";
        double price = 0;
        try (Connection conn = config.connectDB();
             PreparedStatement ps = conn.prepareStatement(
                 "SELECT b_address, b_contact, b_staff, b_price, b_tasknote FROM tbl_bookings WHERE b_id=?")) {
            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    address   = rs.getString("b_address")  != null ? rs.getString("b_address")  : "";
                    contact   = rs.getString("b_contact")  != null ? rs.getString("b_contact")  : "";
                    staffName = rs.getString("b_staff")    != null ? rs.getString("b_staff")    : "";
                    taskNote  = rs.getString("b_tasknote") != null ? rs.getString("b_tasknote") : "";
                    price     = rs.getDouble("b_price");
                }
            }
        } catch (SQLException e) { System.out.println("Receipt load error: " + e.getMessage()); }
        GenerateReceipt receipt = new GenerateReceipt(
            bookingId, date, customer, address, contact,
            employee, service, String.valueOf(price), taskNote, staffName);
        receipt.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        receipt.setVisible(true);
    }

    private int getSelectedId() {
        int row = TableMyBooking.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a booking from the table first.",
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return -1;
        }
        return Integer.parseInt(TableMyBooking.getValueAt(row, 0).toString());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        panelbottom = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lg = new javax.swing.JLabel();
        exit = new javax.swing.JLabel();
        name2 = new javax.swing.JLabel();
        border1 = new javax.swing.JPanel();
        border2 = new javax.swing.JPanel();
        border = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        mybookingscrollpane = new javax.swing.JScrollPane();
        TableMyBooking = new javax.swing.JTable();
        seereceiptpanel = new javax.swing.JPanel();
        addpanel = new javax.swing.JPanel();
        add = new javax.swing.JLabel();
        updatepanel = new javax.swing.JPanel();
        update = new javax.swing.JLabel();
        deletepanel = new javax.swing.JPanel();
        delete = new javax.swing.JLabel();
        searchfield = new javax.swing.JTextField();
        reporttypecombobox = new javax.swing.JComboBox<>();
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
        seereceiptpanel1 = new javax.swing.JPanel();
        seereceipt = new javax.swing.JLabel();

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
        jPanel1.add(lg, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 10, -1, 20));

        exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout (2).png"))); // NOI18N
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitMouseClicked(evt);
            }
        });
        jPanel1.add(exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 10, 30, 20));

        name2.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        name2.setForeground(new java.awt.Color(255, 255, 255));
        name2.setText("My Booking");
        jPanel1.add(name2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 130, 50));

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
        border2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                border2MouseClicked(evt);
            }
        });
        border2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(border2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 650, 10));

        border.setBackground(new java.awt.Color(153, 255, 255));
        border.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                borderMouseClicked(evt);
            }
        });
        border.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(border, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 490));

        jPanel2.setBackground(new java.awt.Color(29, 45, 61));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableMyBooking.setBackground(new java.awt.Color(29, 45, 61));
        TableMyBooking.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        TableMyBooking.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        TableMyBooking.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Booking ID", "Customer Name", "Service", "Assign Cleaner", "Booking Date", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        TableMyBooking.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        mybookingscrollpane.setViewportView(TableMyBooking);

        jPanel2.add(mybookingscrollpane, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 610, 320));

        seereceiptpanel.setBackground(new java.awt.Color(29, 45, 61));
        seereceiptpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(seereceiptpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 60, -1, 30));

        addpanel.setBackground(new java.awt.Color(153, 255, 255));
        addpanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        addpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addpanelMouseClicked(evt);
            }
        });
        addpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        add.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        add.setText("ADD");
        add.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addMouseEntered(evt);
            }
        });
        addpanel.add(add, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 40, 20));

        jPanel2.add(addpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, -1, -1));

        updatepanel.setBackground(new java.awt.Color(153, 255, 255));
        updatepanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        updatepanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updatepanelMouseClicked(evt);
            }
        });
        updatepanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        update.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        update.setForeground(new java.awt.Color(51, 51, 51));
        update.setText("UPDATE");
        update.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                updateMouseEntered(evt);
            }
        });
        updatepanel.add(update, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 20));

        jPanel2.add(updatepanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 20, 60, -1));

        deletepanel.setBackground(new java.awt.Color(153, 255, 255));
        deletepanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        deletepanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deletepanelMouseClicked(evt);
            }
        });
        deletepanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        delete.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        delete.setForeground(new java.awt.Color(51, 51, 51));
        delete.setText("DELETE");
        delete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                deleteMouseEntered(evt);
            }
        });
        deletepanel.add(delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 0, 60, 20));

        jPanel2.add(deletepanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 20, 60, -1));

        searchfield.setFont(new java.awt.Font("Bahnschrift", 0, 11)); // NOI18N
        searchfield.setText("Search ");
        searchfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchfieldActionPerformed(evt);
            }
        });
        jPanel2.add(searchfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 340, -1));

        reporttypecombobox.setBackground(new java.awt.Color(29, 45, 61));
        reporttypecombobox.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        reporttypecombobox.setForeground(new java.awt.Color(29, 45, 61));
        reporttypecombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        reporttypecombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reporttypecomboboxActionPerformed(evt);
            }
        });
        jPanel2.add(reporttypecombobox, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 20, 100, 20));

        jPanel4.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 630, 370));

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
        availableemployeerpanel.add(availableemployee, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 90, -1));

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
        viewservicespanel.add(viewservices, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 100, -1));

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
        createbookingpanel.add(createbooking, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 100, -1));

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
        mybookingspanel.add(mybookings, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 90, -1));

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

        seereceiptpanel1.setBackground(new java.awt.Color(29, 45, 61));
        seereceiptpanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        seereceipt.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        seereceipt.setForeground(new java.awt.Color(255, 255, 255));
        seereceipt.setText("See Receipt");
        seereceiptpanel1.add(seereceipt, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 90, 30));

        jPanel4.add(seereceiptpanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 70, -1, 30));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 500));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void panelbottomMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelbottomMouseClicked
    }//GEN-LAST:event_panelbottomMouseClicked

    private void exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMouseClicked
        int c = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);
        if (c == JOptionPane.YES_OPTION) { Session.getInstance().logout(); new LoginForm().setVisible(true); this.dispose(); }
    }//GEN-LAST:event_exitMouseClicked

    private void addMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseEntered
    }//GEN-LAST:event_addMouseEntered
    private void border1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_border1MouseClicked
    }//GEN-LAST:event_border1MouseClicked
    private void border2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_border2MouseClicked
    }//GEN-LAST:event_border2MouseClicked
    private void borderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_borderMouseClicked
    }//GEN-LAST:event_borderMouseClicked
    private void deleteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteMouseEntered
    }//GEN-LAST:event_deleteMouseEntered
    private void updateMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateMouseEntered
    }//GEN-LAST:event_updateMouseEntered

    private void searchfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchfieldActionPerformed
        filterMyBookings();
    }//GEN-LAST:event_searchfieldActionPerformed

    private void addpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addpanelMouseClicked
        new CreateBooking().setVisible(true); this.dispose();
    }//GEN-LAST:event_addpanelMouseClicked

    private void updatepanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updatepanelMouseClicked
        int row = TableMyBooking.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a booking to update.", "No Selection", JOptionPane.WARNING_MESSAGE); return; }
        int    id       = Integer.parseInt(TableMyBooking.getValueAt(row, 0).toString());
        String customer = TableMyBooking.getValueAt(row, 1).toString();
        String service  = TableMyBooking.getValueAt(row, 2).toString();
        String employee = TableMyBooking.getValueAt(row, 3).toString();
        String date     = TableMyBooking.getValueAt(row, 4).toString();
        String status   = TableMyBooking.getValueAt(row, 5).toString();
        MyBookingForm form = new MyBookingForm(id, customer, service, employee, date, status, this);
        form.setVisible(true);
    }//GEN-LAST:event_updatepanelMouseClicked

    private void deletepanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deletepanelMouseClicked
        int id = getSelectedId();
        if (id < 0) return;
        int row = TableMyBooking.getSelectedRow();
        String status = TableMyBooking.getValueAt(row, 5).toString();
        if (!"Pending".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(this, "Only Pending bookings can be deleted.\nThis booking is: " + status, "Cannot Delete", JOptionPane.WARNING_MESSAGE); return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Delete this booking? This cannot be undone.", "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            conf.deleteRecord("DELETE FROM tbl_bookings WHERE b_id = ?", id);
            JOptionPane.showMessageDialog(this, "Booking deleted.", "Deleted", JOptionPane.INFORMATION_MESSAGE);
            filterMyBookings();
        }
    }//GEN-LAST:event_deletepanelMouseClicked

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

    private void reporttypecomboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reporttypecomboboxActionPerformed
        filterMyBookings();
    }//GEN-LAST:event_reporttypecomboboxActionPerformed

    public static void main(String args[]) {
        Session session = Session.getInstance();
        if (!session.isLoggedIn()) {
            JOptionPane.showMessageDialog(null, "Please login first!", "Unauthorized Access", JOptionPane.WARNING_MESSAGE);
            java.awt.EventQueue.invokeLater(() -> new LoginForm().setVisible(true)); return;
        }
        java.awt.EventQueue.invokeLater(() -> new StaffDash().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TableMyBooking;
    private javax.swing.JLabel add;
    private javax.swing.JPanel addpanel;
    private javax.swing.JLabel availableemployee;
    private javax.swing.JPanel availableemployeerpanel;
    private javax.swing.JPanel border;
    private javax.swing.JPanel border1;
    private javax.swing.JPanel border2;
    private javax.swing.JLabel createbooking;
    private javax.swing.JLabel createbooking3;
    private javax.swing.JLabel createbooking5;
    private javax.swing.JLabel createbooking6;
    private javax.swing.JPanel createbookingpanel;
    private javax.swing.JPanel createbookingpanel3;
    private javax.swing.JPanel createbookingpanel5;
    private javax.swing.JPanel createbookingpanel6;
    private javax.swing.JLabel dashboard;
    private javax.swing.JPanel dashpanel;
    private javax.swing.JLabel delete;
    private javax.swing.JPanel deletepanel;
    private javax.swing.JLabel exit;
    private javax.swing.JLabel feedback;
    private javax.swing.JPanel feedbackpanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lg;
    private javax.swing.JPanel listadmin;
    private javax.swing.JLabel mybookings;
    private javax.swing.JScrollPane mybookingscrollpane;
    private javax.swing.JPanel mybookingspanel;
    private javax.swing.JLabel name2;
    private javax.swing.JPanel panelbottom;
    private javax.swing.JComboBox<String> reporttypecombobox;
    private javax.swing.JTextField searchfield;
    private javax.swing.JLabel seereceipt;
    private javax.swing.JPanel seereceiptpanel;
    private javax.swing.JPanel seereceiptpanel1;
    private javax.swing.JPanel servicepanel;
    private javax.swing.JLabel settings;
    private javax.swing.JLabel staff;
    private javax.swing.JLabel update;
    private javax.swing.JPanel updatepanel;
    private javax.swing.JLabel viewservices;
    private javax.swing.JPanel viewservicespanel;
    // End of variables declaration//GEN-END:variables
}
