package com.example.socket_in;

import android.content.Context;
import android.os.Handler;
import android.widget.Button;

import com.example.socket_in.MainActivity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread{
    private String ipaddress;
    private int portnum;
    private Socket socket;
    private Socket socket_listener;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String msg;
    private Context context;
    private Handler handler;

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
            handler.post(new Runnable() {
                public void run() {
                    context =
                }
            });
//            in = new ObjectInputStream(socket.getInputStream());

            msg = "initialize";

            out.writeObject(msg);

//            while ((msg =(String) in.readObject()) != null) {
//                if(msg == "Hi!"){
//                    break;
//                }
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
//        } catch(ClassNotFoundException e){
//            e.printStackTrace();
//        }
    }
    ////////////////////// light related methods /////////////
    void instrOn(String instr) {
        try {
            out.flush();
            out.writeObject(instr);
            out.flush();
            out.writeObject("end");
        } catch (IOException e) {
            e.printStackTrace();
//            showErrorsMessages("Error while sending command!!");
        }
    }

    void instrOff(String instr) {
        try {
            out.writeObject(instr);
            out.flush();
            out.writeObject("end");
        } catch (IOException e) {
            e.printStackTrace();
//            showErrorsMessages("Error while sending command!!");
        }
    }
}
