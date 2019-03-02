package edu.sancher6tcnj.raspberrypiclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
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
    private EditText ip, port;
    private Button connect;
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
            ip = (EditText) findViewById(R.id.ip);
            port = (EditText) findViewById(R.id.port);
            connect = (Button) findViewById(R.id.connect);

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
                            if (portnum > 65535 || portnum < 0)
                                throw new UnknownHostException(port + "is not a valid port number ");
                            client = new Client(ipaddress, portnum);
                            client.start();
                            Toast.makeText(MainActivity.this, "CONNECTED",
                                    Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(MainActivity.this, SecondaryActivity.class);
                            intent.putExtra("ip", ipaddress);
                            intent.putExtra("port", portnum);
                            startActivity(intent);
                        } catch (UnknownHostException e) {
                            showErrorsMessages("Please enter a valid IP !! ");
                        } catch (NumberFormatException e) {
                            showErrorsMessages("Please enter valid port number !! ");
                        }
                    }
                }
            });
        }
    }
    public Client getClient(){
        return (this.client);
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