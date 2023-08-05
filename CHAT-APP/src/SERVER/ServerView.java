package SERVER;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ServerView extends JFrame {
    private JLabel serverPanel;
    private JLabel serverStateLabel;
    private JButton startServerButton;
    private JPanel panel1;

    private ServerService serverService;

    public ServerView() {
        initComponents();
    }

    private void initComponents() {

        startServerButton = new javax.swing.JButton();
        serverStateLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        startServerButton.setText("Start");
        startServerButton.addActionListener(evt -> {
            Thread thread = new Thread(() -> {
                try {
                    serverService = new ServerService();
                    serverService.start();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (serverService != null) {
                        try {
                            serverService.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
            serverStateLabel.setText("Server is running");
            startServerButton.setEnabled(false);
        });

        serverStateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        serverStateLabel.setText("Server Offline");

        pack();
    }

    public void render() {
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> setVisible(true));
    }
}
