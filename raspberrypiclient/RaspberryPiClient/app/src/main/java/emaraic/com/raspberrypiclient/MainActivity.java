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
import android.widget.Toast;
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
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            state = (ToggleButton) findViewById(R.id.onoff);
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
                            Toast.makeText(MainActivity.this, "CONNECTED",
                                    Toast.LENGTH_LONG).show();

                            connect.setBackgroundColor(getResources().getColor(R.color.connected));
                            state.setChecked(false);
                        } catch (UnknownHostException e) {
                            showErrorsMessages("Please enter a valid IP !! ");
                        } catch (NumberFormatException e) {
                            showErrorsMessages("Please enter valid port number !! ");
                        }
                    }
                    else {
                        connect.setText("Connect");
                        changeButtonState(false);
                        closeConnection();
                    }
                }
            });
            f.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(state.isChecked())){
                        Toast.makeText(MainActivity.this, "MOVE FORWARD",
                                Toast.LENGTH_SHORT).show();
                        state.setChecked(true);
                        lightOn(3);
                    } else{
                        Toast.makeText(MainActivity.this, "BUSY",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

            l.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(state.isChecked())){
                        Toast.makeText(MainActivity.this, "BUSY",
                            Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(MainActivity.this, "ADJUST LEFT",
                                Toast.LENGTH_SHORT).show();
                        lightOn(2);

                    }
                }
            });

            r.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!(state.isChecked())) {
                        Toast.makeText(MainActivity.this, "BUSY",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "ADJUST RIGHT",
                                Toast.LENGTH_SHORT).show();
                        lightOn(1);
                    }
                }
            });

            state.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(state.isChecked())){
                        Toast.makeText(MainActivity.this, "STOPPED",
                                Toast.LENGTH_SHORT).show();
                        state.setChecked(false);
                        lightOff(3);
                    }
                    else{
                        state.setChecked(false);
                    }
                }
            });
        }
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

    public boolean checkOn(int num){
        if (num ==1){
            if(l.isPressed() || r.isPressed()){
                return false;
            }
        }
        else if (num == 2){
            if (f.isPressed() || r.isPressed()){
                return false;
            }
        }
        else if (num == 3){
            if(f.isPressed() || l.isPressed()){
                return false;
            }
        }
        return true;
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
