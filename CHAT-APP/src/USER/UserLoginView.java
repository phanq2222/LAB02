package USER;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class UserLoginView  extends JFrame {

    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public UserLoginView() throws IOException {
        initComponents();
    }

    private void initComponents() {

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        loginPanel = new JPanel();
        btnRegister = new JButton();
        lblUsername = new JLabel();
        txtUsername = new JTextField();
        lblPassword = new JLabel();
        btnLoginUser = new JButton();
        txtPassword = new JPasswordField();


        btnRegister.setText("Register");
        btnRegister.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 10)); // NOI18N
        btnRegister.addActionListener(evt -> {
            try {
                socket = new Socket("localhost", 9999);
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                String username = txtUsername.getText();
                String password = String.valueOf(txtPassword.getPassword());

                dataOutputStream.writeUTF("register");
                dataOutputStream.writeUTF(username);
                dataOutputStream.writeUTF(password);
                dataOutputStream.flush();

                String res = dataInputStream.readUTF();
                JOptionPane.showMessageDialog(
                        null,
                        res,
                        "Response",
                        JOptionPane.INFORMATION_MESSAGE
                );

                if (res.equals("Register account successful")) {
                    new UserService(username, dataInputStream, dataOutputStream);
                    dispose();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 12)); // NOI18N
        lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
        lblUsername.setText("Username");

        txtUsername.setHorizontalAlignment(JTextField.CENTER);

        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 12)); // NOI18N
        lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
        lblPassword.setText("Password");

        btnLoginUser.setText("LOGIN");
        btnLoginUser.addActionListener(evt -> {
            try {
                socket = new Socket("localhost", 9999);
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                String username = txtUsername.getText();
                String password = String.valueOf(txtPassword.getPassword());

                dataOutputStream.writeUTF("login");
                dataOutputStream.writeUTF(username);
                dataOutputStream.writeUTF(password);
                dataOutputStream.flush();

                String res = dataInputStream.readUTF();
                JOptionPane.showMessageDialog(
                        null,
                        res,
                        "Response",
                        JOptionPane.INFORMATION_MESSAGE
                );

                if (res.equals("Login Successful")) {
                    new UserService(username, dataInputStream, dataOutputStream);
                    dispose();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        txtPassword.setHorizontalAlignment(JTextField.CENTER);

        GroupLayout jPanel1Layout = new GroupLayout(loginPanel);
        loginPanel.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap(56, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(btnLoginUser, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btnRegister, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE))
                                                .addGap(137, 137, 137))
                                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(txtPassword))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(lblUsername, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE)))
                                                .addGap(50, 50, 50))))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblUsername, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblPassword, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                                        .addComponent(txtPassword))
                                .addGap(26, 26, 26)
                                .addComponent(btnLoginUser, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRegister, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(24, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(loginPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(loginPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        pack();
    }

    public void render() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserLoginView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(() -> setVisible(true));
    }

    private JPanel loginPanel;
    private JButton btnLoginUser;
    private JButton btnRegister;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JLabel lblUsername;
    private JLabel lblPassword;
}