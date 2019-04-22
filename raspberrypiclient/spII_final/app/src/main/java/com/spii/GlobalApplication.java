package com.spii;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import android.os.Handler;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class GlobalApplication extends Application {

    private static Context appContext;
    private static String prev_instr;

    public static String ipaddr;
    public static int port;
    public static Client client;
    private static Socket socket;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }
    public static Context getAppContext() {
        return appContext;
    }

    public static void makeToast(String Butter){
        Toast.makeText(getAppContext(), Butter,
                Toast.LENGTH_SHORT).show();
    }

    public static void makeClient(String ip, int portnum){
        ipaddr = ip;
        port = portnum;
        prev_instr = "";
        client = new Client(ipaddr, port);
//        socket = client.getSocket();
        client.start();
        prev_instr = "Connected";
    }
    public static void sendInstr(String instruction){
        Log.d("sendInstr","In method send Instr");

        String action = instrLogic(instruction);

        Log.d("sendInstr","instruction: " + instruction);
        //can do current instr
        try{
            Method m = Client.class.getDeclaredMethod("sendIt",String.class);
            m.setAccessible(true);
            m.invoke(client, action);
            prev_instr = action;
        } catch (NoSuchMethodException e){
            e.printStackTrace();
        }catch (InvocationTargetException e){
            e.getCause();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect(){
        try{
            Method m = Client.class.getDeclaredMethod("closeConnection", Socket.class);
            m.setAccessible(true);
            m.invoke(client, socket);
        } catch (NoSuchMethodException e){
            e.printStackTrace();
        }catch (InvocationTargetException e){
            e.getCause();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void showErrorsMessages(String error) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getAppContext());
        dialog.setTitle("Error!! ").setMessage(error).setNeutralButton("OK", null).create().show();
    }

    public static String instrLogic(String curr_instr){
        //not doing any instr rn send it
        Log.d("instrLogic", "doing logic");
        if(prev_instr.equalsIgnoreCase("Connected"))return curr_instr;
        else{
            //doing something, check that we are not stopping
            if(!curr_instr.equals("done")){
                //check if we are stopping some action
                if(curr_instr.contains("done")){
                    curr_instr = curr_instr.replace("done", "");
                    prev_instr = prev_instr.replace(curr_instr,"");
                }else prev_instr += curr_instr;
            }else prev_instr = curr_instr;
        }
        Log.d("Leaving instrLogic", "Returning: " + prev_instr);
        return prev_instr;
    }
}
/////////////// client thread ////////////////////////////
class Client extends Thread {
    private String ipaddress;
    private int portnum;
//    private InputStream in;
    private OutputStream out = null;
//    private BufferedReader in;
//    private PrintWriter out;
    private Handler handler;
    private Socket socket;
    private Socket temp;

    Client(String ip, int port) {
        this.ipaddress = ip;
        this.portnum = port;
    }

    @Override
    public void run() {
        super.run();
        connectToServer(ipaddress, portnum);
    }

    public Socket getSocket(){
        return this.socket;
    }

    private void sendIt(String msg){
        try{
            Log.d("SENNNDDD ITTTTTTT", "SENDING");
//            this.out.flush();
            out.write(msg.getBytes("UTF-8"));
//            this.out.write(msg);
        } catch (IOException e) {
            e.printStackTrace();
            handler.post(new Runnable() {
                public void run() {
                    GlobalApplication.showErrorsMessages("Unknown host!!");
                }
            });
        }
    }

    private void connectToServer(String ip, int port) {
        try {
            Log.d("CONNECTING", "Sending Connect instruction");
            socket = new Socket(InetAddress.getByName(ip), port);
//            socket.bind(new InetSocketAddress(ip, port));
            out = socket.getOutputStream();
            out.write("Connected".getBytes("UTF-8"));
//            this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"),true);
//            this.out.write("Connected");
            out.flush();
//            this.in = socket.getInputStream();
//            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
//            listenToServer();

//            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            handler.post(new Runnable() {
                public void run() {
                    GlobalApplication.showErrorsMessages("Unknown host!!");
                }
            });
        }
    }

//    private void listenToServer(){
//        try {
////            InputStreamReader isr = new InputStreamReader(in, "UTF8");
//            String readline = "";
////            int data = isr.read();
////            while(data != -1){
////                Log.d("READING IN : ","" + (char) data);
////                data = isr.read();
////            }
////            isr.close();
//            while((readline = in.readLine()) != null){
//                Log.d("MSG FROM PI : ", in.readLine());
//                if(in.readLine().equalsIgnoreCase("Hello Friend")) break;
//                else if(in.readLine().equalsIgnoreCase("Goodbye Friend") )break;
//            }
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
    private void closeConnection(Socket s) {
        try {
            Log.d("DISCONNECTING", "Sending Disconnect instruction");
//            out = socket.getOutputStream();
            out.write("Disconnect".getBytes("UTF-8"));
//            listenToServer();
//            out.flush();
//            out.write("Disconnect");
            out.close();
            s.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            GlobalApplication.showErrorsMessages(ex.getMessage());
        }
    }//end of closeConnection

}//end of client class