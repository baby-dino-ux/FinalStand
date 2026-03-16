package Admin;
import AdminInternalPage.Profile;
import Admin.Services;
import Admin.Users;
import Admin.Employee;
import Admin.Reports;
import Staff.Feedback;
import Session.Session;
import config.config;
import LoginandRegister.LoginForm;
import javax.swing.JOptionPane;

public class Bookings extends javax.swing.JFrame {

public Bookings() {
    initComponents();
    loadSessionInfo();
    loadStats();

    reporttypecombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{
        "All Bookings",
        "Pending Only",
        "In Progress Only",
        "Done Only",
        "Cancelled Only"
    }));

    reporttypecombobox.addActionListener(e -> filterBookings());
    filterBookings();

    searchfield.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
        public void insertUpdate(javax.swing.event.DocumentEvent e)  { filterBookings(); }
        public void removeUpdate(javax.swing.event.DocumentEvent e)  { filterBookings(); }
        public void changedUpdate(javax.swing.event.DocumentEvent e) { filterBookings(); }
    });

    seereceiptpanel.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) { openReceipt(); }
    });
}

private void loadSessionInfo() {
    Session session = Session.getInstance();
    lblUsername.setText(session.isLoggedIn() ? session.getFullName() : "Admin");
}

private void loadStats() {
    config conf = new config();
    Object total    = conf.getValue("SELECT COUNT(*) FROM tbl_bookings");
    Object done     = conf.getValue("SELECT COUNT(*) FROM tbl_bookings WHERE b_status = 'Done'");
    Object progress = conf.getValue("SELECT COUNT(*) FROM tbl_bookings WHERE b_status = 'In Progress'");
    Object cancelled= conf.getValue("SELECT COUNT(*) FROM tbl_bookings WHERE b_status = 'Cancelled'");
    Object pending  = conf.getValue("SELECT COUNT(*) FROM tbl_bookings WHERE b_status = 'Pending'");
    totalnum.setText    (total     != null ? total.toString()     : "0");
    donenum.setText     (done      != null ? done.toString()      : "0");
    progressnum.setText (progress  != null ? progress.toString()  : "0");
    cancellednum.setText(cancelled != null ? cancelled.toString() : "0");
    pendingnum.setText  (pending   != null ? pending.toString()   : "0");
}

private void filterBookings() {
    String selected = reporttypecombobox.getSelectedItem() != null
                      ? reporttypecombobox.getSelectedItem().toString() : "All Bookings";
    String kw = searchfield.getText().trim();

    String sql;
    switch (selected) {
        case "Pending Only":
            sql = "SELECT b_id AS 'ID', b_customer AS 'Customer', b_service AS 'Service', " +
                  "b_employee AS 'Employee', b_date AS 'Date', b_status AS 'Status' " +
                  "FROM tbl_bookings WHERE b_status = 'Pending'"; break;
        case "In Progress Only":
            sql = "SELECT b_id AS 'ID', b_customer AS 'Customer', b_service AS 'Service', " +
                  "b_employee AS 'Employee', b_date AS 'Date', b_status AS 'Status' " +
                  "FROM tbl_bookings WHERE b_status = 'In Progress'"; break;
        case "Done Only":
            sql = "SELECT b_id AS 'ID', b_customer AS 'Customer', b_service AS 'Service', " +
                  "b_employee AS 'Employee', b_date AS 'Date', b_status AS 'Status' " +
                  "FROM tbl_bookings WHERE b_status = 'Done'"; break;
        case "Cancelled Only":
            sql = "SELECT b_id AS 'ID', b_customer AS 'Customer', b_service AS 'Service', " +
                  "b_employee AS 'Employee', b_date AS 'Date', b_status AS 'Status' " +
                  "FROM tbl_bookings WHERE b_status = 'Cancelled'"; break;
        default:
            sql = "SELECT b_id AS 'ID', b_customer AS 'Customer', b_service AS 'Service', " +
                  "b_employee AS 'Employee', b_date AS 'Date', b_status AS 'Status' " +
                  "FROM tbl_bookings WHERE 1=1"; break;
    }
    if (!kw.isEmpty()) {
        sql += " AND (b_customer LIKE '%" + kw + "%' OR b_service LIKE '%" + kw + "%' " +
               "OR b_employee LIKE '%" + kw + "%' OR b_status LIKE '%" + kw + "%')";
    }
    sql += " ORDER BY b_id DESC";
    new config().displayData(sql, TableBookings);
}

private void openReceipt() {
    int row = TableBookings.getSelectedRow();
    if (row < 0) {
        JOptionPane.showMessageDialog(this, "Please select a booking to view its receipt.",
            "No Selection", JOptionPane.WARNING_MESSAGE); return;
    }
    int    bookingId = Integer.parseInt(TableBookings.getValueAt(row, 0).toString());
    String customer  = TableBookings.getValueAt(row, 1).toString();
    String service   = TableBookings.getValueAt(row, 2).toString();
    String employee  = TableBookings.getValueAt(row, 3).toString();
    String date      = TableBookings.getValueAt(row, 4).toString();
    String address = "", contact = "", staffName = "", taskNote = "";
    double price = 0;
    try (java.sql.Connection conn = config.connectDB();
         java.sql.PreparedStatement ps = conn.prepareStatement(
             "SELECT b_address, b_contact, b_staff, b_price, b_tasknote FROM tbl_bookings WHERE b_id=?")) {
        ps.setInt(1, bookingId);
        try (java.sql.ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                address   = rs.getString("b_address")  != null ? rs.getString("b_address")  : "";
                contact   = rs.getString("b_contact")  != null ? rs.getString("b_contact")  : "";
                staffName = rs.getString("b_staff")    != null ? rs.getString("b_staff")    : "";
                taskNote  = rs.getString("b_tasknote") != null ? rs.getString("b_tasknote") : "";
                price     = rs.getDouble("b_price");
            }
        }
    } catch (java.sql.SQLException e) { System.out.println("Receipt load error: " + e.getMessage()); }
    Staff.GenerateReceipt receipt = new Staff.GenerateReceipt(
        bookingId, date, customer, address, contact,
        employee, service, String.valueOf(price), taskNote, staffName);
    receipt.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    receipt.setVisible(true);
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lg = new javax.swing.JLabel();
        exit = new javax.swing.JLabel();
        Bookingsheader = new javax.swing.JLabel();
        border2 = new javax.swing.JPanel();
        border3 = new javax.swing.JPanel();
        bookingsscrollpane = new javax.swing.JScrollPane();
        TableBookings = new javax.swing.JTable();
        searchfield = new javax.swing.JTextField();
        reporttypecombobox = new javax.swing.JComboBox<>();
        seereceiptpanel = new javax.swing.JPanel();
        seereceipt = new javax.swing.JLabel();
        // stat panels
        black = new javax.swing.JPanel(); totalnum = new javax.swing.JLabel(); total = new javax.swing.JLabel();
        green = new javax.swing.JPanel(); donenum = new javax.swing.JLabel(); done = new javax.swing.JLabel(); donebutton = new javax.swing.JLabel();
        donepanel = new javax.swing.JPanel();
        skyblue = new javax.swing.JPanel(); progressnum = new javax.swing.JLabel(); progress = new javax.swing.JLabel(); progressbutton = new javax.swing.JLabel();
        progresspanel = new javax.swing.JPanel();
        red = new javax.swing.JPanel(); cancellednum = new javax.swing.JLabel(); cancelled = new javax.swing.JLabel(); cancelledbutton = new javax.swing.JLabel();
        cancelledpanel = new javax.swing.JPanel();
        yellow = new javax.swing.JPanel(); pendingnum = new javax.swing.JLabel(); pending = new javax.swing.JLabel(); pendingbutton = new javax.swing.JLabel();
        pendingpanel = new javax.swing.JPanel();
        listadmin = new javax.swing.JPanel();
        settings = new javax.swing.JLabel(); lblUsername = new javax.swing.JLabel(); administrator = new javax.swing.JLabel();
        dashpanel = new javax.swing.JPanel(); dashboard1 = new javax.swing.JLabel();
        userpanel = new javax.swing.JPanel(); users = new javax.swing.JLabel();
        employeepanel = new javax.swing.JPanel(); employee = new javax.swing.JLabel();
        servicespanel = new javax.swing.JPanel(); services = new javax.swing.JLabel();
        bookingspanel = new javax.swing.JPanel(); bookings = new javax.swing.JLabel();
        reportspanel = new javax.swing.JPanel(); reports = new javax.swing.JLabel();
        feedbackpanel = new javax.swing.JPanel(); review = new javax.swing.JLabel(); feedback = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(28, 69, 91));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(29, 45, 61));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        lg.setFont(new java.awt.Font("Bahnschrift", 0, 14)); lg.setForeground(new java.awt.Color(239,234,234)); lg.setText("Log Out");
        jPanel1.add(lg, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, -1, 20));
        exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout (2).png")));
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) { exitMouseClicked(evt); }
        });
        jPanel1.add(exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, 30, 20));
        Bookingsheader.setFont(new java.awt.Font("Bahnschrift", 0, 18)); Bookingsheader.setForeground(new java.awt.Color(239,234,234)); Bookingsheader.setText("Bookings");
        jPanel1.add(Bookingsheader, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 190, 50));
        jPanel4.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 630, 50));

        border2.setBackground(new java.awt.Color(153, 255, 255));
        border2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        border2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(border2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 630, 10));
        border3.setBackground(new java.awt.Color(153, 255, 255));
        border3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(border3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 490));

        // Stat panels — clickable to filter
        black.setBackground(new java.awt.Color(29,45,61)); black.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153,153,153))); black.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        black.addMouseListener(new java.awt.event.MouseAdapter() { public void mouseClicked(java.awt.event.MouseEvent evt) { /* all */ reporttypecombobox.setSelectedItem("All Bookings"); } });
        totalnum.setFont(new java.awt.Font("Bahnschrift",0,24)); totalnum.setForeground(new java.awt.Color(255,255,255)); totalnum.setText("0");
        black.add(totalnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(10,40,60,-1));
        total.setFont(new java.awt.Font("Bahnschrift",0,12)); total.setForeground(new java.awt.Color(255,255,255)); total.setText("Total");
        black.add(total, new org.netbeans.lib.awtextra.AbsoluteConstraints(10,0,80,30));
        jPanel4.add(black, new org.netbeans.lib.awtextra.AbsoluteConstraints(170,70,100,80));

        yellow.setBackground(new java.awt.Color(29,45,61)); yellow.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153,153,153))); yellow.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        pendingpanel.setBackground(new java.awt.Color(29,45,61)); pendingpanel.setLayout(new java.awt.FlowLayout());
        pendingpanel.addMouseListener(new java.awt.event.MouseAdapter() { public void mouseClicked(java.awt.event.MouseEvent evt) { pendingpanelMouseClicked(evt); } });
        pendingnum.setFont(new java.awt.Font("Bahnschrift",0,24)); pendingnum.setForeground(new java.awt.Color(255,255,255)); pendingnum.setText("0");
        yellow.add(pendingnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(10,40,60,-1));
        pending.setFont(new java.awt.Font("Bahnschrift",0,12)); pending.setForeground(new java.awt.Color(255,255,255)); pending.setText("Pending");
        yellow.add(pending, new org.netbeans.lib.awtextra.AbsoluteConstraints(10,0,80,30));
        pendingbutton.setText(""); yellow.add(pendingbutton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        yellow.addMouseListener(new java.awt.event.MouseAdapter() { public void mouseClicked(java.awt.event.MouseEvent evt) { pendingpanelMouseClicked(evt); } });
        jPanel4.add(yellow, new org.netbeans.lib.awtextra.AbsoluteConstraints(280,70,110,80));

        skyblue.setBackground(new java.awt.Color(29,45,61)); skyblue.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153,153,153))); skyblue.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        progresspanel.setBackground(new java.awt.Color(29,45,61)); progresspanel.setLayout(new java.awt.FlowLayout());
        progressnum.setFont(new java.awt.Font("Bahnschrift",0,24)); progressnum.setForeground(new java.awt.Color(255,255,255)); progressnum.setText("0");
        skyblue.add(progressnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(10,40,60,-1));
        progress.setFont(new java.awt.Font("Bahnschrift",0,12)); progress.setForeground(new java.awt.Color(255,255,255)); progress.setText("In Progress");
        skyblue.add(progress, new org.netbeans.lib.awtextra.AbsoluteConstraints(10,0,80,30));
        progressbutton.setText(""); skyblue.add(progressbutton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        skyblue.addMouseListener(new java.awt.event.MouseAdapter() { public void mouseClicked(java.awt.event.MouseEvent evt) { progresspanelMouseClicked(evt); } });
        jPanel4.add(skyblue, new org.netbeans.lib.awtextra.AbsoluteConstraints(400,70,110,80));

        green.setBackground(new java.awt.Color(29,45,61)); green.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153,153,153))); green.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        donepanel.setBackground(new java.awt.Color(29,45,61)); donepanel.setLayout(new java.awt.FlowLayout());
        donenum.setFont(new java.awt.Font("Bahnschrift",0,24)); donenum.setForeground(new java.awt.Color(255,255,255)); donenum.setText("0");
        green.add(donenum, new org.netbeans.lib.awtextra.AbsoluteConstraints(10,40,60,-1));
        done.setFont(new java.awt.Font("Bahnschrift",0,12)); done.setForeground(new java.awt.Color(255,255,255)); done.setText("Done");
        green.add(done, new org.netbeans.lib.awtextra.AbsoluteConstraints(10,0,80,30));
        donebutton.setText(""); green.add(donebutton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        green.addMouseListener(new java.awt.event.MouseAdapter() { public void mouseClicked(java.awt.event.MouseEvent evt) { donepanelMouseClicked(evt); } });
        jPanel4.add(green, new org.netbeans.lib.awtextra.AbsoluteConstraints(520,70,100,80));

        red.setBackground(new java.awt.Color(29,45,61)); red.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153,153,153))); red.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        cancelledpanel.setBackground(new java.awt.Color(29,45,61)); cancelledpanel.setLayout(new java.awt.FlowLayout());
        cancellednum.setFont(new java.awt.Font("Bahnschrift",0,24)); cancellednum.setForeground(new java.awt.Color(255,255,255)); cancellednum.setText("0");
        red.add(cancellednum, new org.netbeans.lib.awtextra.AbsoluteConstraints(10,40,60,-1));
        cancelled.setFont(new java.awt.Font("Bahnschrift",0,12)); cancelled.setForeground(new java.awt.Color(255,255,255)); cancelled.setText("Cancelled");
        red.add(cancelled, new org.netbeans.lib.awtextra.AbsoluteConstraints(10,0,80,30));
        cancelledbutton.setText(""); red.add(cancelledbutton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,0,0));
        red.addMouseListener(new java.awt.event.MouseAdapter() { public void mouseClicked(java.awt.event.MouseEvent evt) { cancelledpanelMouseClicked(evt); } });
        jPanel4.add(red, new org.netbeans.lib.awtextra.AbsoluteConstraints(630,70,110,80));

        // Search, filter, see receipt
        searchfield.setFont(new java.awt.Font("Bahnschrift",0,11)); searchfield.setText("Search ");
        searchfield.addActionListener(new java.awt.event.ActionListener() { public void actionPerformed(java.awt.event.ActionEvent evt) { searchfieldActionPerformed(evt); } });
        jPanel4.add(searchfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(170,160,300,20));

        reporttypecombobox.setFont(new java.awt.Font("Bahnschrift",0,12));
        reporttypecombobox.addActionListener(new java.awt.event.ActionListener() { public void actionPerformed(java.awt.event.ActionEvent evt) { reporttypecomboboxActionPerformed(evt); } });
        jPanel4.add(reporttypecombobox, new org.netbeans.lib.awtextra.AbsoluteConstraints(480,160,130,20));

        seereceiptpanel.setBackground(new java.awt.Color(29,45,61)); seereceiptpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        seereceipt.setFont(new java.awt.Font("Bahnschrift",0,14)); seereceipt.setForeground(new java.awt.Color(255,255,255)); seereceipt.setText("See Receipt");
        seereceiptpanel.add(seereceipt, new org.netbeans.lib.awtextra.AbsoluteConstraints(10,0,90,30));
        jPanel4.add(seereceiptpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(620,160,110,30));

        // Table
        TableBookings.setBackground(new java.awt.Color(29,45,61)); TableBookings.setFont(new java.awt.Font("Bahnschrift",1,12)); TableBookings.setForeground(new java.awt.Color(255,255,255));
        TableBookings.setModel(new javax.swing.table.DefaultTableModel(new Object[][]{}, new String[]{"ID","Customer","Service","Employee","Date","Status"}));
        bookingsscrollpane.setViewportView(TableBookings);
        jPanel4.add(bookingsscrollpane, new org.netbeans.lib.awtextra.AbsoluteConstraints(170,200,610,270));

        // Sidebar
        listadmin.setBackground(new java.awt.Color(55,86,93)); listadmin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        settings.setBackground(new java.awt.Color(255,255,255)); settings.setFont(new java.awt.Font("Arial Black",1,14));
        settings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user (1).png"))); settings.setText("ACCOUNT");
        settings.addMouseListener(new java.awt.event.MouseAdapter() { public void mouseClicked(java.awt.event.MouseEvent evt) { settingsMouseClicked(evt); } });
        listadmin.add(settings, new org.netbeans.lib.awtextra.AbsoluteConstraints(-220,10,280,50));
        lblUsername.setFont(new java.awt.Font("Century Gothic",1,12)); lblUsername.setText("Admin");
        listadmin.add(lblUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(60,20,90,20));

        dashpanel.setBackground(new java.awt.Color(29,45,61)); dashpanel.addMouseListener(new java.awt.event.MouseAdapter() { public void mouseClicked(java.awt.event.MouseEvent evt) { dashpanelMouseClicked(evt); } }); dashpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        dashboard1.setFont(new java.awt.Font("Bahnschrift",0,14)); dashboard1.setForeground(new java.awt.Color(239,234,234)); dashboard1.setText("Dashboard");
        dashpanel.add(dashboard1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40,10,70,20));
        listadmin.add(dashpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,70,150,40));

        userpanel.setBackground(new java.awt.Color(29,45,61)); userpanel.addMouseListener(new java.awt.event.MouseAdapter() { public void mouseClicked(java.awt.event.MouseEvent evt) { userpanelMouseClicked(evt); } }); userpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        users.setFont(new java.awt.Font("Bahnschrift",0,14)); users.setForeground(new java.awt.Color(239,234,234)); users.setText("Users");
        userpanel.add(users, new org.netbeans.lib.awtextra.AbsoluteConstraints(40,10,70,-1));
        listadmin.add(userpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,110,150,40));

        employeepanel.setBackground(new java.awt.Color(29,45,61)); employeepanel.addMouseListener(new java.awt.event.MouseAdapter() { public void mouseClicked(java.awt.event.MouseEvent evt) { employeepanelMouseClicked(evt); } }); employeepanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        employee.setFont(new java.awt.Font("Bahnschrift",0,14)); employee.setForeground(new java.awt.Color(239,234,234)); employee.setText("Employee");
        employeepanel.add(employee, new org.netbeans.lib.awtextra.AbsoluteConstraints(40,10,100,-1));
        listadmin.add(employeepanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,150,150,40));

        servicespanel.setBackground(new java.awt.Color(29,45,61)); servicespanel.addMouseListener(new java.awt.event.MouseAdapter() { public void mouseClicked(java.awt.event.MouseEvent evt) { servicespanelMouseClicked(evt); } }); servicespanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        services.setFont(new java.awt.Font("Bahnschrift",0,14)); services.setForeground(new java.awt.Color(239,234,234)); services.setText("Services");
        servicespanel.add(services, new org.netbeans.lib.awtextra.AbsoluteConstraints(40,10,110,-1));
        listadmin.add(servicespanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,190,150,40));

        bookingspanel.setBackground(new java.awt.Color(29,45,61)); bookingspanel.addMouseListener(new java.awt.event.MouseAdapter() { public void mouseClicked(java.awt.event.MouseEvent evt) { bookingspanelMouseClicked(evt); } }); bookingspanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        bookings.setFont(new java.awt.Font("Bahnschrift",0,14)); bookings.setForeground(new java.awt.Color(239,234,234)); bookings.setText("Bookings");
        bookingspanel.add(bookings, new org.netbeans.lib.awtextra.AbsoluteConstraints(40,10,60,-1));
        listadmin.add(bookingspanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,230,150,40));

        reportspanel.setBackground(new java.awt.Color(29,45,61)); reportspanel.addMouseListener(new java.awt.event.MouseAdapter() { public void mouseClicked(java.awt.event.MouseEvent evt) { reportspanelMouseClicked(evt); } }); reportspanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        reports.setFont(new java.awt.Font("Bahnschrift",0,14)); reports.setForeground(new java.awt.Color(239,234,234)); reports.setText("Reports");
        reportspanel.add(reports, new org.netbeans.lib.awtextra.AbsoluteConstraints(40,10,-1,-1));
        listadmin.add(reportspanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,270,150,40));

        feedbackpanel.setBackground(new java.awt.Color(29,45,61)); feedbackpanel.addMouseListener(new java.awt.event.MouseAdapter() { public void mouseClicked(java.awt.event.MouseEvent evt) { feedbackpanelMouseClicked(evt); } }); feedbackpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        review.setFont(new java.awt.Font("Arial Black",1,14));
        feedbackpanel.add(review, new org.netbeans.lib.awtextra.AbsoluteConstraints(50,10,90,-1));
        feedback.setFont(new java.awt.Font("Bahnschrift",0,14)); feedback.setForeground(new java.awt.Color(239,234,234)); feedback.setText("Feedback");
        feedbackpanel.add(feedback, new org.netbeans.lib.awtextra.AbsoluteConstraints(40,10,80,-1));
        listadmin.add(feedbackpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,310,150,40));

        administrator.setFont(new java.awt.Font("Century Gothic",1,10)); administrator.setForeground(new java.awt.Color(153,255,255)); administrator.setText("ADMINISTRATOR");
        listadmin.add(administrator, new org.netbeans.lib.awtextra.AbsoluteConstraints(60,40,90,20));

        jPanel4.add(listadmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10,0,150,-1));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,790,490));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void pendingpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pendingpanelMouseClicked
        reporttypecombobox.setSelectedItem("Pending Only");
    }//GEN-LAST:event_pendingpanelMouseClicked

    private void progresspanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_progresspanelMouseClicked
        reporttypecombobox.setSelectedItem("In Progress Only");
    }//GEN-LAST:event_progresspanelMouseClicked

    private void donepanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_donepanelMouseClicked
        reporttypecombobox.setSelectedItem("Done Only");
    }//GEN-LAST:event_donepanelMouseClicked

    private void cancelledpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelledpanelMouseClicked
        reporttypecombobox.setSelectedItem("Cancelled Only");
    }//GEN-LAST:event_cancelledpanelMouseClicked

    private void reporttypecomboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reporttypecomboboxActionPerformed
        filterBookings();
    }//GEN-LAST:event_reporttypecomboboxActionPerformed

    private void searchfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchfieldActionPerformed
        filterBookings();
    }//GEN-LAST:event_searchfieldActionPerformed

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
    private void blackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_blackMouseClicked
    }//GEN-LAST:event_blackMouseClicked

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
    private javax.swing.JComboBox<String> reporttypecombobox;
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
