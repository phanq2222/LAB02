package SERVER;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ServerView extends JFrame {

    private ServerService serverService;
    private JPanel serverPanel;

    public ServerView() {
        initComponents();
    }

    private JLabel serverStateLabel;
    private JButton startServerButton;
    private void initComponents() {

        serverPanel = new javax.swing.JPanel();
        startServerButton = new javax.swing.JButton();
        serverStateLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        startServerButton.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 24)); // NOI18N
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

        serverStateLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        serverStateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        serverStateLabel.setText("Server Offline");

        javax.swing.GroupLayout serverPanelLayout = new javax.swing.GroupLayout(serverPanel);
        serverPanel.setLayout(serverPanelLayout);
        serverPanelLayout.setHorizontalGroup(
                serverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(serverStateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, serverPanelLayout.createSequentialGroup()
                                .addContainerGap(82, Short.MAX_VALUE)
                                .addComponent(startServerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(82, 82, 82))
        );
        serverPanelLayout.setVerticalGroup(
                serverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, serverPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(serverStateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(startServerButton)
                                .addGap(32, 32, 32))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(serverPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(serverPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 129, Short.MAX_VALUE)
                                .addContainerGap())
        );

        pack();
    }

    public void render() {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> setVisible(true));
    }
}
