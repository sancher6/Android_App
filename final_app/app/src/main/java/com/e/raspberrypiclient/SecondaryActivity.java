package com.e.raspberrypiclient;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static com.e.raspberrypiclient.GlobalApplication.makeToast;

public class SecondaryActivity extends AppCompatActivity {
    private Button remote;
    private Button set_instructions;
    private Button previous_runs;
    private Button dc;
    private Button more;
    private Button calibrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);

        //Buttons are all set
        remote = (Button)findViewById(R.id.remote_control);
        calibrate = (Button)findViewById(R.id.calibrate);
        set_instructions = (Button)findViewById(R.id.set_instructions);
        previous_runs = (Button)findViewById(R.id.previous_runs);
        dc = (Button)findViewById(R.id.disconnect);
        more = (Button)findViewById(R.id.more);

        //onClickListeners for Buttons
        remote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("Remote Control : ", "REMOTE CONTROL BUTTON PRESSED");
                Intent intent = new Intent(SecondaryActivity.this, ManualOverride.class);
                startActivity(intent);
            }
        });

        calibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("Calibrate : ", "CALIBRATE BUTTON PRESSED");
                Intent intent = new Intent(SecondaryActivity.this, Calibrate.class);
                startActivity(intent);
            }
        });

        set_instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d(" : ", "SET INSTRUCTIONS BUTTON PRESSED");
                Intent intent = new Intent(SecondaryActivity.this, SetInstructions.class);
                startActivity(intent);
            }
        });

        previous_runs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d(" : ", "PREVIOUS RUNS BUTTON PRESSED");
                Intent intent = new Intent(SecondaryActivity.this, PreviousRuns.class);
                startActivity(intent);
            }
        });

        dc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d(" : ", "DISCONNECT BUTTON PRESSED");
                Intent intent = new Intent(SecondaryActivity.this, MainActivity.class);
                makeToast("Disconnecting ...");
                startActivity(intent);
            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
////                Log.d(" : ", "MORE BUTTON PRESSED");
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://engprojects.tcnj.edu/rccar19/the-team/"));
                startActivity(browserIntent);
            }
        });
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(SecondaryActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
