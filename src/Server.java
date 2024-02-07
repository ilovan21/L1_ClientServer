import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {

            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Serverul este pornit. Asteptand conexiuni...");

            while (true) {

                Socket clientSocket = serverSocket.accept();
                System.out.println("S-a conectat un nou client: " + clientSocket);


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
                System.out.println("Mesaj de la client: " + clientMessage);
                out.println("Server: Mesaj primit - " + clientMessage);
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}