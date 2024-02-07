import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {

            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Waiting for connections..");

            while (true) {

                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);


                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);


            String clientMessage;
            while ((clientMessage = in.readLine()) != null) {
                System.out.println("Client " + clientMessage);
                out.println("Server: - " + clientMessage);
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}