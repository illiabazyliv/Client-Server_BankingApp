package gd.workshop.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private DataOutputStream out;
    private Scanner in;


    public Client() throws IOException {
        in = new Scanner(System.in);
        socket = new Socket("127.0.0.1", Server.PORT);
        out = new DataOutputStream(socket.getOutputStream());
    }
    public void sendMessage(String message) throws IOException {
        String line = "";
        while (!line.equals(Server.StopSighn)) {
            line = in.nextLine();
            out.writeUTF("client:" + line);
            out.writeUTF(message);
        }
    }
    private void Close(){
        try {
            socket.close();
            out.close();
            in.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        new Client();
    }
}
