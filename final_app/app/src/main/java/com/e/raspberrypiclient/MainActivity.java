package com.e.raspberrypiclient;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.e.raspberrypiclient.GlobalApplication.makeToast;

public class MainActivity extends AppCompatActivity {
    private Button Connect;
    private EditText ip,port;
    private String TAG = "MAIN";
    private String ipaddress;
    private int portnum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Buttons and Edit Texts are all set
        Connect = (Button)findViewById(R.id.connect);

        //EditText Fields
        ip = (EditText)findViewById(R.id.ip);
        port = (EditText)findViewById(R.id.port);
        //connect to pi
        Connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("Connect : ", "CONNECT BUTTON PRESSED");               }
                String check = ip.getText().toString();
                String check2 = port.getText().toString();
                if(!check.isEmpty() && !check2.isEmpty()){
                    ipaddress = check;
                    portnum = Integer.parseInt(check2);
                }else {
                    ipaddress = "192.168.4.1";
                    portnum = 4957;
                }
                makeToast("Connecting");
                Intent intent = new Intent(MainActivity.this, SecondaryActivity.class);
                intent.putExtra("ip",ip.getText().toString());
                intent.putExtra("port",port.getText().toString());
                startActivity(intent);
            }
        });
    }
}
