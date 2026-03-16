/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StaffInternalPage;
import LoginandRegister.LoginForm;
import Session.Session;
import Staff.Feedback;
import Staff.StaffDash;
import config.config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class RatingForm extends javax.swing.JFrame {
 private final config conf = new config();
    private int     bookingId;
    private String  employeeName;
    private Feedback parentFeedback; // refreshed after save
 
    // ── Parameterized constructor — called from Staff/Feedback ────────────────
    public RatingForm(int bookingId, String employeeName, Feedback parent) {
        this.bookingId      = bookingId;
        this.employeeName   = employeeName;
        this.parentFeedback = parent;
        initComponents();
        wireButtonGroup();
        st_label.setText("SUBMIT RATING");
    }
 
    // ── Default constructor (designer) ────────────────────────────────────────
    public RatingForm() {
        initComponents();
        wireButtonGroup();
        st_label.setText("SUBMIT RATING");
    }
 
    // ── Wire all radio buttons into one group so only one is selectable ───────
    private void wireButtonGroup() {
        javax.swing.ButtonGroup group = new javax.swing.ButtonGroup();
        group.add(verygood);
        group.add(good);
        group.add(Bad);
        group.add(verybad);
        verygood.setSelected(true); // default
    }
 
    // ── Get selected rating string ────────────────────────────────────────────
    private String getSelectedRating() {
        if (verygood.isSelected()) return "Very Good";
        if (good.isSelected())     return "Good";
        if (Bad.isSelected())      return "Bad";
        if (verybad.isSelected())  return "Very Bad";
        return "Good"; // fallback
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        verybad = new javax.swing.JRadioButton();
        verygood = new javax.swing.JRadioButton();
        good = new javax.swing.JRadioButton();
        Bad = new javax.swing.JRadioButton();
        rate = new javax.swing.JLabel();
        add = new javax.swing.JPanel();
        st_label = new javax.swing.JLabel();
        ex = new javax.swing.JLabel();
        exit = new javax.swing.JLabel();
        label1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(29, 45, 61));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 234, 234), 3));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(55, 86, 93));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 234, 234), 3));
        jPanel2.setLayout(null);

        verybad.setBackground(new java.awt.Color(29, 45, 61));
        verybad.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        verybad.setForeground(new java.awt.Color(255, 255, 255));
        verybad.setText("Very Bad");
        verybad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verybadActionPerformed(evt);
            }
        });
        jPanel2.add(verybad);
        verybad.setBounds(450, 10, 90, 30);

        verygood.setBackground(new java.awt.Color(29, 45, 61));
        verygood.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        verygood.setForeground(new java.awt.Color(255, 255, 255));
        verygood.setText("Very Good");
        verygood.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verygoodActionPerformed(evt);
            }
        });
        jPanel2.add(verygood);
        verygood.setBounds(90, 10, 90, 30);

        good.setBackground(new java.awt.Color(29, 45, 61));
        good.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        good.setForeground(new java.awt.Color(255, 255, 255));
        good.setText("Good");
        good.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goodActionPerformed(evt);
            }
        });
        jPanel2.add(good);
        good.setBounds(210, 10, 90, 30);

        Bad.setBackground(new java.awt.Color(29, 45, 61));
        Bad.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        Bad.setForeground(new java.awt.Color(255, 255, 255));
        Bad.setText("Bad");
        Bad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BadActionPerformed(evt);
            }
        });
        jPanel2.add(Bad);
        Bad.setBounds(330, 10, 90, 30);

        rate.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        rate.setForeground(new java.awt.Color(239, 234, 234));
        rate.setText("Rate");
        jPanel2.add(rate);
        rate.setBounds(10, 10, 60, 30);

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 590, 50));

        add.setBackground(new java.awt.Color(55, 86, 93));
        add.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        add.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addMouseExited(evt);
            }
        });
        add.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        st_label.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        st_label.setForeground(new java.awt.Color(239, 234, 234));
        st_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        st_label.setText("Label");
        add.add(st_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 100, 20));

        jPanel1.add(add, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 160, 100, 40));

        ex.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        ex.setForeground(new java.awt.Color(239, 234, 234));
        ex.setText("Exit");
        jPanel1.add(ex, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 0, 40, 50));

        exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout (2).png"))); // NOI18N
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitMouseClicked(evt);
            }
        });
        jPanel1.add(exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 0, 40, 50));

        label1.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        label1.setForeground(new java.awt.Color(239, 234, 234));
        label1.setText("Comment");
        jPanel1.add(label1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 140, -1));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 390, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, 220));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMouseClicked
    this.dispose(); 
    }//GEN-LAST:event_exitMouseClicked

    private void addMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseClicked
    String rating  = getSelectedRating();
        String comment = jTextArea1.getText().trim();
        String date    = new java.text.SimpleDateFormat("yyyy-MM-dd")
                             .format(new java.util.Date());
 
        // Insert into tbl_feedback
        String sql = "INSERT INTO tbl_feedback (f_employee, f_rating, f_comment, f_date, f_booking_id) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = config.connectDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, employeeName);
            ps.setString(2, rating);
            ps.setString(3, comment);
            ps.setString(4, date);
            ps.setInt(5, bookingId);
            ps.executeUpdate();
 
            JOptionPane.showMessageDialog(this,
                "Rating submitted!\n\nEmployee: " + employeeName +
                "\nRating: " + rating,
                "Thank You", JOptionPane.INFORMATION_MESSAGE);
 
            // Refresh parent feedback table and close
            if (parentFeedback != null) parentFeedback.loadTable("");
            this.dispose();
 
        } catch (SQLException e) {
            // If f_booking_id column doesn't exist yet, add it and retry
            if (e.getMessage() != null && e.getMessage().contains("no such column")) {
                setupFeedbackTable();
                addMouseClicked(evt); // retry once
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error saving rating: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
 
    // ── Create/fix tbl_feedback schema if needed ──────────────────────────────
    private void setupFeedbackTable() {
        String[] sqls = {
            "CREATE TABLE IF NOT EXISTS tbl_feedback " +
            "(f_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " f_employee TEXT, f_rating TEXT, f_comment TEXT, " +
            " f_date TEXT, f_booking_id INTEGER DEFAULT NULL)",
            "ALTER TABLE tbl_feedback ADD COLUMN f_booking_id INTEGER DEFAULT NULL"
        };
        for (String sql : sqls) {
            try (Connection conn = config.connectDB();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.executeUpdate();
            } catch (SQLException e) {
                // Table/column already exists — safe to ignore
            }
        }
    }//GEN-LAST:event_addMouseClicked

    private void addMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseEntered

    }//GEN-LAST:event_addMouseEntered

    private void addMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseExited

    }//GEN-LAST:event_addMouseExited

    private void verybadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verybadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_verybadActionPerformed

    private void verygoodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verygoodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_verygoodActionPerformed

    private void goodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_goodActionPerformed

    private void BadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BadActionPerformed

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
    private javax.swing.JRadioButton Bad;
    private javax.swing.JPanel add;
    private javax.swing.JLabel ex;
    private javax.swing.JLabel exit;
    private javax.swing.JRadioButton good;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel rate;
    public javax.swing.JLabel st_label;
    private javax.swing.JRadioButton verybad;
    private javax.swing.JRadioButton verygood;
    // End of variables declaration//GEN-END:variables

   
}
