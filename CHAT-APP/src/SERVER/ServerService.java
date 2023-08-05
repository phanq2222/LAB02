package SERVER;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
}
