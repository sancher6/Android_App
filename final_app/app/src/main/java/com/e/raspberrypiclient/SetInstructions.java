package com.e.raspberrypiclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class SetInstructions extends AppCompatActivity {
    private DatabaseHelper myDB;
    private Button save;
    private Button add_instr;
    private Spinner instr_spinner;
    private ListView instrlist;
    private ArrayList<String> stringArrayList;
    private ArrayAdapter<String> stringArrayAdapter;
    private EditText distance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_instructions);

        add_instr = (Button)findViewById(R.id.add);
        save = (Button)findViewById(R.id.save);
        distance = (EditText)findViewById(R.id.dist);
        instr_spinner = (Spinner)findViewById(R.id.list);
        instrlist = (ListView)findViewById(R.id.listV);
        stringArrayList = new ArrayList<String>();
        myDB = new DatabaseHelper(this);

        stringArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, stringArrayList);
        instrlist.setAdapter(stringArrayAdapter);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(SetInstructions.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.instructions));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        instr_spinner.setAdapter(myAdapter);

        //to get spinner selected item
        // String text = instr_spinner.getSelectedItem().toString();

        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });

        add_instr.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                stringArrayList.add(instr_spinner.getSelectedItem().toString() + " ---- " + distance.getText().toString());
                stringArrayAdapter.notifyDataSetChanged();
            }
        });
    }
}
