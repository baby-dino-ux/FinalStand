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
        this.serviceId      = id;
        this.parentServices = parent;
        initComponents();

        // Enable fields so user can type
        servicenamefield.setEnabled(true);
        pricefield.setEnabled(true);
        idfield.setEnabled(false);   // ID is always read-only

        if (id > 0) {
            // EDIT mode
            label1.setText("Edit Service");
            st_label.setText("Update");
            idfield.setText(String.valueOf(id));
            servicenamefield.setText(name);
            pricefield.setText(String.valueOf(priceV));
        } else {
            // ADD mode
            label1.setText("Add Services");
            st_label.setText("Save");
            idfield.setText("Auto");
        }
    }

    /** No-arg constructor for NetBeans designer */
    public ServiceForm() {
        this(0, "", 0, null);
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
        price = new javax.swing.JLabel();
        pricefield = new javax.swing.JTextField();
        servicenamefield = new javax.swing.JTextField();
        serviceid = new javax.swing.JLabel();
        idfield = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(29, 45, 61));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 234, 234), 3));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(55, 86, 93));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 234, 234), 3));
        jPanel2.setLayout(null);

        label1.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        label1.setForeground(new java.awt.Color(239, 234, 234));
        label1.setText("Add Services");
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

        price.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        price.setForeground(new java.awt.Color(239, 234, 234));
        price.setText("Price:");
        jPanel1.add(price, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 200, 100, 30));

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

        // DB operation
        if (serviceId == 0) {
            // INSERT
            conf.addRecord(
                "INSERT INTO tbl_services (s_name, s_price) VALUES (?, ?)",
                name, priceVal
            );
            JOptionPane.showMessageDialog(this,
                "Service added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // UPDATE
            conf.updateRecord(
                "UPDATE tbl_services SET s_name = ?, s_price = ? WHERE s_id = ?",
                name, priceVal, serviceId
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
    private javax.swing.JPanel add;
    private javax.swing.JLabel ex;
    private javax.swing.JLabel exit;
    public javax.swing.JTextField idfield;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel price;
    public javax.swing.JTextField pricefield;
    private javax.swing.JLabel serviceid;
    private javax.swing.JLabel servicename;
    public javax.swing.JTextField servicenamefield;
    public javax.swing.JLabel st_label;
    // End of variables declaration//GEN-END:variables
}
