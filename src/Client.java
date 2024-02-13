import java.net.Socket;
import java.io.*;

public class Client {
    public static void main(String[] args) {

        try
        {
            System.out.println("Client started");
            Socket clientSocket=new Socket("localhost",1999);
            BufferedReader userInput=new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out= new PrintWriter(clientSocket.getOutputStream(),true);
            // Înainte de bucla principală
            Thread receiveThread = new Thread(() -> {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println("Server: " + serverMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receiveThread.start();

// În bucla principală
            while (true) {
                System.out.println("Write a message");
                String str = userInput.readLine();
                out.println(str);

                if (str.equalsIgnoreCase("BYE"))
                    break;
            }
            clientSocket.close();
            userInput.close();
            out.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}