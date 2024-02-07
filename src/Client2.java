import java.net.Socket;
import java.io.*;

public class Client2 {
    public static void main(String[] args) {

        try
        {
            System.out.println("Client started");
            Socket clientSocket=new Socket("localhost",1224);
            BufferedReader userInput=new BufferedReader(new InputStreamReader(System.in));
            while(true){

                System.out.println("Write a message");
                String str = userInput.readLine();
                PrintWriter out= new PrintWriter(clientSocket.getOutputStream(),true);
                out.println(str);

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                System.out.println(in.readLine());

                if(str.equalsIgnoreCase("BYE"))
                    break;
            }
            clientSocket.close();
            userInput.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}