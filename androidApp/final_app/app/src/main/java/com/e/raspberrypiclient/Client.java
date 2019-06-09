package com.e.raspberrypiclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import android.util.Log;

public class Client extends Thread{
    private String ip;
    private int port;
    private OutputStreamWriter osw;
    private InputStreamReader isr;
    private String TAG = "CLIENT";
    private String toReturn;
    private String received;
    private String previous;
    public Socket clientSocket;
    public boolean connect;
    public boolean still;

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
            still= true;
            previous = "";
        } catch (IOException e) {
            disconnect();
        }
        PrintWriter out = new PrintWriter(osw, true);
        BufferedReader in = new BufferedReader(isr);
//      Send Connect Instruction
        setToReturn("Connect");
        while(true){
            //ensure toReturn is set to the correct String
            if((connect && toReturn.equalsIgnoreCase("Connect"))){
                //nothing
            }else{
                if(toReturn.equalsIgnoreCase(previous)) {
                    //nothing
                }else{
                    previous = toReturn;
                    out.println(getToReturn());
                    try {
                        received = in.readLine();
                    } catch (Exception e) {
                        received = "Connect";
                    }
                    //            Log.d(TAG,"SERVER SAYS: " + received);
                    if (received.equalsIgnoreCase("Disconnecting")) {
//                        Log.d(TAG, "Closing Connection");
                        try {
                            out.close();
                            in.close();
                            clientSocket.close();
                            connect = false;
                        } catch (IOException e) {
//                            Log.d("ERROR ", e.getMessage());
                            disconnect();
                        }
                        break;
                    } else if (received.equalsIgnoreCase("connected")) {
                        connect = true;
                    } else if (received.equalsIgnoreCase("off")) {
                        connect = false;
                        break;
                    }
                }

            }
        }
    }

    public void setToReturn(String message){
//        Log.d(TAG,message);
        toReturn = message;
    }
    public String getToReturn(){
        return toReturn;
    }

    public void disconnect(){
        try {
            osw.close();
            isr.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connect = false;
    }
}

