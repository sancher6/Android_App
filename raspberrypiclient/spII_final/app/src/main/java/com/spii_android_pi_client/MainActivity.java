package com.spii_android_pi_client;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;



import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Richard Bustamante
 *         Website: https://sancher6.github.io/personal_website/
 *         Email : sancher6@tcnj.edu
 *         Created on: 10/29/2018
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button state;
    private Button override;
    private ImageButton f;
    private ImageButton r;
    private ImageButton l;
    private ImageButton b;
    private EditText ip, port;
    private Button Connect;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    public static Socket socket;
    public String ipaddress;
    private int portnum;
    private Pattern pattern;
    private Matcher matcher;
    private Handler handler;
    private static final String IPADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ip = (EditText) findViewById(R.id.ip);
        port = (EditText) findViewById(R.id.port);


        f = (ImageButton) findViewById(R.id.up);
        r = (ImageButton) findViewById(R.id.right);
        l = (ImageButton) findViewById(R.id.left);
        b = (ImageButton) findViewById(R.id.back);
        state = (Button) findViewById(R.id.off);
        override = (Button) findViewById(R.id.MANUALOVERRIDE);
        Connect = (Button) findViewById(R.id.connect);

        Connect.setOnClickListener(this);
        state.setOnClickListener(this);
        override = (Button) findViewById(R.id.MANUALOVERRIDE);
        f.setOnClickListener(this);
        l.setOnClickListener(this);
        r.setOnClickListener(this);
        b.setOnClickListener(this);


        pattern = Pattern.compile(IPADDRESS_PATTERN);
        handler = new Handler();
    }//end of oncreate

    private void closeConnection() {
        try {
            out.writeObject("close");
            out.close();
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            showErrorsMessages(ex.getMessage());
        }
    }//end of closeConnection


    @Override
    protected void onStop() {
        super.onStop();
        closeConnection();
    }


    void changeButtonState(Boolean off) {
        f.setPressed(off);
        r.setPressed(off);
        l.setPressed(off);
        b.setPressed(off);
    }

    ////////////////////// light related methods /////////////
    void sendInstr(String instruction) {
        try {
            String msg = in.readObject().toString();
            if(!(instruction.equals(msg))){
                //do instr
                out.flush();
                out.writeObject(instruction);
            }
        } catch (IOException e) {
            e.printStackTrace();
            showErrorsMessages("Error while sending command!!");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static java.net.Socket getSocket() {
        return socket;
    }

    public boolean getState() {
        try {
            String msg = in.readObject().toString();
            if(msg.equalsIgnoreCase("done")){
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return false;
    }


    void showErrorsMessages(String error) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Error!! ").setMessage(error).setNeutralButton("OK", null).create().show();
    }

    public boolean checkIP(final String ip) {
        matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case(R.id.connect):
                if (Connect.getText().toString().equalsIgnoreCase("Connect")) {
                    try {
                        String check = "";
                        check = ip.getText().toString();
                        if (check.isEmpty()) {
                            ip.setText("192.168.4.1");
                            ipaddress = ip.getText().toString();
                        }
                        if (!checkIP(ipaddress))
                            throw new UnknownHostException(port + "is not a valid IP address");
                        check = port.getText().toString();
                        if (check.isEmpty()) {
                            port.setText("22");
                            portnum = Integer.parseInt(port.getText().toString());
                        }
                        if (portnum > 65535 && portnum < 0)
                            throw new UnknownHostException(port + "is not a valid port number ");
                        Client client = new Client(ipaddress, portnum);
                        client.start();
                        Connect.setText("Connected");
                        Connect.setBackgroundColor(Color.GREEN);
                        Toast.makeText(MainActivity.this, "CONNECTED",
                                Toast.LENGTH_LONG).show();
                    } catch (UnknownHostException e) {
                        showErrorsMessages("Please enter a valid IP !! ");
                    } catch (NumberFormatException e) {
                        showErrorsMessages("Please enter valid port number !! ");
                    }
                } else {
                    Connect.setText("Connect");
                    sendInstr("disconnect");
                    closeConnection();
                    Toast.makeText(MainActivity.this, "DISCONNECTED",
                            Toast.LENGTH_LONG).show();
                }
                break;
            case(R.id.up):
                sendInstr("forward");
                break;
            case(R.id.back):
                sendInstr("back");
                break;
            case(R.id.left):
                sendInstr("left");
                break;
            case(R.id.right):
                sendInstr("right");
                break;
            case(R.id.off):
                sendInstr("stop");
                break;
            case(R.id.MANUALOVERRIDE):
                //go to manual override
                sendInstr("stop");
                //go to remote
                Intent intent = new Intent(MainActivity.this, remote.class);
                startActivity(intent);
            default:
                break;
        }
    }
    /////////////// client thread ////////////////////////////
    private class Client extends Thread {
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
                out = new ObjectOutputStream(socket.getOutputStream());
                out.flush();
                handler.post(new Runnable() {
                    public void run() {
                        Connect.setText("Connect");
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                handler.post(new Runnable() {
                    public void run() {
                        showErrorsMessages("Unknown host!!");
                    }
                });
            }

        }

    }//end of client class
}
