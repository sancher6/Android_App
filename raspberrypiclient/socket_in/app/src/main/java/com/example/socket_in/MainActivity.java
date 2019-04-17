package com.example.socket_in;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class MainActivity extends AppCompatActivity {
    private EditText ip, port;
    private Button connect;
    private Button forward;
    private Button backward;
    private Button left;
    private Button right;
    private Button stop;
    private Button disconnect;
    private String ipaddress;
    private int portnum;
    private Pattern pattern;
    private Matcher matcher;
    private Handler handler;
    private Client client;
    private static final String IPADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pattern = Pattern.compile(IPADDRESS_PATTERN);
        handler = new Handler();
        ip = findViewById(R.id.ip);
        port = findViewById(R.id.port);
        connect = findViewById(R.id.connect);
        disconnect = findViewById(R.id.disconnect);
        forward = findViewById(R.id.forward);
        backward = findViewById(R.id.backward);
        left = findViewById(R.id.left);
        right = findViewById(R.id.right);
        stop = findViewById(R.id.stop);


        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    if (portnum > 65535 || portnum < 0)
                        throw new UnknownHostException(port + "is not a valid port number ");
                    client = new Client(ipaddress, portnum);
                    client.start();
                    Toast.makeText(MainActivity.this, "CONNECTED",
                            Toast.LENGTH_LONG).show();
//                    connect.setText("CONNECTED");
                } catch (UnknownHostException e) {
                    showErrorsMessages("Please enter a valid IP !! ");
                } catch (NumberFormatException e) {
                    showErrorsMessages("Please enter valid port number !! ");
                }
            }
        });
        disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check = "";
                check = connect.getText().toString();
                if(check == "CONNECTED"){
                    //DISCONNECT BITCHHHHH
                    client.instrOn("disconnect");
                }
            }
        });
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check = "";
                check = connect.getText().toString();
                if(check == "CONNECTED"){
                    //DISCONNECT BITCHHHHH
                    client.instrOn("forward");
                }
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check = "";
                check = connect.getText().toString();
                if(check == "CONNECTED"){
                    //DISCONNECT BITCHHHHH
                    client.instrOn("left");
                }
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check = "";
                check = connect.getText().toString();
                if(check == "CONNECTED"){
                    //DISCONNECT BITCHHHHH
                    client.instrOn("right");
                }
            }
        });

    }
    void showErrorsMessages(String error) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Error!! ").setMessage(error).setNeutralButton("OK", null).create().show();
    }

    public boolean checkIP(final String ip) {
        matcher = pattern.matcher(ip);
        return matcher.matches();
    }
}
