package USER;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class UserChatView extends JFrame {

    public UserChatView(String username, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        this.username = username;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
        initComponents();
    }

    private void initComponents() {

        chatHistoryListModel = new DefaultListModel<>();
        onlineListModel = new DefaultListModel<>();

        // Variables declaration - do not modify
        chatBoxPanel = new JPanel();
        inputTextField = new JTextField();
        submitBtn = new JButton();
        JScrollPane sp = new JScrollPane();
        chatHistoryList = new JList<>(chatHistoryListModel);
        JLabel receiverUser = new JLabel();
        JScrollPane onlineSP = new JScrollPane();
        onlineList = new JList<>();
        JLabel onlineUsersLabel = new JLabel();
        sendFileBtn = new JButton();

        inputTextField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        inputTextField.setHorizontalAlignment(JTextField.CENTER);
        inputTextField.getDocument().addDocumentListener(new DocumentListener() {
            private void enableSubmitBtn() {
                submitBtn.setEnabled(!inputTextField.getText().isEmpty());
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                enableSubmitBtn();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                enableSubmitBtn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                enableSubmitBtn();
            }
        });

        submitBtn.setText("SEND");
        submitBtn.setEnabled(false);
//        submitBtn.addActionListener(this::submitBtnActionPerformed);

        chatHistoryList.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        chatHistoryList.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        sp.setViewportView(chatHistoryList);

        receiverUser.setFont(new Font("Segoe UI", 0, 24)); // NOI18N
        receiverUser.setHorizontalAlignment(SwingConstants.CENTER);
        receiverUser.setText(username);

        onlineList.setFont(new Font("Segoe UI", 0, 14)); // NOI18N
        onlineList.addListSelectionListener(e -> {

        });
        onlineSP.setViewportView(onlineList);

        onlineUsersLabel.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        onlineUsersLabel.setHorizontalAlignment(SwingConstants.CENTER);
        onlineUsersLabel.setText("Online Users");

        sendFileBtn.setText("File");

        pack();
    }// </editor-fold>

    public void render() {
        EventQueue.invokeLater(() -> setVisible(true));
    }

    private String username;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private JPanel chatBoxPanel;
    private JLabel onlineUser;
    private JList<String> chatHistoryList;
    private JTextField inputTextField;
    private JList<String> onlineList;
    private JLabel receiverUser;
    private JButton sendFileBtn;
    private JButton submitBtn;

    private DefaultListModel<String> chatHistoryListModel;
    private DefaultListModel<String> onlineListModel;

    public JPanel getChatBoxPanel() {
        return chatBoxPanel;
    }

    public JList<String> getOnlineListUser() {
        return onlineList;
    }

    public JList<String> getChatHistoryList() {
        return chatHistoryList;
    }

    public DefaultListModel<String> getChatHistoryListModel() {
        return chatHistoryListModel;
    }

    public JTextField getInputTextField() {
        return inputTextField;
    }

    public JButton getSendFileBtn() {
        return sendFileBtn;
    }

    public JButton getSubmitBtn() {
        return submitBtn;
    }

    public DefaultListModel<String> getOnlineListModel() {
        return onlineListModel;
    }
}
