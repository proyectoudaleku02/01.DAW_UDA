/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import proyectoudaleku.Main;

/**
 *
 * @author 1glm07
 */
public class Inicio extends javax.swing.JFrame {

    /**
     * Creates new form Inicio
     */
    public Inicio() {
        initComponents();
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuBar1 = new javax.swing.JMenuBar();
        mInicio = new javax.swing.JMenu();
        mInscripciones = new javax.swing.JMenu();
        itemInscribirse = new javax.swing.JMenuItem();
        mConsultas = new javax.swing.JMenu();
        mConSolicitud = new javax.swing.JMenuItem();
        mSalir = new javax.swing.JMenu();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1300, 720));

        mInicio.setText("Inicio");
        jMenuBar1.add(mInicio);

        mInscripciones.setText("Inscripciones");

        itemInscribirse.setText("Nueva solicitud");
        itemInscribirse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemInscribirseActionPerformed(evt);
            }
        });
        mInscripciones.add(itemInscribirse);

        jMenuBar1.add(mInscripciones);

        mConsultas.setText("Consultas");

        mConSolicitud.setText("Consultar solicitud");
        mConSolicitud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mConSolicitudActionPerformed(evt);
            }
        });
        mConsultas.add(mConSolicitud);

        jMenuBar1.add(mConsultas);

        mSalir.setText("Salir");
        mSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mSalirMouseClicked(evt);
            }
        });
        jMenuBar1.add(mSalir);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1034, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 752, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itemInscribirseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemInscribirseActionPerformed
        Main.verPanInscrip();
    }//GEN-LAST:event_itemInscribirseActionPerformed

    private void mSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mSalirMouseClicked
        Main.salir();
    }//GEN-LAST:event_mSalirMouseClicked

    private void mConSolicitudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mConSolicitudActionPerformed
        
    }//GEN-LAST:event_mConSolicitudActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Inicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem itemInscribirse;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem mConSolicitud;
    private javax.swing.JMenu mConsultas;
    private javax.swing.JMenu mInicio;
    private javax.swing.JMenu mInscripciones;
    private javax.swing.JMenu mSalir;
    // End of variables declaration//GEN-END:variables
}
