package edu.sancher6tcnj.raspberrypiclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondaryActivity extends AppCompatActivity {
    private Button remote_control, set_inst, prev_runs, disconnect, more;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);

        remote_control = findViewById(R.id.remote_control);
        set_inst = findViewById(R.id.set_instructions);
        prev_runs = findViewById(R.id.previous_runs);
        disconnect = findViewById(R.id.disconnect);
        more = findViewById(R.id.more);

        remote_control.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecondaryActivity.this,RemoteActivity.class));
            }
        });

    }
}
