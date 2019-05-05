package com.e.raspberrypiclient;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static com.e.raspberrypiclient.GlobalApplication.makeToast;

public class PreviousRuns extends AppCompatActivity {
    private static final String TAG = "Previous Runs Activity";
    DatabaseHelper mDatabaseHelper;
    private ListView mListView;
    private ArrayList<String> stringArrayList;
    private ArrayAdapter<String> stringArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_runs);

        mListView = (ListView)findViewById(R.id.list);

        mListView.setAdapter(stringArrayAdapter);

        mDatabaseHelper = new DatabaseHelper(this);
        final String[] instr = getResources().getStringArray(R.array.instructions);

        populateListView();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean calCheck = mDatabaseHelper.findName("Calibration(distance)") || mDatabaseHelper.findName("Calibration(angle)");
                //calibration found
                if(!calCheck){
                    String longInstr = mDatabaseHelper.getInstr(mListView.getAdapter().getItem(position).toString());
//                    makeToast("Selected: " + mListView.getAdapter().getItem(position).toString());
                    Intent i = new Intent(PreviousRuns.this, ManualOverride.class);
                    Log.d(TAG,longInstr);
                    i.putExtra("AUTO", longInstr);
                    startActivity(i);
                } else{makeToast("MUST CALIBRATE FIRST!");}
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //delete item
                mDatabaseHelper.deleteData(mDatabaseHelper.getId(mListView.getAdapter().getItem(position).toString()));
                stringArrayAdapter.remove(mListView.getAdapter().getItem(position).toString());
                stringArrayAdapter.notifyDataSetChanged();
                makeToast("Deleted");
                return false;
            }
        });
    }

    private void populateListView(){
        Log.d(TAG, "populateListView: Displaying Data within the ListView");
        //get the data and append to a list
        Cursor data = mDatabaseHelper.getData();

        stringArrayList = new ArrayList<>();
        if(data.getCount() == 0){
            makeToast("Database is Empty!");
        } else{
            while(data.moveToNext()){
                //get data from column 1 added to list
                stringArrayList.add(data.getString(1));

                //create the list adapter and set the adapter
                stringArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, stringArrayList);
                mListView.setAdapter(stringArrayAdapter);
            }
        }
    }
    @Override
    public void onBackPressed(){
//        Log.d("BACK BUTTON PRESSED ", "DISCONNECTING");
        Intent intent = new Intent(PreviousRuns.this, SecondaryActivity.class);
        startActivity(intent);
    }
}