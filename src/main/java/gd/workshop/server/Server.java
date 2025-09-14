package gd.workshop.server;

import gd.workshop.comands.CommandRegistry;
import gd.workshop.comands.Commands;
import gd.workshop.entities.UserContext;
import gd.workshop.handler.CommandRouter;
import gd.workshop.handler.LoginHandler;
import org.w3c.dom.ls.LSOutput;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {
    private ServerSocket server;
    public static int PORT = 3000;
    public static String STOP_STRING = "##";
    private int index = 0;
    private List<ConnectedClient> clients = Collections.synchronizedList(new ArrayList<>());
    private CommandRouter router;

    public Server() {
        try {
            server = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);
            router = new CommandRouter();
            CommandRegistry.register(router);

            while (true) {
                Socket socket = server.accept();
                System.out.println("New connection from" + socket.getRemoteSocketAddress());
                ConnectedClient connectedClient = new ConnectedClient(socket , index++ , new UserContext(), router, this);
                clients.add(connectedClient);
                new Thread(connectedClient).start();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (server != null) {
                    server.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeDisconnectedClient(ConnectedClient connectedClient) {
        clients.remove(connectedClient);
        System.out.println("Client"+ connectedClient.getID() + " disconnected");
    }
    public void broadcastMessage(String message , ConnectedClient connectedClient) {
        for (ConnectedClient client : clients) {
            if(client!=connectedClient) {
                client.sendMessage(message);
            }
        }
    }
    public static void main(String[] args) {
        new Server();
    }
}
