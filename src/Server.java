import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(8080);

        System.out.println("Waiting for connection...");
        Socket clientSocket = serverSocket.accept();
        System.out.println("Connected to client...");
        BufferedReader inFromConsole = new BufferedReader(new InputStreamReader(System.in)); // console read
        BufferedWriter outToClient = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //clientden gelen

       new Thread(()->{
           String clientdenGelen;
           while (true) {
               try {
                   if (!((clientdenGelen = inFromClient.readLine()) != null)) break;
               } catch (IOException e) {
                   throw new RuntimeException(e);
               }
               System.out.println(clientdenGelen);
           }
       }).start();

        String input;
        while ((input = inFromConsole.readLine()) != null) {
            outToClient.write(input);
            outToClient.newLine();
            outToClient.flush();
        }


        outToClient.close();
        clientSocket.close();
        serverSocket.close();
        System.out.println("Server bağlandı.");
    }

}
