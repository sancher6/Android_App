package edu.sancher6tcnj.raspberrypiclient;

import android.os.Bundle;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private EditText ip, port;
    private Button connect;
    private String ipaddress;
    private ObjectInputStream in;
    private int portnum;
    private Matcher matcher;
    private Pattern pattern;
    static ObjectOutputStream out;
    static Socket socket;
    static Handler handler;
    private static final String IPADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    public void createActivity() {
        this.ip = ip;
        this.port = port;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int SDK_INT = Build.VERSION.SDK_INT;
        if (SDK_INT > 8){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            pattern = Pattern.compile(IPADDRESS_PATTERN);
            handler = new Handler();

            ip = findViewById(R.id.ip);
            port = findViewById(R.id.port);
            connect = findViewById(R.id.connect);

            connect.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if (connect.getText().toString().equalsIgnoreCase("Connect")){
                        try {
                            ipaddress = ip.getText().toString();
                            if (!checkIP(ipaddress))
                                throw new UnknownHostException(port + "is not a valid IP Address");
                            portnum = Integer.parseInt(port.getText().toString());
                            if (portnum > 65535 && portnum < 0)
                                throw new UnknownHostException(port + "is not a valid port number");
                            Client client = new Client(ipaddress, portnum);
                            client.start();
                            startActivity(new Intent(MainActivity.this,SecondaryActivity.class));
                        } catch (UnknownHostException e){
                            showErrorsMessages("Please enter a Valid IP!!");
                        } catch (NumberFormatException e){
                            showErrorsMessages("Please enter a Valid port number!!");
                        }
                    } else{
                        connect.setText("Connect");
                        closeConnection();
                    }
                }
            });
        }
    }
    public int get_port(){
        return portnum;
    }
    public String get_IP(){
        return ipaddress;
    }

    public void doAction(int action){
        //forward action = 1
        //left    action = 2
        //right   action = 3
        //back    action = 4
        try{
            out.writeObject(action + "1");
            out.flush();
            out.writeObject("end");
        } catch (IOException e){
            e.printStackTrace();
            showErrorsMessages("Error while sending Command!!");
        }
    }

    public void stopAction(int action) {
        //forward action = 1
        //left    action = 2
        //right   action = 3
        //back    action = 4
        try{
            out.writeObject(action + "0");
            out.flush();
            out.writeObject("end");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void closeConnection (){
        try{
            out.writeObject("close");
            out.close();
            in.close();
            socket.close();
        } catch (IOException ex){
            ex.printStackTrace();
            showErrorsMessages(ex.getMessage());
        }
    }
    void showErrorsMessages (String error){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Error!!").setMessage(error).setNeutralButton("OK",null).create().show();
    }
    public boolean checkIP (final String ip){
        matcher = pattern.matcher(ip);
        return matcher.matches();
    }
    // Creates a Client Thread Class
    static class Client extends Thread {
        private String ipaddress;
        private int portnum;

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
                System.out.println("Created Socket");
                out = new ObjectOutputStream(socket.getOutputStream());
                out.flush();
                handler.post(new Runnable() {
                    public void run() {
                        connect.setText("Close");
                    }
                });
            }catch (IOException e){
                e.printStackTrace();
                handler.post(new Runnable() {
                    public void run() {
                        showErrorsMessages("Unknown Host!!");
                    }
                });
            }
        }

    }//end of client class
}
