package USER;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.HashMap;

public class UserService {
    final private UserChatView userChatView;

    final private DataInputStream dataInputStream;
    final private DataOutputStream dataOutputStream;

    final private String username;

    final private HashMap<String, DefaultListModel<String>> privateModelChat;

    public UserService(String username, DataInputStream dataInputStream, DataOutputStream dataOutputStream){
            this.username = username;
            this.dataInputStream = dataInputStream;
            this.dataOutputStream = dataOutputStream;

            this.privateModelChat = new HashMap<>();

            this.userChatView = new UserChatView(username,dataInputStream, dataOutputStream);

    }
}
