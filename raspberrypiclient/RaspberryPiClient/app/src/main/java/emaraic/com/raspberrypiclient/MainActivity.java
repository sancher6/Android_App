package emaraic.com.raspberrypiclient;

import android.support.v7.app.AppCompatActivity;


import android.util.Log;
import android.view.MotionEvent;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import emaraic.com.raspberrypiclient.R;

import static emaraic.com.raspberrypiclient.GlobalApplication.makeToast;

/**
 * @author Richard Bustamante
 *         Website: https://sancher6.github.io/personal_website/
 *         Email : sancher6@tcnj.edu
 *         Created on: 10/29/2018
 */

public class MainActivity extends AppCompatActivity {
    private ImageButton f;
    private ImageButton r;
    private ImageButton l;
    private EditText ip, port;
    private Button connect;
    private OutputStream out;
//    private BufferedReader sIn;
    private Socket socket;
    private InputStream sIn;
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
                            ClientListener listener = new ClientListener();
                            listener.start();
                            Toast.makeText(MainActivity.this, "CONNECTED",
                                    Toast.LENGTH_LONG).show();
//                            ass();
                            connect.setBackgroundColor(getResources().getColor(R.color.connected));
                            connect.setText("Connected");
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
            f.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    // Get touch action.
                    int action = motionEvent.getAction();

                    if(action == MotionEvent.ACTION_DOWN && connect.getText().toString().equalsIgnoreCase("Connected")) {
                        // If pressed.
//                    makeToast("forward sending");
                        Log.d("Forward", "SENDING FORWARD");
                        sendInstr("forward");
                    }else if(action == MotionEvent.ACTION_UP && connect.getText().toString().equalsIgnoreCase("Connected")) {
                        // If released.
                    view.performClick();
//                    makeToast("forward sent");
                        Log.d("Forward", "FORWARD SENT");
                        sendInstr("done");
                    }
                    return false;
                }
            });
            f.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(connect.getText().toString().equalsIgnoreCase("Connected")){
                        makeToast("Forward");
                        sendInstr("forward");
                    } else{
                        makeToast("Not Connected");
                    }
                }
            });
            r.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    // Get touch action.
                    int action = motionEvent.getAction();

                    if(action == MotionEvent.ACTION_DOWN && connect.getText().toString().equalsIgnoreCase("Connected")) {
                        // If pressed.
//                    makeToast("forward sending");
                        Log.d("Right", "SENDING RIGHT");
                        sendInstr("right");
                    }else if(action == MotionEvent.ACTION_UP && connect.getText().toString().equalsIgnoreCase("Connected")) {
                        // If released.
                        view.performClick();
//                    makeToast("forward sent");
                        Log.d("Right", "RIGHT SENT");
                        sendInstr("done");
                    }
                    return false;
                }
            });
            r.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(connect.getText().toString().equalsIgnoreCase("Connected")){
                        makeToast("right");
                        sendInstr("right");
                    } else{
                        makeToast("Right");
                    }
                }
            });
            l.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    // Get touch action.
                    int action = motionEvent.getAction();

                    if(action == MotionEvent.ACTION_DOWN && connect.getText().toString().equalsIgnoreCase("Connected")) {
                        // If pressed.
//                    makeToast("forward sending");
                        Log.d("Left", "SENDING LEFT");
                        sendInstr("left");
                    }else if(action == MotionEvent.ACTION_UP && connect.getText().toString().equalsIgnoreCase("Connected")) {
                        // If released.
                        view.performClick();
//                    makeToast("forward sent");
                        Log.d("Left", "LEFT SENT");
                        sendInstr("done");
                    }
                    return false;
                }
            });
            l.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(connect.getText().toString().equalsIgnoreCase("Connected")){
                        makeToast("left");
                        sendInstr("left");
                    } else{
                        makeToast("Not Connected");
                    }
                }
            });
        }
    }//end of oncreate

    private void closeConnection() {
        try {
            out.write("close".getBytes("UTF-8"));
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
    void sendInstr(String instr) {
        try {
            out.write(instr.getBytes("UTF-8"));
//            out.flush();
//            out.write("end".getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
            showErrorsMessages("Error while sending command!!");
        }
    }

    void ass() {
        try {
            Log.d("ASS : ", "WE ARE IN THE ASS!");
//            Log.d("ASS : ", kappa);
            out.write("Connected".getBytes("UTF-8"));
            out.flush();
//            out.write("end".getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
            showErrorsMessages("Error while sending command!!");
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

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
                out = socket.getOutputStream();
                out.flush();
                Log.d("INPUTTTT : ", "we are going to listener");
                Log.d("INPUTTTT : ", "Starting listener");
                ass();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }//end of client class

    public class ClientListener extends Thread{
        private volatile boolean cancelled = false;
        private String msg = "";

        @Override
        public void run() {
            while (!cancelled) {
                try {
//                    socket = new Socket(InetAddress.getByName(ipaddress), portnum);
                    sIn = socket.getInputStream();
                    BufferedReader isr = new BufferedReader(new InputStreamReader(sIn, "UTF8"));
                    Log.d("IN TRY : ", isr.readLine());
                    while((msg = isr.readLine()) != null){
                        System.out.println(msg);
                        Log.d("INPUTTTT : ", msg);
                        if(msg.equalsIgnoreCase("Disconnecting")){
                            cancel();
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void cancel()
        {
            cancelled = true;
        }

        public boolean isCancelled() {
            return cancelled;
        }
    }
}
