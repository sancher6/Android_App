package edu.sancher6tcnj.raspberrypiclient;

import android.os.Handler;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.io.ObjectOutputStream;

/**
 * Created by peruv on 2/26/2019.
 */

public class Client extends Thread{
    private String ipaddress;
    private int portnum;
    private Socket socket;
    private ObjectOutputStream out;
    private Handler handler;

    private Client(String ipaddress, int portnum) {
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
}
