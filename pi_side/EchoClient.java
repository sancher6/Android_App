// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.io.*;
import java.net.*;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class EchoClient {

    // private static final Logger LOG = LoggerFactory.getLogger(EchoClient.class);

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Scanner reader; 

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(),"UTF-8"), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),"UTF-8"));
            reader = new Scanner(System.in); 
            String inputLine = reader.nextLine();
            String response = sendMessage(inputLine); 
            while(!(response.equals("exit"))){
                System.out.println("Server Says :" + response); 
                inputLine = reader.nextLine();
                response = sendMessage(inputLine); 
            }
            stopConnection(); 
        } catch (IOException e) {
            // LOG.debug("Error when initializing connection", e);
            System.out.println(e.getMessage());
        }
    }

    public String sendMessage(String msg) {
        try {
            out.println(msg);
            return in.readLine();
        } catch (Exception e) {
            return null;
        }
    }

    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            reader.close(); 
        } catch (IOException e) {
            // LOG.debug("error when closing", e);
            System.out.println(e.getMessage());
        }

    }
    public static void main(String[] args) {
        EchoClient client = new EchoClient();
        client.startConnection("0.0.0.0",1986);        
    }
}