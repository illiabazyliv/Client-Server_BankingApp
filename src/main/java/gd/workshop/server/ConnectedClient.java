package gd.workshop.server;

import gd.workshop.comands.CommandRegistry;
import gd.workshop.entities.UserContext;
import gd.workshop.handler.CommandRouter;

import java.io.*;
import java.net.Socket;

public class ConnectedClient implements Runnable {
   private final Socket socket;
   private DataInputStream in;
   private DataOutputStream out;
   private final int ID;
   private final UserContext context;
   private final CommandRouter router;
   private final Server server;

    public ConnectedClient(Socket socket, int ID, UserContext context, CommandRouter router , Server server) {
       this.socket = socket;
       this.ID = ID;
       this.context = context;
       this.router = router;
       this.server = server;

       try {
           System.out.println("Client" + ID + "connected");
           this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
           this.out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
       }
       catch (IOException e) {
           System.out.println("Error setting up streams for client"  + ID + e.getMessage());
           e.printStackTrace();
       }
       try {
           close();
       }
       catch (IOException e) {
           e.printStackTrace();
       }
   }
   @Override
    public void run(){
       try {
           while (!socket.isClosed()) {
               String line = in.readUTF();
               System.out.println("Received from Client " + ID + ": " + line);
               if (line.equals(Server.STOP_STRING)) break;

               String response = router.Route(line, context);
               out.writeUTF(response);
               out.flush();
               if (!line.toUpperCase().startsWith("LOGIN") && !line.toUpperCase().startsWith("HELP")) {
                   server.broadcastMessage("Client" + ID  + ":" + line, this);
               }
           }
       }
       catch (IOException e) {
           e.printStackTrace();
           System.out.println("Client " + ID + "disconnected" + e.getMessage());
       }
       finally {
           try {
               close();
               server.removeDisconnectedClient(this);
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
       }

   }
    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
            out.flush();
        } catch (IOException e) {
            System.out.println("Error sending message to client " + ID + ": " + e.getMessage());
            try {
                close();
                server.removeDisconnectedClient(this);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public int getID() {
        return ID;
    }
   public void readMessage()throws IOException {
    String line = "";
    while (!line.equals(Server.STOP_STRING)) {
        try {
            line = in.readUTF();
            System.out.println("Client" + ID + ": " + line);
            String response = router.Route(line,context);
            out.writeUTF(response);
            out.flush();
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
