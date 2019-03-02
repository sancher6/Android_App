package edu.sancher6tcnj.raspberrypiclient;

import android.app.AlertDialog;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by peruv on 2/26/2019.
 */

public class Client extends Thread{
    private String ipaddress;
    private int portnum;
    private Socket socket;
    private ObjectOutputStream out;

    public Client(String ipaddress, int portnum) {
        this.ipaddress = ipaddress;
        this.portnum = portnum;
    }

    @Override
    public void run() {
        super.run();
        connectToServer(ipaddress, portnum);
    }

    private void connectToServer(String ip, int port) {

        try {
            socket = new Socket(InetAddress.getByName(ip), port);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    ////////////////////// light related methods /////////////
    void lightOn(int lednum) {
        try {
            out.writeObject(lednum + "1");
            out.flush();
            out.writeObject("end");
        } catch (IOException e) {
            e.printStackTrace();
//            showErrorsMessages("Error while sending command!!");
        }
    }

    void lightOff(int lednum) {
        try {
            out.writeObject(lednum + "0");
            out.flush();
            out.writeObject("end");
        } catch (IOException e) {
            e.printStackTrace();
//            showErrorsMessages("Error while sending command!!");
        }
    }
//    void showErrorsMessages(String error) {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
//        dialog.setTitle("Error!! ").setMessage(error).setNeutralButton("OK", null).create().show();
//    }
}
