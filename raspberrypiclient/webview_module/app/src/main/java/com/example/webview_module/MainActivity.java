package com.example.webview_module;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private Button power_button;
    private Socket socket;
    private ObjectOutputStream out;
    private Client client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        power_button = findViewById(R.id.POWER);
        webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://192.168.4.1:8000/");
//        webView.loadUrl("http://google.com");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        client = new Client("192.168.4.1", 22);
        client.start();

        //turn off pi
        power_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.lightOn(-1);
            }
        });
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