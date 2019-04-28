package com.e.raspberrypiclient;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static com.e.raspberrypiclient.GlobalApplication.makeToast;

public class PreviousRuns extends AppCompatActivity {
    private static final String TAG = "Previous Runs Activity";
    DatabaseHelper mDatabaseHelper;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_runs);

        mListView = (ListView)findViewById(R.id.list);
        mDatabaseHelper = new DatabaseHelper(this);
        final String[] instr = getResources().getStringArray(R.array.instructions);

        populateListView();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                makeToast(mListView.getAdapter().getItem(position).toString());
            }
        });
    }

    private void populateListView(){
        Log.d(TAG, "populateListView: Displaying Data within the ListView");

        //get the data and append to a list
        Cursor data = mDatabaseHelper.getData();

        ArrayList<String> listData = new ArrayList<>();
        if(data.getCount() == 0){
            makeToast("Database is Empty!");
        } else{
            while(data.moveToNext()){
                //get data from column 1 added to list
                listData.add(data.getString(1));
                //create the list adapter and set the adapter
                ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
                mListView.setAdapter(adapter);
            }
        }
    }
}