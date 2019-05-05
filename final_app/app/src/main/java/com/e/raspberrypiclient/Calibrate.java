package com.e.raspberrypiclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import static com.e.raspberrypiclient.GlobalApplication.makeToast;

public class Calibrate extends AppCompatActivity {
    private DatabaseHelper myDB;
    private Spinner opt_spinner;
    private EditText time;
    private Button start;
    private Button stop;
    private Button save;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Client client;
    private String TAG = "MANUAL OVERRIDE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibrate);

        //initialize variables
        start = (Button)findViewById(R.id.Start);
        stop = (Button)findViewById(R.id.Stop);
        save = (Button)findViewById(R.id.Save);
        opt_spinner = (Spinner)findViewById(R.id.options);
        time = (EditText)findViewById(R.id.time);

        //Initialize the new Client for Manual Override
        client = new Client("192.168.4.1",4957);
        Log.d(TAG, "Client Created");
        client.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.setToReturn("m");
        //initialize database
        myDB = new DatabaseHelper(this);

        //      THIS IS FOR THE SPINNER//////////////////////////////////////////////////////////////////////
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(Calibrate.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.options));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        opt_spinner.setAdapter(myAdapter);

        //to get spinner selected item
        // String text = opt_spinner.getSelectedItem().toString();

        start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(time.getText().toString().length() < 1){
                    makeToast("Must enter a valid Time!!");
                }else{
//                    client.setToReturn(opt_spinner.getSelectedItem().toString() + " " + time.getText().toString());
                }
            }
        });

//        stop.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                client.setToReturn("stop");
//            }
//        });

        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(time.getText().toString().length() < 1) {
                    makeToast("Must enter a valid Time!!");
                }else {
//                    distance calibration
                    if(opt_spinner.getSelectedItem().toString().equalsIgnoreCase("Distance")) {
                        boolean namecheck = myDB.findName("Calibration(distance)");
                        if (namecheck) {
                            boolean insertData = myDB.addData("Calibration(distance)", "Forward " + time.getText().toString());
                            if (insertData) {
                                makeToast("Successfully Saved Distance Calibration!");
                            } else {
                                makeToast("Error Saving Run");
                            }
                        } else {
                            String temp = myDB.getId("Calibration(distance)");
                            boolean replace = myDB.nameReplace(temp, "Calibration(distance)", "Forward " + time.getText().toString());
                            makeToast("OVERWRITING PREVIOUS DISTANCE CALIBRATION");
                        }
                    }
//                    angle calibration
                    else{
                        boolean namecheck = myDB.findName("Calibration(angle)");
                        if (namecheck) {
                            boolean insertData = myDB.addData("Calibration(angle)", "Left " + time.getText().toString());
                            if (insertData) {
                                makeToast("Successfully Saved Angle Calibration!");
                            } else {
                                makeToast("Error Saving Run");
                            }
                        } else {
                            String temp = myDB.getId("Calibration(angle)");
                            boolean replace = myDB.nameReplace(temp, "Calibration(angle)", "Left " + time.getText().toString());
                            makeToast("OVERWRITING PREVIOUS ANGLE CALIBRATION");
                        }
                    }
                }
            }
        });
    }
}
