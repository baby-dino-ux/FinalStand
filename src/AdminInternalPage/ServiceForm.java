/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AdminInternalPage;


import Admin.Services;
import LoginandRegister.LoginForm;
import Session.Session;
import config.config;
import javax.swing.JOptionPane;
public class ServiceForm extends javax.swing.JFrame {
 private final config conf = new config();
    private int     serviceId;
    private Services parentServices;

    /**
     * @param id     0 = Add mode,  >0 = Edit mode
     * @param name   pre-fill name  (Edit)
     * @param priceV pre-fill price (Edit)
     * @param parent Services window — refreshed after save
     */
    public ServiceForm(int id, String name, int priceV, Services parent) {
        this(id, name, priceV, "Active", parent); // default status Active
    }

    public ServiceForm(int id, String name, int priceV, String statusV, Services parent) {
        this.serviceId      = id;
        this.parentServices = parent;
        initComponents();

        // Wire the radio buttons into a ButtonGroup so only one can be selected
        javax.swing.ButtonGroup statusGroup = new javax.swing.ButtonGroup();
        statusGroup.add(active);
        statusGroup.add(inactive);

        // Enable fields so user can type
        servicenamefield.setEnabled(true);
        pricefield.setEnabled(true);
        idfield.setEnabled(false); // ID is always read-only

        if (id > 0) {
            // EDIT mode — pre-fill all fields including status
            label1.setText("Edit Service");
            st_label.setText("Update");
            idfield.setText(String.valueOf(id));
            servicenamefield.setText(name);
            pricefield.setText(String.valueOf(priceV));
            if ("Inactive".equalsIgnoreCase(statusV)) {
                inactive.setSelected(true);
            } else {
                active.setSelected(true);
            }
        } else {
            // ADD mode — default to Active
            label1.setText("Add Services");
            st_label.setText("Save");
            idfield.setText("Auto");
            active.setSelected(true);
        }
    }

    /** No-arg constructor for NetBeans designer */
    public ServiceForm() {
        this(0, "", 0, "Active", null);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        label1 = new javax.swing.JLabel();
        ex = new javax.swing.JLabel();
        exit = new javax.swing.JLabel();
        add = new javax.swing.JPanel();
        st_label = new javax.swing.JLabel();
        servicename = new javax.swing.JLabel();
        status = new javax.swing.JLabel();
        pricefield = new javax.swing.JTextField();
        servicenamefield = new javax.swing.JTextField();
        serviceid = new javax.swing.JLabel();
        idfield = new javax.swing.JTextField();
        price1 = new javax.swing.JLabel();
        inactive = new javax.swing.JRadioButton();
        active = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(29, 45, 61));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 234, 234), 3));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(55, 86, 93));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 234, 234), 3));
        jPanel2.setLayout(null);

        label1.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        label1.setForeground(new java.awt.Color(239, 234, 234));
        label1.setText(" Services");
        jPanel2.add(label1);
        label1.setBounds(0, 0, 300, 40);

        ex.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        ex.setForeground(new java.awt.Color(239, 234, 234));
        ex.setText("Exit");
        jPanel2.add(ex);
        ex.setBounds(510, 0, 40, 40);

        exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout (2).png"))); // NOI18N
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitMouseClicked(evt);
            }
        });
        jPanel2.add(exit);
        exit.setBounds(550, 0, 40, 40);

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 590, 40));

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

        jPanel1.add(add, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 360, 100, 40));

        servicename.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        servicename.setForeground(new java.awt.Color(239, 234, 234));
        servicename.setText("Service Name:");
        jPanel1.add(servicename, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 160, 100, 30));

        status.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        status.setForeground(new java.awt.Color(239, 234, 234));
        status.setText("Status: ");
        jPanel1.add(status, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 250, 100, 30));

        pricefield.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        pricefield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pricefield.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 234, 234), 2));
        pricefield.setEnabled(false);
        pricefield.setOpaque(false);
        jPanel1.add(pricefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 200, 210, 30));

        servicenamefield.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        servicenamefield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        servicenamefield.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 234, 234), 2));
        servicenamefield.setEnabled(false);
        servicenamefield.setOpaque(false);
        jPanel1.add(servicenamefield, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 160, 210, 30));

        serviceid.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        serviceid.setForeground(new java.awt.Color(239, 234, 234));
        serviceid.setText("Service ID:");
        jPanel1.add(serviceid, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, 100, 30));

        idfield.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        idfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        idfield.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 234, 234), 2));
        idfield.setEnabled(false);
        idfield.setOpaque(false);
        jPanel1.add(idfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 110, 210, 30));

        price1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        price1.setForeground(new java.awt.Color(239, 234, 234));
        price1.setText("Price:");
        jPanel1.add(price1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 200, 100, 30));

        inactive.setBackground(new java.awt.Color(55, 86, 93));
        inactive.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        inactive.setForeground(new java.awt.Color(255, 255, 255));
        inactive.setText("Inactive");
        inactive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inactiveActionPerformed(evt);
            }
        });
        jPanel1.add(inactive, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 250, 80, 30));

        active.setBackground(new java.awt.Color(55, 86, 93));
        active.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        active.setForeground(new java.awt.Color(255, 255, 255));
        active.setText("Active");
        active.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                activeActionPerformed(evt);
            }
        });
        jPanel1.add(active, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 250, 80, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 590, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 412, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMouseClicked
        int confirm = JOptionPane.showConfirmDialog(this,
            "Discard changes and go back?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
        }

    }//GEN-LAST:event_exitMouseClicked

    private void addMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseClicked
          String name      = servicenamefield.getText().trim();
        String priceText = pricefield.getText().trim();

        // Validate
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Service Name cannot be empty.", "Validation", JOptionPane.WARNING_MESSAGE);
            servicenamefield.requestFocus();
            return;
        }
        if (priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Price cannot be empty.", "Validation", JOptionPane.WARNING_MESSAGE);
            pricefield.requestFocus();
            return;
        }
        int priceVal;
        try {
            priceVal = Integer.parseInt(priceText);
            if (priceVal < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Price must be a valid whole number (0 or more).",
                "Validation", JOptionPane.WARNING_MESSAGE);
            pricefield.requestFocus();
            return;
        }

        // Read selected status from radio buttons
        String statusVal = active.isSelected() ? "Active" : "Inactive";

        // DB operation
        if (serviceId == 0) {
            // INSERT — includes s_status
            conf.addRecord(
                "INSERT INTO tbl_services (s_name, s_price, s_status) VALUES (?, ?, ?)",
                name, priceVal, statusVal
            );
            JOptionPane.showMessageDialog(this,
                "Service added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // UPDATE — includes s_status
            conf.updateRecord(
                "UPDATE tbl_services SET s_name = ?, s_price = ?, s_status = ? WHERE s_id = ?",
                name, priceVal, statusVal, serviceId
            );
            JOptionPane.showMessageDialog(this,
                "Service updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }

        // Refresh parent Services table and close
        if (parentServices != null) {
            parentServices.loadTable("");
        }
        this.dispose();
    }//GEN-LAST:event_addMouseClicked

    private void addMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseEntered
 add.setBackground(new java.awt.Color(39, 65, 75));
    }//GEN-LAST:event_addMouseEntered

    private void addMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseExited
   add.setBackground(new java.awt.Color(55, 86, 93));
    }//GEN-LAST:event_addMouseExited

    private void inactiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inactiveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inactiveActionPerformed

    private void activeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_activeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_activeActionPerformed

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
                new Profile().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton active;
    private javax.swing.JPanel add;
    private javax.swing.JLabel ex;
    private javax.swing.JLabel exit;
    public javax.swing.JTextField idfield;
    private javax.swing.JRadioButton inactive;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel price1;
    public javax.swing.JTextField pricefield;
    private javax.swing.JLabel serviceid;
    private javax.swing.JLabel servicename;
    public javax.swing.JTextField servicenamefield;
    public javax.swing.JLabel st_label;
    private javax.swing.JLabel status;
    // End of variables declaration//GEN-END:variables
}
