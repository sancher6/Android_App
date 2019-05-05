package com.e.raspberrypiclient;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

import static com.e.raspberrypiclient.GlobalApplication.makeToast;

public class ManualOverride extends AppCompatActivity {
    private WebView webView;
    private ImageButton f;
    private ImageButton b;
    private ImageButton l;
    private ImageButton r;
    private Button stop;
    private Button dc;
    private Button off;
    private Client client;
    AlertDialog.Builder builder;
    private String TAG = "MANUAL OVERRIDE";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_override);

        Intent in = getIntent();
        Bundle bundle = in.getExtras();

        webView = findViewById(R.id.webview);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://192.168.4.1:8000/");

        client = new Client("192.168.4.1",4957);
//        Log.d(TAG, "Client Created");
        client.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(bundle!=null){
            client.setToReturn("a");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String longinstr = (String)bundle.get("AUTO");
            client.setToReturn(longinstr);
//            client.setToReturn("end");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        client.setToReturn("m");


        //ImageButtons are all set
        f = (ImageButton)findViewById(R.id.forward);
        b = (ImageButton)findViewById(R.id.back);
        l = (ImageButton)findViewById(R.id.left);
        r = (ImageButton)findViewById(R.id.right);
        stop = (Button)findViewById(R.id.stop);
        dc = (Button)findViewById(R.id.dc);
        off = (Button)findViewById(R.id.off);
        builder = new AlertDialog.Builder(this);

        //forward Held
        f.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                //send forward as long as this is held
                client.setToReturn("f");
//                    Log.d("SENDING ", "FORWARD");
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
//                    Log.d("SENDING ", "BACKWARD");
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
//                Log.d("Command : ", "STOP BUTTON PRESSED");
            client.setToReturn("stop");
            }
        });

        //disconnect
        dc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("Command : ", "DISCONNECT  BUTTON PRESSED");
            client.setToReturn("Disconnect");
            }
        });

        //POWER OFF
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //Setting message manually and performing action on button click
            builder.setMessage("Do you want to Shutdown?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'YES' Button
                            client.setToReturn("Off");
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.cancel();
                        }
                    });
            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("ALERT");
            alert.show();
            }
        });
    }

    @Override
    public void onBackPressed(){
//        Log.d("BACK BUTTON PRESSED ", "DISCONNECTING");
        client.setToReturn("Disconnect");
        Intent intent = new Intent(ManualOverride.this, SecondaryActivity.class);
        startActivity(intent);
    }
}
