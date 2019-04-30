package com.e.raspberrypiclient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.NetworkOnMainThreadException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ManualOverride extends AppCompatActivity {
    private WebView webView;
    private ImageButton f;
    private ImageButton b;
    private ImageButton l;
    private ImageButton r;
    private Button stop;
    private Button dc;
    private Button off;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Client client;
    private String TAG = "MANUAL OVERRIDE";


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_override);

        //Initialize the new Client for Manual Override
        Log.d(TAG, "TRYING");

        //create client variables
//        try {
//            clientSocket = new Socket("192.168.4.1",4957);
//            out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(),"UTF-8"), true);
//            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),"UTF-8"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch(NetworkOnMainThreadException e){
//            e.printStackTrace();
//        }

//        Log.d(TAG , "SUCCESSFUL");
//        client = new Client(clientSocket, out, in);
        client = new Client("192.168.4.1",4957);
        Log.d(TAG, "Client Created");
        client.start();

        webView = findViewById(R.id.webview);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://192.168.4.1:8000/");

        //ImageButtons are all set
        f = (ImageButton)findViewById(R.id.forward);
        b = (ImageButton)findViewById(R.id.back);
        l = (ImageButton)findViewById(R.id.left);
        r = (ImageButton)findViewById(R.id.right);
        stop = (Button)findViewById(R.id.stop);
        dc = (Button)findViewById(R.id.dc);
        off = (Button)findViewById(R.id.off);

        //forward Held
        f.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //send forward as long as this is held
                    client.setToReturn("f");
                    Log.d("SENDING ", "FORWARD");
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP) {
                    client.setToReturn("end");
                    return false;
                }
                return false;
            }
        });

        b.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //send forward as long as this is held
                    client.setToReturn("b");
                    Log.d("SENDING ", "BACKWARD");
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    client.setToReturn("end");
                    return false;
                }
                return false;
            }
        });
        l.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //send forward as long as this is held
                    client.setToReturn("l");
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    client.setToReturn("end");
                    return false;
                }
                return false;
            }
        });
        r.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //send forward as long as this is held
                    client.setToReturn("r");
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    client.setToReturn("end");
                    return false;
                }
                return false;
            }
        });
        //stop
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Command : ", "STOP BUTTON PRESSED");
                client.setToReturn("stop");
            }
        });

        //disconnect
        dc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Command : ", "DISCONNECT  BUTTON PRESSED");
                client.setToReturn("Disconnect");
            }
        });

        //POWER OFF
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Command : ", "DISCONNECT  BUTTON PRESSED");
                client.setToReturn("Off");
            }
        });
    }

    @Override
    public void onBackPressed(){
        Log.d("BACK BUTTON PRESSED ", "DISCONNECTING");
        client.setToReturn("Disconnect");
        Intent intent = new Intent(ManualOverride.this, SecondaryActivity.class);
        startActivity(intent);
    }
}
