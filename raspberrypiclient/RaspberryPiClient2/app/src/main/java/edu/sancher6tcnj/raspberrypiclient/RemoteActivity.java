package edu.sancher6tcnj.raspberrypiclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class RemoteActivity extends AppCompatActivity {
    private Button f, l, r, b, start_stop;
    private String ipaddress;
    private int portnum;
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);

        f = findViewById(R.id.f);
        l = findViewById(R.id.l);
        r = findViewById(R.id.r);
        b = findViewById(R.id.b);
        start_stop = findViewById(R.id.start_stop);

        f.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_BUTTON_PRESS){
                    //send forward instruction
                }
                return false;
            }
        });
        l.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_BUTTON_PRESS){
                    //send forward instruction
                }
                return false;
            }
        });
        r.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_BUTTON_PRESS){
                    //send forward instruction
                }
                return false;
            }
        });
        b.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_BUTTON_PRESS){
                    //send forward instruction
                }
                return false;
            }
        });
        start_stop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_BUTTON_PRESS){
                    //check text on Button
                }
                return false;
            }
        });
    }
}
