package com.spii;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.spii.GlobalApplication.makeToast;
import static com.spii.GlobalApplication.sendInstr;
import static com.spii.GlobalApplication.showErrorsMessages;

/**
 * @author Richard Bustamante
 *         Website: https://sancher6.github.io/personal_website/
 *         Email : sancher6@tcnj.edu
 *         Created on: 10/29/2018
 */

public class MainActivity extends AppCompatActivity{
    private EditText ip, port;
    private Button Connect;
    public static String ipaddress;
    public static int portloc;
    public int portnum;
    private Pattern pattern;
    private static final String IPADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pattern = Pattern.compile(IPADDRESS_PATTERN);
        ip = (EditText) findViewById(R.id.ip);
        port = (EditText) findViewById(R.id.port);


        ImageButton f = (ImageButton) findViewById(R.id.up);
        ImageButton r = (ImageButton) findViewById(R.id.right);
        ImageButton l = (ImageButton) findViewById(R.id.left);
        ImageButton b = (ImageButton) findViewById(R.id.back);

        Button override = (Button) findViewById(R.id.MANUALOVERRIDE);
        Connect = (Button) findViewById(R.id.connect);

        // Listen to touch event.
        f.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // Get touch action.
                int action = motionEvent.getAction();

                if(action == MotionEvent.ACTION_DOWN) {
                    // If pressed.
                    sendInstr(getString(R.string.forward));
                }else if(action == MotionEvent.ACTION_UP) {
                    // If released.
//                    view.performClick();
                    sendInstr("done");
                }
                return false;
            }
        });

        b.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // Get touch action.
                int action = motionEvent.getAction();

                if(action == MotionEvent.ACTION_DOWN) {
                    // If pressed.
                    sendInstr(getString(R.string.backward));
                }else if(action == MotionEvent.ACTION_UP) {
                    // If released.
//                    view.performClick();
                    sendInstr("done");
                }
                return false;
            }
        });
        l.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // Get touch action.
                int action = motionEvent.getAction();

                if(action == MotionEvent.ACTION_DOWN) {
                    // If pressed.
                    sendInstr(getString(R.string.left));
                }else if(action == MotionEvent.ACTION_UP) {
                    // If released.
//                    view.performClick();
                    sendInstr("done");
                }
                return false;
            }
        });
        r.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // Get touch action.
                int action = motionEvent.getAction();

                if(action == MotionEvent.ACTION_DOWN) {
                    // If pressed.
                    sendInstr(getString(R.string.right));
                }else if(action == MotionEvent.ACTION_UP) {
                    // If released.
//                    view.performClick();
                    sendInstr("done");
                }
                return false;
            }
        });
        Connect.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // Get touch action.
                int action = motionEvent.getAction();

                if(action == MotionEvent.ACTION_DOWN) {
                    // If pressed.
                    if (Connect.getText().toString().equals("Connect")) {
                        try {
                            String check = "";
                            check = ip.getText().toString();
                            if (!check.isEmpty()){
                                if (!checkIP(check)) throw new UnknownHostException(port + "is not a valid IP address");
                                else ipaddress = check;
                            }else{
                                ipaddress = "192.168.4.1";
                            }
                            check = port.getText().toString();
                            if (!check.isEmpty()) {
                                portnum = Integer.parseInt(check);
                            }else{
                                portnum=22;
                            }
                            GlobalApplication.setIP(ipaddress);
                            GlobalApplication.setPort(portnum);
//                            Connect.setTextSize(14);
//                            Connect.setHeight(10);
//                            Connect.setWidth(50);
                            Connect.setText(getString(R.string.connected));
                            makeToast("CONNECTED");
                        } catch (UnknownHostException e) {
                            showErrorsMessages("Please enter a valid IP !! ");
                        } catch (NumberFormatException e) {
                            showErrorsMessages("Please enter valid port number !! ");
                        }
                    } else {
                        Connect.setText(getString(R.string.connect));
                        Connect.setBackgroundColor(Color.WHITE);
                        GlobalApplication.sendInstr(getString(R.string.disconnect));
                        makeToast(getString(R.string.disconnected));

                    }
                }else if(action == MotionEvent.ACTION_UP) {
                    // If released.
                    view.performClick();
                    sendInstr("done");
                }
                return false;
            }
        });
    }//end of on create

    public boolean checkIP(final String ip) {
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    public void onClick(View v) {
        switch(v.getId()){
            case(R.id.connect):
                break;
            case(R.id.MANUALOVERRIDE):
                Intent intent = new Intent(getApplicationContext(), remote.class);
                startActivity(intent);
            default:
                break;
        }
    }
}
