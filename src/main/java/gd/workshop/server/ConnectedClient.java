package gd.workshop.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnectedClient {
   private final Socket socket;
   private DataInputStream in;
   private final int ID;

   public ConnectedClient(Socket socket, int ID) {
       this.socket = socket;
       this.ID = ID;
       try {
           System.out.println("Client" + ID + "connected");
           this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
       }
       catch (IOException e) {
           e.printStackTrace();
       }
   }
   public void readMessage()throws IOException {
    String line = "";
    while (!line.equals(Server.STOP_STRING)) {
        try {
            line = in.readUTF();
            System.out.println("Client" + ID + ": " + line);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(line);
    }
       System.out.println("Client" + ID + "disconnected");
   }
    public void close() throws IOException {
       try {
           socket.close();
           in.close();
       }
       catch (IOException e) {
           e.printStackTrace();
       }
    }
   
}
