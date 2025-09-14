package gd.workshop.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private Scanner consoleInput;


    public Client() throws IOException {
        consoleInput = new Scanner(System.in);
        socket = new Socket("127.0.0.1", Server.PORT);
        out = new DataOutputStream(socket.getOutputStream());
        in  = new DataInputStream(socket.getInputStream());

        new Thread(() -> {
            try {
                while (!socket.isConnected()) {
                    String resp = in.readUTF();
                    System.out.println("\nServer: " + resp);
                    System.out.print("Client> ");
                }
            }catch (Exception e) {
                System.out.println("Client disconnected");
            }
            finally {
                Close();
            }

        }).start();

            String line;
            do {
                System.out.print("Client> ");
                line = consoleInput.nextLine();
                out.writeUTF(line);
                out.flush();
            } while (!line.equals(Server.STOP_STRING) && !socket.isClosed());
        Close();
    }
//    public void sendMessage() throws IOException {
//        String line = "";
//        while (!line.equals(Server.STOP_STRING)) {
//            line = in.readUTF();
//            out.writeUTF("client:" + line);
//            out.flush();
//        }
//    }
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
