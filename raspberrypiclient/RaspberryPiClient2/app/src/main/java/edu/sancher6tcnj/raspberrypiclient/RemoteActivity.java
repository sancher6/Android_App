package edu.sancher6tcnj.raspberrypiclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.io.ObjectInputStream;

public class RemoteActivity extends AppCompatActivity {
    private Button f, l, r, b;
    private ToggleButton start_stop;
    private String ipaddress;
    private int portnum;
    private Client client;
    private ObjectInputStream in;
    private WebView camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*////////////////////////////////////////////////////////
        Activity Elements
        *////////////////////////////////////////////////////////

        camera = findViewById(R.id.stream);
        f = findViewById(R.id.f);
        l = findViewById(R.id.l);
        r = findViewById(R.id.r);
        b = findViewById(R.id.b);
        start_stop = findViewById(R.id.start_stop);

        camera.loadUrl("http://192.168.4.1:8000/");

        setContentView(R.layout.activity_remote);

        /*////////////////////////////////////////////////////////
        Remote Control Instructions
        *////////////////////////////////////////////////////////
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(start_stop.isChecked())){
                    Toast.makeText(RemoteActivity.this, "MOVE FORWARD",
                            Toast.LENGTH_SHORT).show();
                    start_stop.setChecked(true);
//                    lightOn(3);
                } else{
                    Toast.makeText(RemoteActivity.this, "BUSY",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(start_stop.isChecked())){
                    Toast.makeText(RemoteActivity.this, "BUSY",
                            Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(RemoteActivity.this, "ADJUST LEFT",
                            Toast.LENGTH_SHORT).show();
//                    lightOn(2);
                }
            }
        });

        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(start_stop.isChecked())) {
                    Toast.makeText(RemoteActivity.this, "BUSY",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RemoteActivity.this, "ADJUST RIGHT",
                            Toast.LENGTH_SHORT).show();
//                    lightOn(1);
                }
            }
        });

        start_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(start_stop.isChecked())){
                    Toast.makeText(RemoteActivity.this, "STOPPED",
                            Toast.LENGTH_SHORT).show();
                    start_stop.setChecked(false);
                }
                else{
                    start_stop.setChecked(false);
                }
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(start_stop.isChecked())) {
                    Toast.makeText(RemoteActivity.this, "BUSY",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RemoteActivity.this, "MOVE BACK",
                            Toast.LENGTH_SHORT).show();
//                    lightOn(1);
                }
            }
        });
    }
}
