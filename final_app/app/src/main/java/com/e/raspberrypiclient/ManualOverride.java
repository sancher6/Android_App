package com.e.raspberrypiclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;

import java.net.Socket;

public class ManualOverride extends AppCompatActivity {
    private WebView webView;
    private ImageButton f;
    private ImageButton b;
    private ImageButton l;
    private ImageButton r;
    private Button stop;
    private Button dc;
    private Client client;
    private String response;
    private Socket socket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_override);


        //Initialize the new Client for Manual Override
        client.startConnection("192.168.4.1",5900);
        Log.d("Connection : ", "SUCCESSFUL");

        webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://192.168.4.1:8000/");
//        webView.loadUrl("http://google.com");

        //ImageButtons are all set
        f = (ImageButton)findViewById(R.id.forward);
        b = (ImageButton)findViewById(R.id.back);
        l = (ImageButton)findViewById(R.id.left);
        r = (ImageButton)findViewById(R.id.right);
        stop = (Button)findViewById(R.id.stop);
        dc = (Button)findViewById(R.id.disconnect);

        //used to log server response
        response ="";


        //forward Held
        f.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //send forward as long as this is held
                    response = client.sendMessage("f");
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    v.performClick();
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
                    response = client.sendMessage("b");
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    v.performClick();
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
                    response = client.sendMessage("l");
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    v.performClick();
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
                    response = client.sendMessage("r");
                    return true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    v.performClick();
                    return false;
                }
                return false;
            }
        });
        //forward
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Command : ", "FORWARD BUTTON PRESSED");
                response = client.sendMessage("end");
                Log.d("Response from Server : ", response);
            }
        });

        //backward
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Command : ", "BACK BUTTON PRESSED");
                response = client.sendMessage("end");
                Log.d("Response from Server : ", response);
            }
        });

        //left
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Command : ", "LEFT BUTTON PRESSED");
                response = client.sendMessage("end");
                Log.d("Response from Server : ", response);
            }
        });

        //right
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Command : ", "RIGHT BUTTON PRESSED");
                response = client.sendMessage("end");
                Log.d("Response from Server : ", response);
            }
        });

        //stop
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Command : ", "STOP BUTTON PRESSED");
                response = client.sendMessage("stop");
                while(!(response.equalsIgnoreCase("stopping"))){
                    response = client.sendMessage("stop");
                }
                Log.d("Response from Server : ", response);
            }
        });

        //disconnect
        dc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Command : ", "DISCONNECT  BUTTON PRESSED");
                response = client.sendMessage("disconnect");
                while(!(response.equalsIgnoreCase("disconnecting"))) {
                    client.sendMessage("disconnect");
                }
                client.stopConnection();
            }
        });
    }
}
