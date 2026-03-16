/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Staff;

import LoginandRegister.LoginForm;
import Session.Session;
import javax.swing.JOptionPane;

/**
 *
 * @author ashlaran
 */
public class GenerateReceipt extends javax.swing.JFrame {

  // ── Parameterized constructor — called from CreateBooking ──────────────────
public GenerateReceipt(int bookingId, String date, String customerName,
                        String address, String contactNo, String cleaner,
                        String service, String amount, String taskNote,
                        String staffName, String serviceDescription) {
    initComponents();
    receiptno.setText("REC-" + String.format("%04d", bookingId));
    bookingid.setText("BK-" + bookingId);
    this.date.setText(date);
    customername.setText(customerName);
    this.address.setText(address);
    contactno.setText(contactNo);
    this.cleaner.setText(cleaner);
    this.service.setText(service);
    this.amount.setText("₱" + amount);
    tasknote.setText(taskNote == null || taskNote.isEmpty() ? "None" : taskNote);
    fullname4.setText("₱" + amount);
    // Show description if available, otherwise show service name
    description.setText(serviceDescription != null && !serviceDescription.isEmpty()
        ? serviceDescription : service);
}

// ── Backward-compatible 10-arg constructor (no description) ────────────────
public GenerateReceipt(int bookingId, String date, String customerName,
                        String address, String contactNo, String cleaner,
                        String service, String amount, String taskNote,
                        String staffName) {
    this(bookingId, date, customerName, address, contactNo, cleaner,
         service, amount, taskNote, staffName, "");
}

// ── Default constructor (for NetBeans designer) ─────────────────────────────
public GenerateReceipt() {
    initComponents();
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSlider1 = new javax.swing.JSlider();
        bookingid = new javax.swing.JLabel();
        contactno = new javax.swing.JLabel();
        date = new javax.swing.JLabel();
        customername = new javax.swing.JLabel();
        address = new javax.swing.JLabel();
        cleaner = new javax.swing.JLabel();
        receiptno = new javax.swing.JLabel();
        amount = new javax.swing.JLabel();
        tasknote = new javax.swing.JLabel();
        service = new javax.swing.JLabel();
        description = new javax.swing.JLabel();
        fullname4 = new javax.swing.JLabel();
        createpanel = new javax.swing.JPanel();
        create = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bookingid.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        bookingid.setForeground(new java.awt.Color(102, 102, 102));
        bookingid.setText("Label");
        bookingid.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        getContentPane().add(bookingid, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 180, 160, 40));

        contactno.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        contactno.setForeground(new java.awt.Color(102, 102, 102));
        contactno.setText("Label");
        contactno.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        getContentPane().add(contactno, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 280, 160, 20));

        date.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        date.setForeground(new java.awt.Color(102, 102, 102));
        date.setText("Label");
        date.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        getContentPane().add(date, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 210, 160, 30));

        customername.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        customername.setForeground(new java.awt.Color(102, 102, 102));
        customername.setText("Label");
        customername.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        getContentPane().add(customername, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 230, 160, 30));

        address.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        address.setForeground(new java.awt.Color(102, 102, 102));
        address.setText("Label");
        address.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        getContentPane().add(address, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 260, 160, 20));

        cleaner.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        cleaner.setForeground(new java.awt.Color(102, 102, 102));
        cleaner.setText("Label");
        cleaner.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        getContentPane().add(cleaner, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, 160, 20));

        receiptno.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        receiptno.setForeground(new java.awt.Color(102, 102, 102));
        receiptno.setText("Label");
        receiptno.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        getContentPane().add(receiptno, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 160, 160, 30));

        amount.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        amount.setForeground(new java.awt.Color(102, 102, 102));
        amount.setText("Label");
        amount.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        getContentPane().add(amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 380, 90, 20));

        tasknote.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        tasknote.setForeground(new java.awt.Color(102, 102, 102));
        tasknote.setText("Label");
        getContentPane().add(tasknote, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 470, 290, 40));

        service.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        service.setForeground(new java.awt.Color(102, 102, 102));
        service.setText("Full Name");
        getContentPane().add(service, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 380, 100, 20));

        description.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        description.setForeground(new java.awt.Color(102, 102, 102));
        description.setText("Label");
        getContentPane().add(description, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 380, 120, 20));

        fullname4.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        fullname4.setForeground(new java.awt.Color(29, 45, 61));
        fullname4.setText("Label");
        fullname4.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        getContentPane().add(fullname4, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 420, 110, 40));

        createpanel.setBackground(new java.awt.Color(29, 45, 61));
        createpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createpanelMouseClicked(evt);
            }
        });
        createpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        create.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        create.setForeground(new java.awt.Color(255, 255, 255));
        create.setText("CREATE");
        create.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        createpanel.add(create, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 50, 40));

        getContentPane().add(createpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 610, 100, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/receept.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 420, 700));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void createpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createpanelMouseClicked
       saveReceiptAsPDF();
}

private void saveReceiptAsPDF() {
    // Use Java's PrinterJob to print to PDF
    // This uses the system's "Print to PDF" printer
    java.awt.print.PrinterJob job = java.awt.print.PrinterJob.getPrinterJob();
    job.setPageable(new java.awt.print.Book() {{
        // Capture the current frame as a printable page
    }});

    // Simpler approach: use javax.print to print the frame content
    java.awt.print.PrinterJob printerJob = java.awt.print.PrinterJob.getPrinterJob();
    printerJob.setPrintable((graphics, pageFormat, pageIndex) -> {
        if (pageIndex > 0) return java.awt.print.Printable.NO_SUCH_PAGE;
        // Scale the frame to fit the page
        double scaleX = pageFormat.getImageableWidth() / getWidth();
        double scaleY = pageFormat.getImageableHeight() / getHeight();
        double scale = Math.min(scaleX, scaleY);
        ((java.awt.Graphics2D) graphics).translate(
            pageFormat.getImageableX(), pageFormat.getImageableY());
        ((java.awt.Graphics2D) graphics).scale(scale, scale);
        printAll(graphics);
        return java.awt.print.Printable.PAGE_EXISTS;
    });

    if (printerJob.printDialog()) {
        try {
            printerJob.print();
            JOptionPane.showMessageDialog(this,
                "Receipt sent to printer/PDF successfully!",
                "Printed", JOptionPane.INFORMATION_MESSAGE);
        } catch (java.awt.print.PrinterException e) {
            JOptionPane.showMessageDialog(this,
                "Print failed: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    }//GEN-LAST:event_createpanelMouseClicked

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
    private javax.swing.JLabel address;
    private javax.swing.JLabel amount;
    private javax.swing.JLabel bookingid;
    private javax.swing.JLabel cleaner;
    private javax.swing.JLabel contactno;
    private javax.swing.JLabel create;
    private javax.swing.JPanel createpanel;
    private javax.swing.JLabel customername;
    private javax.swing.JLabel date;
    private javax.swing.JLabel description;
    private javax.swing.JLabel fullname4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JLabel receiptno;
    private javax.swing.JLabel service;
    private javax.swing.JLabel tasknote;
    // End of variables declaration//GEN-END:variables
}
