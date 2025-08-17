package gd.workshop.server;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket server;
    private DataInputStream in;
    public static int PORT = 3000;
    public static String STOP_STRING = "##";
    private int index = 0;

    public Server() {
        try {
            server = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);
            while (true) {
                iniConnections();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void iniConnections() throws Exception {
        System.out.println("Waiting for connections...");
        Socket socket = server.accept();
        System.out.println("New connection from " + socket.getRemoteSocketAddress());
        if (socket.isConnected()) {
            new Thread(() ->{
                index++;
                ConnectedClient connectedClient = new ConnectedClient(socket , index);
                try {
                    connectedClient.readMessage();
                    connectedClient.close();
                }
                catch (Exception e){
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }
    public static void main(String[] args) {
        Server server = new Server();
    }
}
