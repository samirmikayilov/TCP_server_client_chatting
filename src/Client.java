import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket clientSocket = new Socket("127.0.0.1", 8080);

        BufferedReader inFromConsole = new BufferedReader(new InputStreamReader(System.in)); // console read
        BufferedWriter outToClient = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())); // clinete geden
        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        new Thread(() -> {
            String message;
            while (true) {
                try {
                    if (!((message = inFromConsole.readLine()) != null)) break;
                    outToClient.write(message);
                    outToClient.newLine();
                    outToClient.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();


        String input;
        while ((input = inFromClient.readLine()) != null) {
            System.out.println(input);
        }


    }
}

