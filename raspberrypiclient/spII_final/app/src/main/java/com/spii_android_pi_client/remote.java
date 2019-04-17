package com.spii_android_pi_client;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.support.v4.app.SupportActivity.ExtraData;

import java.net.Socket;

public class remote extends AppCompatActivity implements View.OnClickListener{
    private ImageButton fr;
    private ImageButton rr;
    private ImageButton lr;
    private ImageButton br;
    MainActivity mainActivity;
    private Socket socket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);

        fr = (ImageButton) findViewById(R.id.upr);
        rr = (ImageButton) findViewById(R.id.rightr);
        lr = (ImageButton) findViewById(R.id.leftr);
        br = (ImageButton) findViewById(R.id.backr);

        fr.setOnClickListener(this);
        lr.setOnClickListener(this);
        rr.setOnClickListener(this);
        br.setOnClickListener(this);

        socket = mainActivity.getSocket();


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case (R.id.upr):
                mainActivity.sendInstr("forward");
                break;
            case (R.id.backr):
                mainActivity.sendInstr("back");
                break;
            case (R.id.leftr):
                mainActivity.sendInstr("left");
                break;
            case (R.id.rightr):
                mainActivity.sendInstr("right");
                break;
            case (R.id.POWER):
                mainActivity.sendInstr("stop");
                break;
            default:
                break;
        }
    }
}
