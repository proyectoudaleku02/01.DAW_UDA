/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Modelo.UML.Inscripcion;
import Modelo.UML.Solicitud;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import proyectoudaleku.Main;

/**
 *
 * @author sergio
 */
public class ConfInscrip extends javax.swing.JDialog {

    private Solicitud solSelected;
    private Inscripcion insSelected;
    private final int maxInscrip;
    
    
    public ConfInscrip(java.awt.Frame parent, boolean modal, Solicitud solSelected, Inscripcion insSelected,int maxInscrip) {
        super(parent, modal);
        initComponents();
        this.solSelected=solSelected;this.insSelected=insSelected;this.maxInscrip=maxInscrip;
        setLocationRelativeTo(null);
        controlInicio();
    }

    private ConfInscrip(JFrame jFrame, boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bTerminar = new javax.swing.JButton();
        lAviso2 = new javax.swing.JLabel();
        bCancelar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        taInfo = new javax.swing.JTextArea();
        lAviso1 = new javax.swing.JLabel();
        bAdd = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        bTerminar.setText("Terminar");
        bTerminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTerminarActionPerformed(evt);
            }
        });

        lAviso2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lAviso2.setText("¿Desea cerrar la solicitud o añadir más participantes a la misma?");

        bCancelar.setText("Cancelar");
        bCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarActionPerformed(evt);
            }
        });

        taInfo.setColumns(20);
        taInfo.setRows(5);
        jScrollPane1.setViewportView(taInfo);

        lAviso1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lAviso1.setText("Hay 3 plazas en esta solcitud");

        bAdd.setText("Añadir participante");
        bAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 761, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(179, 179, 179)
                        .addComponent(bTerminar)
                        .addGap(93, 93, 93)
                        .addComponent(bAdd)
                        .addGap(84, 84, 84)
                        .addComponent(bCancelar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(209, 209, 209)
                        .addComponent(lAviso2, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(39, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lAviso1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(301, 301, 301))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lAviso1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lAviso2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bCancelar)
                    .addComponent(bAdd)
                    .addComponent(bTerminar))
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bTerminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTerminarActionPerformed
        if(solSelected.getInscripciones().size()<=maxInscrip)
        {
            Main.inscribir("end");
        }else
        {
            bCancelar.doClick();
        }
        
    }//GEN-LAST:event_bTerminarActionPerformed

    private void bAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddActionPerformed
        Main.inscribir("add");
    }//GEN-LAST:event_bAddActionPerformed

    private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
        Main.cancelarDialogo(this);
    }//GEN-LAST:event_bCancelarActionPerformed

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
            java.util.logging.Logger.getLogger(ConfInscrip.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConfInscrip.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConfInscrip.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConfInscrip.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ConfInscrip dialog = new ConfInscrip(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAdd;
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bTerminar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lAviso1;
    private javax.swing.JLabel lAviso2;
    private javax.swing.JTextArea taInfo;
    // End of variables declaration//GEN-END:variables

    private void controlInicio() {
        // Controlamos si hemos llegago al límite de inscripciones.
        if(solSelected.getInscripciones().size()>=maxInscrip)
            bAdd.setEnabled(false);
        String text = "RESUMEN DE LA SOLICITUD\n---------------------------------------------------\n";
        taInfo.setText(text+"Participante: "+insSelected.getMenor().getNomAps()+"\n"
                +"Padre/madre o Tutor/a: "+insSelected.getTutor().getNomAps()+"\n---------------------------------------------------\n");
        lAviso1.setText("Hay "+String.valueOf(maxInscrip - solSelected.getInscripciones().size())+" plazas en esta solicitud");

    }

    public void mostrarHecho(String tipo) {
        
        if(tipo.equalsIgnoreCase("add"))
        {
            mostrar("Inscripción registrada.\nQueda "+String.valueOf(maxInscrip - solSelected.getInscripciones().size())+" plazas en esta solicitud");
        }else{
            mostrar("Solicitud registrada. Su numero de solicitud es\n"+"XXXXXXXXX");
        }
            
    }

    private void mostrar(String mensaje) {
            JOptionPane.showMessageDialog(rootPane, mensaje);
    }
}
