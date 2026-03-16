package Employee;

import AdminInternalPage.Profile;
import LoginandRegister.LoginForm;
import Session.Session;
import Staff.GenerateReceipt;
import config.config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class MyTasks extends javax.swing.JFrame {

    private final config conf = new config();

    public MyTasks() {
        initComponents();
        loadSessionInfo();
        wireStatusBox();

        // ── Populate the filter combobox ──────────────────────────────────────
        reporttypecombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{
            "All My Tasks",
            "Pending Only",
            "In Progress Only",
            "Done Only",
            "Cancelled Only"
        }));

        // ── When user picks an option → table updates immediately ─────────────
        reporttypecombobox.addActionListener(e -> filterTasks());

        // ── Load all on open ──────────────────────────────────────────────────
        filterTasks();

        // ── Wire refresh ──────────────────────────────────────────────────────
        refreshlistpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchfield.setText("");
                reporttypecombobox.setSelectedIndex(0);
            }
        });

        // ── Wire see receipt ──────────────────────────────────────────────────
        seereceiptpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openReceipt();
            }
        });

        // ── Wire live search ──────────────────────────────────────────────────
        searchfield.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e)  { filterTasks(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e)  { filterTasks(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filterTasks(); }
        });
    }

    private void loadSessionInfo() {
        Session session = Session.getInstance();
        lblUsername.setText(session.isLoggedIn() ? session.getFullName() : "Employee");
    }

    private void wireStatusBox() {
        String myName = Session.getInstance().getFullName();
        String current = "Available";
        String sql = "SELECT work_status FROM tbl_users WHERE (firstname||' '||lastname)=? AND type='Employee'";
        try (Connection conn = config.connectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, myName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String ws = rs.getString("work_status");
                    if (ws != null && !ws.isEmpty()) current = ws;
                }
            }
        } catch (SQLException e) { /* ignore */ }

        for (java.awt.event.ActionListener al : StatusBox.getActionListeners())
            StatusBox.removeActionListener(al);

        String[] statuses = {"Available", "Busy", "Off Duty"};
        StatusBox.setModel(new javax.swing.DefaultComboBoxModel<>(statuses));
        StatusBox.setSelectedItem(current);
        StatusBox.addActionListener(e ->
            conf.updateRecord(
                "UPDATE tbl_users SET work_status=? WHERE (firstname||' '||lastname)=? AND type='Employee'",
                StatusBox.getSelectedItem().toString(), myName));
    }

    // ── Main filter method ────────────────────────────────────────────────────
    private void filterTasks() {
        String selected = reporttypecombobox.getSelectedItem() != null
                          ? reporttypecombobox.getSelectedItem().toString() : "All My Tasks";
        String kw     = searchfield.getText().trim();
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
                     "b_date AS 'Date', b_tasknote AS 'Task Note', b_status AS 'Status' " +
                     "FROM tbl_bookings WHERE b_employee = ?" + statusClause;
        if (!kw.isEmpty() && !kw.equals("Search ")) {
            String k = kw.replace("'", "''");
            sql += " AND (b_customer LIKE '%" + k + "%' OR b_service LIKE '%" + k + "%' " +
                   "OR b_status LIKE '%" + k + "%')";
        }
        sql += " ORDER BY b_id DESC";
        conf.displayData(sql, TableMytask, myName);
    }

    private int getSelectedBookingId() {
        int row = TableMytask.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a task from the table first.",
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return -1;
        }
        return Integer.parseInt(TableMytask.getValueAt(row, 0).toString());
    }

    private void changeStatus(String newStatus) {
        int id = getSelectedBookingId();
        if (id < 0) return;
        String currentBookingStatus = TableMytask.getValueAt(TableMytask.getSelectedRow(), 5).toString();
        String employeeName = Session.getInstance().getFullName();

        if ("Confirmed".equals(newStatus)) {
            if (!"Pending".equalsIgnoreCase(currentBookingStatus)) {
                JOptionPane.showMessageDialog(this,
                    "You can only confirm a Pending booking.\nThis booking is: " + currentBookingStatus,
                    "Not Allowed", JOptionPane.WARNING_MESSAGE); return;
            }
            conf.updateRecord("UPDATE tbl_bookings SET b_status='In Progress' WHERE b_id=?", id);
            conf.updateRecord("UPDATE tbl_users SET work_status='Busy' WHERE (firstname||' '||lastname)=? AND type='Employee'", employeeName);
            JOptionPane.showMessageDialog(this, "Booking accepted!\nStatus set to In Progress.\nYour work status is now Busy.", "Accepted", JOptionPane.INFORMATION_MESSAGE);
            wireStatusBox(); filterTasks(); return;
        }
        if ("In Progress".equals(newStatus)) {
            if (!"Confirmed".equalsIgnoreCase(currentBookingStatus) && !"Pending".equalsIgnoreCase(currentBookingStatus)) {
                JOptionPane.showMessageDialog(this, "Booking must be Confirmed before setting In Progress.", "Not Allowed", JOptionPane.WARNING_MESSAGE); return;
            }
            conf.updateRecord("UPDATE tbl_bookings SET b_status='In Progress' WHERE b_id=?", id);
            conf.updateRecord("UPDATE tbl_users SET work_status='Busy' WHERE (firstname||' '||lastname)=? AND type='Employee'", employeeName);
            JOptionPane.showMessageDialog(this, "Booking set to In Progress.", "Updated", JOptionPane.INFORMATION_MESSAGE);
            wireStatusBox(); filterTasks(); return;
        }
        if ("Done".equals(newStatus)) {
            if (!"In Progress".equalsIgnoreCase(currentBookingStatus) && !"Confirmed".equalsIgnoreCase(currentBookingStatus)) {
                JOptionPane.showMessageDialog(this, "Booking must be In Progress before marking as Done.", "Not Allowed", JOptionPane.WARNING_MESSAGE); return;
            }
            conf.updateRecord("UPDATE tbl_bookings SET b_status='Done' WHERE b_id=?", id);
            conf.updateRecord("UPDATE tbl_users SET work_status='Available' WHERE (firstname||' '||lastname)=? AND type='Employee'", employeeName);
            JOptionPane.showMessageDialog(this, "Booking marked as Done!\nYour work status is now Available.", "Done", JOptionPane.INFORMATION_MESSAGE);
            wireStatusBox(); filterTasks(); return;
        }
        if ("Cancelled".equals(newStatus)) {
            if ("Done".equalsIgnoreCase(currentBookingStatus)) {
                JOptionPane.showMessageDialog(this, "Cannot cancel a completed booking.", "Not Allowed", JOptionPane.WARNING_MESSAGE); return;
            }
            int c = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel this booking?", "Confirm Cancel", JOptionPane.YES_NO_OPTION);
            if (c != JOptionPane.YES_OPTION) return;
            conf.updateRecord("UPDATE tbl_bookings SET b_status='Cancelled' WHERE b_id=?", id);
            conf.updateRecord("UPDATE tbl_users SET work_status='Available' WHERE (firstname||' '||lastname)=? AND type='Employee'", employeeName);
            JOptionPane.showMessageDialog(this, "Booking cancelled.\nYour work status is now Available.", "Cancelled", JOptionPane.INFORMATION_MESSAGE);
            wireStatusBox(); filterTasks(); return;
        }
        conf.updateRecord("UPDATE tbl_bookings SET b_status=? WHERE b_id=?", newStatus, id);
        filterTasks();
    }

    private void openReceipt() {
        int row = TableMytask.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a task to view its receipt.", "No Selection", JOptionPane.WARNING_MESSAGE); return;
        }
        int    bookingId = Integer.parseInt(TableMytask.getValueAt(row, 0).toString());
        String customer  = TableMytask.getValueAt(row, 1).toString();
        String service   = TableMytask.getValueAt(row, 2).toString();
        String date      = TableMytask.getValueAt(row, 3).toString();
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
            Session.getInstance().getFullName(), service, String.valueOf(price), taskNote, staffName);
        receipt.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        receipt.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        seereceiptpanel = new javax.swing.JPanel();
        seereceipt = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lg = new javax.swing.JLabel();
        exit = new javax.swing.JLabel();
        logout1 = new javax.swing.JLabel();
        mytasksheader = new javax.swing.JLabel();
        yellow = new javax.swing.JPanel();
        progress = new javax.swing.JLabel();
        black = new javax.swing.JPanel();
        confirm = new javax.swing.JLabel();
        skyblue = new javax.swing.JPanel();
        done = new javax.swing.JLabel();
        red = new javax.swing.JPanel();
        cancelled = new javax.swing.JLabel();
        border = new javax.swing.JPanel();
        border3 = new javax.swing.JPanel();
        mytasksscrollpane = new javax.swing.JScrollPane();
        TableMytask = new javax.swing.JTable();
        searchfield = new javax.swing.JTextField();
        refreshlistpanel = new javax.swing.JPanel();
        refreshlist = new javax.swing.JLabel();
        list = new javax.swing.JPanel();
        settings = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        dashpanel = new javax.swing.JPanel();
        dashboard = new javax.swing.JLabel();
        mytaskspanel = new javax.swing.JPanel();
        mytasks = new javax.swing.JLabel();
        memployeepanel = new javax.swing.JPanel();
        border1 = new javax.swing.JPanel();
        review1 = new javax.swing.JLabel();
        mystatuspanel = new javax.swing.JPanel();
        StatusBox = new javax.swing.JComboBox<>();
        mystatus = new javax.swing.JLabel();
        Employee = new javax.swing.JLabel();
        reporttypecombobox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(28, 69, 91));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        seereceiptpanel.setBackground(new java.awt.Color(29, 45, 61));
        seereceiptpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        seereceipt.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        seereceipt.setForeground(new java.awt.Color(255, 255, 255));
        seereceipt.setText("See Receipt");
        seereceiptpanel.add(seereceipt, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 90, 30));

        jPanel4.add(seereceiptpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 60, -1, 30));

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

        mytasksheader.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        mytasksheader.setForeground(new java.awt.Color(239, 234, 234));
        mytasksheader.setText("My Tasks");
        jPanel1.add(mytasksheader, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 180, 50));

        jPanel4.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 640, 50));

        yellow.setBackground(new java.awt.Color(153, 153, 0));
        yellow.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        yellow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                yellowMouseClicked(evt);
            }
        });

        progress.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        progress.setForeground(new java.awt.Color(255, 204, 102));
        progress.setText("PROGRESS");
        yellow.add(progress);

        jPanel4.add(yellow, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 100, 70, 20));

        black.setBackground(new java.awt.Color(29, 45, 61));
        black.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        black.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                blackMouseClicked(evt);
            }
        });

        confirm.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        confirm.setForeground(new java.awt.Color(239, 234, 234));
        confirm.setText("CONFIRM");
        black.add(confirm);

        jPanel4.add(black, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 100, 70, 20));

        skyblue.setBackground(new java.awt.Color(0, 153, 153));
        skyblue.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        skyblue.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                skyblueMouseClicked(evt);
            }
        });

        done.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        done.setForeground(new java.awt.Color(0, 255, 255));
        done.setText("DONE");
        skyblue.add(done);

        jPanel4.add(skyblue, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 100, 60, 20));

        red.setBackground(new java.awt.Color(153, 0, 51));
        red.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        red.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                redMouseClicked(evt);
            }
        });

        cancelled.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        cancelled.setForeground(new java.awt.Color(255, 102, 102));
        cancelled.setText("CANCELLED");
        red.add(cancelled);

        jPanel4.add(red, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 100, 80, 20));

        border.setBackground(new java.awt.Color(153, 255, 255));
        border.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        border.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                borderMouseClicked(evt);
            }
        });
        border.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(border, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 630, 10));

        border3.setBackground(new java.awt.Color(153, 255, 255));
        border3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                border3MouseClicked(evt);
            }
        });
        border3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(border3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 490));

        mytasksscrollpane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        TableMytask.setBackground(new java.awt.Color(29, 45, 61));
        TableMytask.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        TableMytask.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        TableMytask.setForeground(new java.awt.Color(255, 255, 255));
        TableMytask.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Booking ID", "Customer Name", "Service", "Address", "Date", "Task Note", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        mytasksscrollpane.setViewportView(TableMytask);

        jPanel4.add(mytasksscrollpane, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 130, 610, 350));

        searchfield.setBackground(new java.awt.Color(29, 45, 61));
        searchfield.setFont(new java.awt.Font("Bahnschrift", 0, 11)); // NOI18N
        searchfield.setForeground(new java.awt.Color(255, 255, 255));
        searchfield.setText("Search ");
        searchfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchfieldActionPerformed(evt);
            }
        });
        jPanel4.add(searchfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 100, 320, 20));

        refreshlistpanel.setBackground(new java.awt.Color(29, 45, 61));
        refreshlistpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        refreshlist.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        refreshlist.setForeground(new java.awt.Color(255, 255, 255));
        refreshlist.setText("Refresh List");
        refreshlistpanel.add(refreshlist, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 90, 30));

        jPanel4.add(refreshlistpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 60, -1, 30));

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

        border1.setBackground(new java.awt.Color(29, 45, 61));
        border1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        review1.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        border1.add(review1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 90, -1));

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

        border1.add(mystatuspanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 130, 60));

        list.add(border1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 150, 340));

        Employee.setBackground(new java.awt.Color(153, 255, 255));
        Employee.setFont(new java.awt.Font("Century Gothic", 1, 10)); // NOI18N
        Employee.setForeground(new java.awt.Color(153, 255, 255));
        Employee.setText("Employee");
        list.add(Employee, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 90, 20));

        jPanel4.add(list, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 150, 490));

        reporttypecombobox.setBackground(new java.awt.Color(29, 45, 61));
        reporttypecombobox.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        reporttypecombobox.setForeground(new java.awt.Color(29, 45, 61));
        reporttypecombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        reporttypecombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reporttypecomboboxActionPerformed(evt);
            }
        });
        jPanel4.add(reporttypecombobox, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 60, 100, 20));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 490));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMouseClicked
        int c = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);
        if (c == JOptionPane.YES_OPTION) { Session.getInstance().logout(); new LoginForm().setVisible(true); this.dispose(); }
    }//GEN-LAST:event_exitMouseClicked

    private void yellowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_yellowMouseClicked
        changeStatus("In Progress");
    }//GEN-LAST:event_yellowMouseClicked

    private void blackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_blackMouseClicked
        changeStatus("Confirmed");
    }//GEN-LAST:event_blackMouseClicked

    private void skyblueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_skyblueMouseClicked
        changeStatus("Done");
    }//GEN-LAST:event_skyblueMouseClicked

    private void redMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_redMouseClicked
        changeStatus("Cancelled");
    }//GEN-LAST:event_redMouseClicked

    private void borderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_borderMouseClicked
    }//GEN-LAST:event_borderMouseClicked

    private void border3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_border3MouseClicked
    }//GEN-LAST:event_border3MouseClicked

    private void searchfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchfieldActionPerformed
        filterTasks();
    }//GEN-LAST:event_searchfieldActionPerformed

    private void settingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsMouseClicked
        new Profile().setVisible(true); this.dispose();
    }//GEN-LAST:event_settingsMouseClicked

    private void mytaskspanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mytaskspanelMouseClicked
        filterTasks();
    }//GEN-LAST:event_mytaskspanelMouseClicked

    private void dashpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashpanelMouseClicked
        new EmployeeDash().setVisible(true); this.dispose();
    }//GEN-LAST:event_dashpanelMouseClicked

    private void reporttypecomboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reporttypecomboboxActionPerformed
        filterTasks();
    }//GEN-LAST:event_reporttypecomboboxActionPerformed

    public static void main(String args[]) {
        Session session = Session.getInstance();
        if (!session.isLoggedIn()) {
            JOptionPane.showMessageDialog(null, "Please login first!", "Unauthorized Access", JOptionPane.WARNING_MESSAGE);
            java.awt.EventQueue.invokeLater(() -> new LoginForm().setVisible(true)); return;
        }
        java.awt.EventQueue.invokeLater(() -> new EmployeeDash().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Employee;
    private javax.swing.JComboBox<String> StatusBox;
    private javax.swing.JTable TableMytask;
    private javax.swing.JPanel black;
    private javax.swing.JPanel border;
    private javax.swing.JPanel border1;
    private javax.swing.JPanel border3;
    private javax.swing.JLabel cancelled;
    private javax.swing.JLabel confirm;
    private javax.swing.JLabel dashboard;
    private javax.swing.JPanel dashpanel;
    private javax.swing.JLabel done;
    private javax.swing.JLabel exit;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lg;
    private javax.swing.JPanel list;
    private javax.swing.JLabel logout1;
    private javax.swing.JPanel memployeepanel;
    private javax.swing.JLabel mystatus;
    private javax.swing.JPanel mystatuspanel;
    private javax.swing.JLabel mytasks;
    private javax.swing.JLabel mytasksheader;
    private javax.swing.JPanel mytaskspanel;
    private javax.swing.JScrollPane mytasksscrollpane;
    private javax.swing.JLabel progress;
    private javax.swing.JPanel red;
    private javax.swing.JLabel refreshlist;
    private javax.swing.JPanel refreshlistpanel;
    private javax.swing.JComboBox<String> reporttypecombobox;
    private javax.swing.JLabel review1;
    private javax.swing.JTextField searchfield;
    private javax.swing.JLabel seereceipt;
    private javax.swing.JPanel seereceiptpanel;
    private javax.swing.JLabel settings;
    private javax.swing.JPanel skyblue;
    private javax.swing.JPanel yellow;
    // End of variables declaration//GEN-END:variables
}
