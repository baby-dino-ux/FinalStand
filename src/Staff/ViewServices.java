/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Staff;

import AdminInternalPage.Profile;
import LoginandRegister.LoginForm;
import Session.Session;
import config.config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
 

/**
 *
 * @author ashlaran
 */
public class ViewServices extends javax.swing.JFrame {
 public ViewServices() {
        initComponents();
        loadSessionInfo();   // show username in sidebar
        loadTable("");       // populate the table from DB on open
        wireSearch();        // live-filter while typing
        wireRefresh();       // refresh button wired
    }
 
    // ── Show logged-in staff name in sidebar ─────────────────────────────────
    private void loadSessionInfo() {
        Session s = Session.getInstance();
        lblUsername.setText(s.isLoggedIn() ? s.getUsername() : "Staff");
    }
 
    // ── Load services table from tbl_services ─────────────────────────────────
    public void loadTable(String keyword) {
        DefaultTableModel model = (DefaultTableModel) TableViewServices.getModel();
        model.setRowCount(0); // clear existing rows

        String sql;
        if (keyword == null || keyword.trim().isEmpty()) {
            sql = "SELECT s_id, s_name, s_price, s_description, s_status FROM tbl_services ORDER BY s_id DESC";
        } else {
            sql = "SELECT s_id, s_name, s_price, s_description, s_status FROM tbl_services " +
                  "WHERE s_name LIKE ? ORDER BY s_id DESC";
        }

        try (Connection conn = config.connectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(1, "%" + keyword.trim() + "%");
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("s_id"),
                        rs.getString("s_name"),
                        rs.getDouble("s_price"),
                        rs.getString("s_description"),
                        rs.getString("s_status")
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Error loading services: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    // ── Wire search field to filter table as user types ───────────────────────
    private void wireSearch() {
        searchfield.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e)  { doSearch(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e)  { doSearch(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { doSearch(); }
            private void doSearch() {
                String text = searchfield.getText().trim();
                // ignore placeholder text
                if (!text.equals("Search Services")) loadTable(text);
            }
        });
 
        // Clear placeholder on focus
        searchfield.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (searchfield.getText().equals("Search Services")) {
                    searchfield.setText("");
                    searchfield.setForeground(java.awt.Color.WHITE);
                }
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (searchfield.getText().isEmpty()) {
                    searchfield.setText("Search Services");
                    searchfield.setForeground(new java.awt.Color(102, 102, 102));
                    loadTable("");
                }
            }
        });
    }
 
    // ── Wire refresh button to reload all services ────────────────────────────
    private void wireRefresh() {
        refreshlistpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchfield.setText("Search Services");
                searchfield.setForeground(new java.awt.Color(102, 102, 102));
                loadTable("");
            }
        });
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jPanel4 = new javax.swing.JPanel();
        panelbottom = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lg = new javax.swing.JLabel();
        exit = new javax.swing.JLabel();
        viewservicesheader = new javax.swing.JLabel();
        border1 = new javax.swing.JPanel();
        border2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        searchfield = new javax.swing.JTextField();
        viewservicesscrollpane = new javax.swing.JScrollPane();
        TableViewServices = new javax.swing.JTable();
        refreshlistpanel = new javax.swing.JPanel();
        refreshlist = new javax.swing.JLabel();
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
        border = new javax.swing.JPanel();

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

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

        viewservicesheader.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        viewservicesheader.setForeground(new java.awt.Color(255, 255, 255));
        viewservicesheader.setText("View Services");
        jPanel1.add(viewservicesheader, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 130, 50));

        jPanel4.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 0, 650, 50));

        border1.setBackground(new java.awt.Color(29, 45, 61));
        border1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                border1MouseClicked(evt);
            }
        });
        border1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(border1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 130, 180));

        border2.setBackground(new java.awt.Color(153, 255, 255));
        border2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        border2.setToolTipText("");
        border2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                border2MouseClicked(evt);
            }
        });
        border2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(border2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 650, 10));

        jPanel7.setBackground(new java.awt.Color(29, 45, 61));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        searchfield.setFont(new java.awt.Font("Bahnschrift", 0, 11)); // NOI18N
        searchfield.setForeground(new java.awt.Color(102, 102, 102));
        searchfield.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        searchfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchfieldActionPerformed(evt);
            }
        });
        jPanel7.add(searchfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 600, 20));

        viewservicesscrollpane.setBackground(new java.awt.Color(29, 45, 61));
        viewservicesscrollpane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        viewservicesscrollpane.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N

        TableViewServices.setBackground(new java.awt.Color(29, 45, 61));
        TableViewServices.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        TableViewServices.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        TableViewServices.setForeground(new java.awt.Color(255, 255, 255));
        TableViewServices.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Service ID", "Service Name", "Price", "Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        TableViewServices.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        viewservicesscrollpane.setViewportView(TableViewServices);

        jPanel7.add(viewservicesscrollpane, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 600, 300));

        jPanel4.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, 620, 360));

        refreshlistpanel.setBackground(new java.awt.Color(29, 45, 61));
        refreshlistpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        refreshlist.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        refreshlist.setForeground(new java.awt.Color(255, 255, 255));
        refreshlist.setText("Refresh List");
        refreshlistpanel.add(refreshlist, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 90, 20));

        jPanel4.add(refreshlistpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 70, -1, -1));

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

        jPanel4.add(listadmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 130, 340));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 1, 780, 490));

        border.setBackground(new java.awt.Color(153, 255, 255));
        border.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                borderMouseClicked(evt);
            }
        });
        border.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(border, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 510));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void panelbottomMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelbottomMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelbottomMouseClicked

    private void exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMouseClicked
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to log out?",
            "Log Out", JOptionPane.YES_NO_OPTION);
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

    private void searchfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchfieldActionPerformed
          String text = searchfield.getText().trim();
        if (!text.equals("Search Services")) loadTable(text);
    }//GEN-LAST:event_searchfieldActionPerformed

    private void borderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_borderMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_borderMouseClicked

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
                "Please login first!", "Unauthorized Access", JOptionPane.WARNING_MESSAGE);
            java.awt.EventQueue.invokeLater(() -> new LoginForm().setVisible(true));
            return;
        }
        java.awt.EventQueue.invokeLater(() -> new StaffDash().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TableViewServices;
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
    private javax.swing.JLabel exit;
    private javax.swing.JLabel feedback;
    private javax.swing.JPanel feedbackpanel;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lg;
    private javax.swing.JPanel listadmin;
    private javax.swing.JLabel mybookings;
    private javax.swing.JPanel mybookingspanel;
    private javax.swing.JPanel panelbottom;
    private javax.swing.JLabel refreshlist;
    private javax.swing.JPanel refreshlistpanel;
    private javax.swing.JTextField searchfield;
    private javax.swing.JPanel servicepanel;
    private javax.swing.JLabel settings;
    private javax.swing.JLabel staff;
    private javax.swing.JLabel viewservices;
    private javax.swing.JLabel viewservicesheader;
    private javax.swing.JPanel viewservicespanel;
    private javax.swing.JScrollPane viewservicesscrollpane;
    // End of variables declaration//GEN-END:variables
}
