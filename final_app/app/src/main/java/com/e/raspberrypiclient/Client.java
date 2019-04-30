package com.e.raspberrypiclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import android.util.Log;

import static com.e.raspberrypiclient.GlobalApplication.makeToast;

public class Client extends Thread{
    //variables for each new Client
//    private Socket clientSocket;
//    private PrintWriter out;
//    private BufferedReader in;
    private String ip;
    private int port;
    private OutputStreamWriter osw;
    private InputStreamReader isr;
    private String TAG = "CLIENT";
    private String toReturn;
    private String received;
    public Socket clientSocket;
    public boolean connect;
//    Attempt 1: socket, pw, br created in manual override
//    public Client(Socket socket, PrintWriter pw, BufferedReader br) {
//        this.clientSocket = socket;
//        this.out = pw;
//        this.in = br;
//    }

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run(){
        Log.d(TAG, "Running");
        try {
            clientSocket = new Socket(ip,port);
            osw = new OutputStreamWriter(clientSocket.getOutputStream(),"UTF-8");
            isr = new InputStreamReader(clientSocket.getInputStream(),"UTF-8");
            connect = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter out = new PrintWriter(osw, true);
        BufferedReader in = new BufferedReader(isr);
//        out.println("Connect");
        setToReturn("Connect");
        while(true){
            //ensure toReturn is set to the correct String
            if(connect && toReturn.equalsIgnoreCase("Connect")){
                //nothing
//                Log.d(TAG,"STUCK AF");
            }else{
//                Log.d(TAG,getToReturn()+" GET TO RETURN VALUE");
                out.println(getToReturn());
                try {
//                    Log.d(TAG, "Waiting for line...");
                    received = in.readLine();
//                    Log.d(TAG, "Line received");
                } catch (Exception e) {
                    received = "Connect";
                }
//
            }
//            Log.d(TAG,"SERVER SAYS: " + received);
            if (received.equalsIgnoreCase("Disconnecting")) {
                Log.d(TAG, "Closing Connection");
                try {
                    out.close();
                    in.close();
                    clientSocket.close();
                    connect = false;
                } catch (IOException e) {
                    Log.d("ERROR ", e.getMessage());
                }
                break;
            }else if(received.equalsIgnoreCase("connected")){
                connect = true;
            }else if(received.equalsIgnoreCase("off")){
                connect = false;
                break;
            }
//            switch (received) {
//                case "connected\n":
//                    connect = true;
//                case "Obstacle":
////                    makeToast("OBSTACLE DETECTED");
//                case "Disconnecting":
//                    connect = false;
////                    makeToast("DISCONNECTING");
//                    break;
//            }
            received = "Connect";
//            Log.d(TAG,Boolean.toString(connect));

        }
//        Log.d(TAG, " WHAT THE ACTUAL FUCK ");
    }

    public void setToReturn(String message){
//        Log.d(TAG,message+" Method setTOReturn ");
        toReturn = message;
    }
    public String getToReturn(){
        return toReturn;
    }
}

