package com.final_spii;

import android.util.Log;

import java.io.*;
import java.net.*;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static com.final_spii.GlobalApplication.makeToast;

public class Client {
    //variables for each new Client
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    //opens a socket and input and output streams for connection
    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(),"UTF-8"), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),"UTF-8"));
            String response = sendMessage("Connect");
            while(!(response.equalsIgnoreCase("connected"))){
                response = sendMessage("Connect");
            }
            makeToast("Connected");
        } catch (IOException e) {
            Log.d("Error",e.getMessage());
        }
    }

    //sends an instruction
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
        } catch (IOException e) {
            Log.d("Error",e.getMessage());
        }
    }
}
