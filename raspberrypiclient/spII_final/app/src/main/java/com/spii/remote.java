package com.spii;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;


import static com.spii.GlobalApplication.sendInstr;

public class remote extends AppCompatActivity implements View.OnClickListener{

    private Button dc;
    private Button stop;

    //    private Socket socket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);

        WebView webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl("http://google.com");
        webView.loadUrl("http://192.168.4.1:8000/");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        ImageButton fr = (ImageButton) findViewById(R.id.upr);
        ImageButton rr = (ImageButton) findViewById(R.id.rightr);
        ImageButton lr = (ImageButton) findViewById(R.id.leftr);
        ImageButton br = (ImageButton) findViewById(R.id.backr);

        Button dc = (Button) findViewById(R.id.dc);
        Button stop = (Button) findViewById(R.id.POWER);

        fr.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // Get touch action.
                int action = motionEvent.getAction();

                if(action == MotionEvent.ACTION_DOWN) {
                    // If pressed.
                    sendInstr(getString(R.string.forward));
                }else if(action == MotionEvent.ACTION_UP) {
                    // If released.
                    view.performClick();
                    sendInstr("done");
                }
                return false;
            }
        });
        br.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                    // Get touch action.
                int action = motionEvent.getAction();

                if(action == MotionEvent.ACTION_DOWN) {
                    // If pressed.
                    sendInstr(getString(R.string.backward));
                }else if(action == MotionEvent.ACTION_UP) {
                    // If released.
                    view.performClick();
                    sendInstr("done");
                }
                return false;
            }
        });
        lr.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // Get touch action.
                int action = motionEvent.getAction();

                if(action == MotionEvent.ACTION_DOWN) {
                    // If pressed.
                    sendInstr(getString(R.string.left));
                }else if(action == MotionEvent.ACTION_UP) {
                    // If released.
                    view.performClick();
                    sendInstr("done");
                }
                return false;
            }
        });
        rr.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // Get touch action.
                int action = motionEvent.getAction();

                if(action == MotionEvent.ACTION_DOWN) {
                    // If pressed.
                    sendInstr(getString(R.string.right));
                }else if(action == MotionEvent.ACTION_UP) {
                    // If released.
                    view.performClick();
                    sendInstr("done");
                }
                return false;
            }
        });

        dc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // Get touch action.
                int action = motionEvent.getAction();

                if(action == MotionEvent.ACTION_DOWN) {
                    // If pressed.
                    sendInstr(getString(R.string.right));
                }else if(action == MotionEvent.ACTION_UP) {
                    // If released.
                    view.performClick();
                    sendInstr("done");
                }
                return false;
            }
        });

        stop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // Get touch action.
                int action = motionEvent.getAction();

                if(action == MotionEvent.ACTION_DOWN) {
                    // If pressed.
                    sendInstr(getString(R.string.right));
                }else if(action == MotionEvent.ACTION_UP) {
                    // If released.
                    view.performClick();
                    sendInstr("done");
                }
                return false;
            }
        });

    }
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case (R.id.POWER):
                GlobalApplication.disconnect();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
            case (R.id.dc):
                GlobalApplication.sendInstr((getString(R.string.OFF).toLowerCase()));
            default:
                break;
        }
    }
    @Override
    public void onBackPressed(){
        GlobalApplication.disconnect();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
