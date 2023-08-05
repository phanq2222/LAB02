package SERVER;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ServerService {
    final static int PORT = 9999;

    private final Lock lock;
    private final ServerSocket serverSocket;
    private Socket socket;



    static HashMap<String, ClientHandlerThread> clients = new HashMap<>();

    public ServerService() throws IOException {
        this.lock = new ReentrantLock();
        this.serverSocket = new ServerSocket(PORT);
        loadAccounts();
    }

    public void start() throws IOException {
        while (true) {
            this.socket = serverSocket.accept();

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String incomingMess = dataInputStream.readUTF();

            switch (incomingMess) {
                case "login" -> login(dataInputStream);
                case "register" -> register(dataInputStream);
            }
        }
    }


    private void login(DataInputStream dataInputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        String username = dataInputStream.readUTF();
        String password = dataInputStream.readUTF();

        if (clients.containsKey(username)) {

            if (clients.get(username).getIsLogin()) {
                dataOutputStream.writeUTF("This account is online");
                dataOutputStream.flush();
                return;
            }

            ClientHandlerThread client = clients.get(username);
            if (client.getPassword().equals(password)) {
                client.setSocket(socket);
                client.setLogin(true);

                dataOutputStream.writeUTF("Login Successful");
                dataOutputStream.flush();

                client.start();

                notifyOnlineUsers();
            }
        } else {
            dataOutputStream.writeUTF("Invalid username or password");
            dataOutputStream.flush();
        }
    }

    class ClientHandlerThread extends Thread {

        private final Lock lock;
        private Socket socket;
        private DataInputStream dataInputStream;

        private DataOutputStream dataOutputStream;

        final private String username;
        final private String password;
        private Boolean isLogin;

        /**
         * Tạo thread cho tài khoản vừa đăng ký rồi đăng nhập liền
         *
         * @param username tên đăng nhập
         * @param password password
         * @param isLogin  kiểm tra xem đã login chưa
         * @param socket   socket của client
         * @param lock     khoá synchronize
         */
        ClientHandlerThread(String username, String password, Boolean isLogin, Socket socket, Lock lock)
                throws IOException {
            this.username = username;
            this.password = password;
            this.isLogin = isLogin;
            this.socket = socket;
            this.lock = lock;

            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            this.dataInputStream = new DataInputStream(socket.getInputStream());
        }
    }
}
