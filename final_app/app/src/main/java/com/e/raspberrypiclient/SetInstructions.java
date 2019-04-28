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

import static com.e.raspberrypiclient.GlobalApplication.makeToast;

public class SetInstructions extends AppCompatActivity {
    private DatabaseHelper myDB;
    private Button save;
    private Button add_instr;
    private Spinner instr_spinner;
    private ListView instrlist;
    private ArrayList<String> stringArrayList;
    private ArrayAdapter<String> stringArrayAdapter;
    private EditText distance;
    private EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_instructions);

        add_instr = (Button)findViewById(R.id.add);
        save = (Button)findViewById(R.id.save);
        distance = (EditText)findViewById(R.id.dist);
        name = (EditText)findViewById(R.id.name);
        instr_spinner = (Spinner)findViewById(R.id.list);
        instrlist = (ListView)findViewById(R.id.listV);
        stringArrayList = new ArrayList<String>();
        myDB = new DatabaseHelper(this);


//      THIS IS FOR THE LIST//////////////////////////////////////////////////////////////////////
        stringArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, stringArrayList);
        instrlist.setAdapter(stringArrayAdapter);

//      THIS IS FOR THE SPINNER//////////////////////////////////////////////////////////////////////
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(SetInstructions.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.instructions));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        instr_spinner.setAdapter(myAdapter);

        //to get spinner selected item
        // String text = instr_spinner.getSelectedItem().toString();

        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(stringArrayList.isEmpty()){
                    makeToast("MUST FIRST ADD INSTRUCTIONS!");
                } else{
                    if(name.getText().toString().length() < 1) {
                        makeToast("MUST ENTER A RUN NAME IN ORDER TO SAVE!");
                    } else{
                        boolean namecheck = myDB.findName(name.getText().toString());

                        if(namecheck){
                            boolean insertData = myDB.addData(name.getText().toString(), getAll(stringArrayList));

                            if(insertData){
                                makeToast("Successfully Saved Run!");
                            } else{
                                makeToast("Error Saving Run");
                            }
                        } else{
                            boolean replace = myDB.nameReplace("1",name.getText().toString(), getAll(stringArrayList));
                            makeToast("Name already exists, overwriting");
                        }
                    }
                }
            }
        });

        add_instr.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(distance.getText().toString().length() < 1){
                    makeToast("Must enter a valid Distance!!");
                }else{
                    stringArrayList.add(instr_spinner.getSelectedItem().toString() + " " + distance.getText().toString());
                    stringArrayAdapter.notifyDataSetChanged();
                    distance.setText("");
                }
            }
        });
    }
    public String getAll(ArrayList<String> instructions){
        String temp = "";
        for(int i = 0; i < instructions.size(); i++){
            temp += instructions.get(i);
        }
        return temp;
    }
}
