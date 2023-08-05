package SERVER;

import java.io.*;
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

    private void loadAccounts() throws IOException {
        String fileName = "accounts.txt";
        if (Files.notExists(Path.of(fileName))) {
            Files.createFile(Path.of(fileName));
        }
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] account = line.split(" - ");

            String username = account[0];
            String password = account[1];
            ClientHandlerThread client = new ClientHandlerThread(username, password, false, lock);
            clients.put(username, client);
        }

        bufferedReader.close();
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

    private void register(DataInputStream dataInputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        String username = dataInputStream.readUTF();
        String password = dataInputStream.readUTF();
        if (!clients.containsKey(username)) {
            if (Files.notExists(Path.of("accounts.txt"))) {
                Files.createFile(Path.of("accounts.txt"));
            }
            FileWriter fileWriter = new FileWriter("accounts.txt", true);
            fileWriter.write(String.format("%s - %s\n", username, password));
            fileWriter.flush();
            fileWriter.close();

            ClientHandlerThread clientHandlerThread = new ClientHandlerThread(username, password, true,
                    socket, lock);
            clients.put(username, clientHandlerThread);

            dataOutputStream.writeUTF("Register account successful");
            dataOutputStream.flush();

            clientHandlerThread.start();

            notifyOnlineUsers();
        } else {
            dataOutputStream.writeUTF("Username already existed");
            dataOutputStream.flush();
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

    private void notifyOnlineUsers() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for (ClientHandlerThread client : clients.values()) {
            if (client.getIsLogin()) {
                stringBuilder.append(client.getUsername());
                stringBuilder.append(";");
            }
        }
        for (ClientHandlerThread client : clients.values()) {
            if (client.getIsLogin()) {
                DataOutputStream dataOutputStream = client.getDataOutputStream();
                dataOutputStream.writeUTF("online-users");
                dataOutputStream.writeUTF(stringBuilder.toString());
                dataOutputStream.flush();
            }
        }
    }

    public void close() throws IOException {
        serverSocket.close();
    }

    class ClientHandlerThread extends Thread {

        private final Lock lock;
        private Socket socket;
        private DataInputStream dataInputStream;

        private DataOutputStream dataOutputStream;

        final private String username;
        final private String password;
        private Boolean isLogin;

        public DataInputStream getDataInputStream() {
            return dataInputStream;
        }

        public DataOutputStream getDataOutputStream() {
            return dataOutputStream;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public Boolean getIsLogin() {
            return isLogin;
        }

        ClientHandlerThread(String username, String password, Boolean isLogin, Lock lock) {
            this.username = username;
            this.password = password;
            this.isLogin = isLogin;
            this.lock = lock;
        }


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



        public void setSocket(Socket socket) throws IOException {
            this.socket = socket;

            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            this.dataInputStream = new DataInputStream(socket.getInputStream());
        }

        public void setLogin(Boolean isLogin) {
            this.isLogin = isLogin;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String messageFromClient = dataInputStream.readUTF();

                    if (messageFromClient.equals("logout")) {
                        dataOutputStream.writeUTF("logout");
                        dataOutputStream.flush();
                        socket.close();

                        isLogin = false;
                        notifyOnlineUsers();

                        break;
                    }
                    switch (messageFromClient) {
                        case "send-text" -> {

                            String receiver = dataInputStream.readUTF();
                            String content = dataInputStream.readUTF();

                            try {
                                // Lock synchronize
                                lock.lock();
                                ClientHandlerThread client = clients.get(receiver);
                                client.dataOutputStream.writeUTF("send-text");
                                client.dataOutputStream.writeUTF(username);
                                client.dataOutputStream.writeUTF(content);
                                client.dataOutputStream.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                lock.unlock();
                            }
                        }
                        case "send-file" -> {

                            int bufferSize = 4 * 1024;
                            byte[] buffer = new byte[bufferSize];

                            String receiver = dataInputStream.readUTF();
                            String fileName = dataInputStream.readUTF();
                            long fileSize = dataInputStream.readLong();

                            try {
                                // Lock synchronize
                                lock.lock();
                                ClientHandlerThread client = clients.get(receiver);
                                DataOutputStream clientOutputStream = client.getDataOutputStream();

                                // Thông tin người gửi và thông tin file
                                clientOutputStream.writeUTF("send-file");
                                clientOutputStream.writeUTF(username);
                                clientOutputStream.writeUTF(fileName);
                                clientOutputStream.writeLong(fileSize);

                                // Gửi file kiểu chunk
                                while (fileSize > 0) {
                                    dataInputStream.read(buffer, 0, (int) Math.min(fileSize, bufferSize));
                                    client.dataOutputStream.write(buffer, 0, (int) Math.min(fileSize, bufferSize));
                                    fileSize -= bufferSize;
                                }
                                clientOutputStream.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                lock.unlock();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
