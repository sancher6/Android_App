package com.spii;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

public class remote extends AppCompatActivity implements View.OnClickListener{
    private ImageButton fr;
    private ImageButton rr;
    private ImageButton lr;
    private ImageButton br;
    private WebView webView;
    MainActivity mainActivity;
//    private Socket socket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);

        webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://google.com");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        fr = (ImageButton) findViewById(R.id.upr);
        rr = (ImageButton) findViewById(R.id.rightr);
        lr = (ImageButton) findViewById(R.id.leftr);
        br = (ImageButton) findViewById(R.id.backr);

        fr.setOnClickListener(this);
        lr.setOnClickListener(this);
        rr.setOnClickListener(this);
        br.setOnClickListener(this);

//        socket = mainActivity.getSocket();


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case (R.id.upr):
//                mainActivity.testInstr(g;("forward");
                mainActivity.testInstr(getString(R.string.forward), v);
                break;
            case (R.id.backr):
//                mainActivity.testInstr(g;("back");
                mainActivity.testInstr(getString(R.string.backward), v);
                break;
            case (R.id.leftr):
//                mainActivity.testInstr(g;("left");
                mainActivity.testInstr(getString(R.string.left), v);
                break;
            case (R.id.rightr):
//                mainActivity.testInstr(g;("right");
                mainActivity.testInstr(getString(R.string.right), v);
                break;
            case (R.id.POWER):
//                mainActivity.testInstr(g;("stop");
                mainActivity.testInstr(getString(R.string.OFF), v);
                break;
            default:
                break;
        }
    }
    @Override
    public void onBackPressed(){
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
