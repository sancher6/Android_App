package emaraic.com.raspberrypiclient;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import java.io.IOException;
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

public class MainActivity extends AppCompatActivity {
    private ToggleButton state;
    private ImageButton f;
    private ImageButton r;
    private ImageButton l;
    private EditText ip, port;
    private Button connect;
    private ObjectOutputStream out;
    private Socket socket;
    private String ipaddress;
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
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here
            super.onCreate(savedInstanceState);setContentView(R.layout.activity_main);


            pattern = Pattern.compile(IPADDRESS_PATTERN);
            handler = new Handler();
            f = (ImageButton) findViewById(R.id.up);
            r = (ImageButton) findViewById(R.id.right);
            l = (ImageButton) findViewById(R.id.left);
            ip = (EditText) findViewById(R.id.ip);
            port = (EditText) findViewById(R.id.port);
            connect = (Button) findViewById(R.id.connect);

            changeButtonState(false);

            connect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (connect.getText().toString().equalsIgnoreCase("Connect")) {
                        try {
                            ipaddress = ip.getText().toString();
                            if (ipaddress.isEmpty()) {
                                ipaddress = "192.168.4.1";
                            }
                            if (!checkIP(ipaddress))
                                throw new UnknownHostException(port + "is not a valid IP address");
                            String check = "";
                            check = port.getText().toString();
                            if (check.isEmpty()) {
                                check = "22";
                            }
                            portnum = Integer.parseInt(check);
                            if (portnum > 65535 && portnum < 0)
                                throw new UnknownHostException(port + "is not a valid port number ");
                            Client client = new Client(ipaddress, portnum);
                            client.start();


                        } catch (UnknownHostException e) {
                            showErrorsMessages("Please enter a valid IP !! ");
                        } catch (NumberFormatException e) {
                            showErrorsMessages("Please enter valid port number !! ");
                        }
                    }else {
                        connect.setText("Connect");
                        changeButtonState(false);
                        closeConnection();
                    }
                }
            });
    }
        f.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(f.isPressed()){
                        return false;
                    }else{
                        f.setPressed(true);
                        state.setChecked(true);
                        lightOn(1);
                        return true;
                    }
                }
                return false;
            }
        });

        l.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(l.isPressed()){
                        return false;
                    }else{
                        l.setPressed(true);
                        state.setChecked(true);
                        lightOn(2);
                        return true;
                    }
                }
                return false;
            }
        });

        r.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(r.isPressed()){
                        return false;
                    }else{
                        r.setPressed(true);
                        state.setChecked(true);
                        lightOn(3);
                        return true;
                    }
                }
                return false;
            }
        });

        state.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if(state.isChecked()){
                        state.toggle();
                        lightOff(1);
                        lightOff(2);
                        lightOff(3);
                        return true;
                    }else{
                        return false;
                    }
                }
                return false;
            }
        });
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


    void changeButtonState(boolean state) {
        f.setPressed(state);
        r.setPressed(state);
        l.setPressed(state);
    }

    ////////////////////// light related methods /////////////
    void lightOn(int lednum) {
        try {
            out.writeObject(lednum + "1");
            out.flush();
            out.writeObject("end");
        } catch (IOException e) {
            e.printStackTrace();
            showErrorsMessages("Error while sending command!!");
        }
    }

    void lightOff(int lednum) {
        try {
            out.writeObject(lednum + "0");
            out.flush();
            out.writeObject("end");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void showErrorsMessages(String error) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Error!! ").setMessage(error).setNeutralButton("OK", null).create().show();
    }

    public boolean checkIP(final String ip) {
        matcher = pattern.matcher(ip);
        return matcher.matches();
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
                        connect.setText("Connect");
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
