package com.spii;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;
import android.os.Handler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class GlobalApplication extends Application {

    private static Context appContext;
    private static Socket socket;
    private static String prev_instr;
    private static boolean f; // true if moving f
    private static boolean b; // true if moving b
    private static boolean l; // true if moving l
    private static boolean r; // true if moving r

    public static String ipaddr;
    public static int port;
    public static Client client;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();

        f = false; // true if moving f
        b = false; // true if moving b
        l = false; // true if moving l
        r = false; // true if moving r
        prev_instr = "done";

        Client client = new Client(getIP(),getPort());
        client.start();
    }
    public static Context getAppContext() {
        return appContext;
    }

    public static void makeToast(String Butter){
        Toast.makeText(getAppContext(), Butter,
                Toast.LENGTH_SHORT).show();
    }

    public static void setIP(String ip){
        ipaddr = ip;
    }
    public static void setPort(int ports) {
        port = ports;
    }

    public static String getIP(){
        return ipaddr;
    }
    public static int getPort(){
        return port;
    }
    public static void sendInstr(String instruction){
        String action = instrLogic(instruction);
        //can do current instr
        client.sendIt(action);
    }

    public static void disconnect(){
        client.closeConnection();
    }

    public static void showErrorsMessages(String error) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getAppContext());
        dialog.setTitle("Error!! ").setMessage(error).setNeutralButton("OK", null).create().show();
    }

/*
Button Press Action:
send instr, "done"

Button Hold Action:
send instr until let go then send "done"
 */
    //test 1: prev = f cur = f out = f
    //test 1: prev = b cur = f out = f
    //test 1: prev = l cur = f out = fl
    //test 1: prev = r cur = f out = fr
    //test 1: prev = fl cur = f out = f
    //test 1: prev = fr cur = f out = f

    public static String instrLogic(String curr_instr){
        if(prev_instr.equals("done"))return curr_instr;
        else{
            if(!curr_instr.equals("done")){
                if(curr_instr.contains("done")){
                    curr_instr = curr_instr.replace("done", "");
                    prev_instr += curr_instr;
                }else prev_instr += curr_instr;
            }else prev_instr += curr_instr;
        }
        return prev_instr;
    }
}
/////////////// client thread ////////////////////////////
class Client extends Thread {
    public String ipaddress;
    public int portnum;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
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

    public void sendIt(String msg){
        try{
            out.flush();
            out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
            handler.post(new Runnable() {
                public void run() {
                    GlobalApplication.showErrorsMessages("Unknown host!!");
                }
            });
        }
    }

    public void connectToServer(String ip, int port) {
        try {
            socket = new Socket(InetAddress.getByName(ip), port);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            handler.post(new Runnable() {
                public void run() {
                    GlobalApplication.showErrorsMessages("Unknown host!!");
                }
            });
        }

    }
    void closeConnection() {
        try {
            out.writeObject("close");
            out.close();
            in.close();
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            GlobalApplication.showErrorsMessages(ex.getMessage());
        }
    }//end of closeConnection

}//end of client class