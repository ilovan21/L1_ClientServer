import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static List<PrintWriter> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1999);
            System.out.println("Waiting for connections..");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                clients.add(out);

                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcast(String message) {
        for (PrintWriter client : clients) {
            client.println("To all: " + message);
        }
    }
}
class ClientHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader in;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            // Inițializare BufferedReader pentru a citi mesaje de la client
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            String clientMessage;
            while ((clientMessage = in.readLine()) != null) {
                System.out.println("Client: " + clientMessage);

                // Trimite mesajul către broadcast
                Server.broadcast(clientMessage);
            }

            // Client disconnected
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
