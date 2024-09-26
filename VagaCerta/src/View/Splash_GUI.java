/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sapat
 */
public class Splash_GUI extends javax.swing.JFrame {

    /**
     * Creates new form Splash_GUI
     */
    public Splash_GUI() {
        initComponents();
        new Thread() {

            public void run() {
                for (int i = 0; i < 101; i++) {
                    try {
                        sleep(60);

                        View.Splash_GUI.progressBar.setValue(i);

                        if (View.Splash_GUI.progressBar.getValue() == 10) {

                            View.Splash_GUI.message_lbl.setText("Fazendo a conexao com o banco de dados");
                            sleep(2000);

                        } else if (View.Splash_GUI.progressBar.getValue() <= 30) {
                            View.Splash_GUI.message_lbl.setText("Carregando o sistema");
                            sleep(100);
                        } else if (View.Splash_GUI.progressBar.getValue() <= 99) {
                            View.Splash_GUI.message_lbl.setText("Carregamento quase completo");

                        } else {
                            View.Splash_GUI.message_lbl.setText("Carregamento completo. Seu programa sera iniciado.");

                            sleep(3000);

                            new SetUp_GUI().setVisible(true);
                            Splash_GUI.this.dispose();
                        }

                    } catch (InterruptedException ex) {
                        Logger.getLogger(Splash_GUI.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }

        }.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        progressBar = new javax.swing.JProgressBar();
        message_lbl = new javax.swing.JLabel();
        splashBG_lbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setLayout(null);

        progressBar.setBackground(new java.awt.Color(255, 255, 255));
        progressBar.setForeground(new java.awt.Color(113, 159, 177));
        progressBar.setToolTipText("");
        jPanel1.add(progressBar);
        progressBar.setBounds(80, 250, 340, 20);

        message_lbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        message_lbl.setForeground(new java.awt.Color(255, 255, 255));
        message_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        message_lbl.setText("Mensagem");
        jPanel1.add(message_lbl);
        message_lbl.setBounds(80, 270, 340, 20);

        splashBG_lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/SplashScreen.png"))); // NOI18N
        jPanel1.add(splashBG_lbl);
        splashBG_lbl.setBounds(0, 0, 500, 500);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(500, 500));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(Splash_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Splash_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Splash_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Splash_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Splash_GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JLabel message_lbl;
    public static javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel splashBG_lbl;
    // End of variables declaration//GEN-END:variables
}