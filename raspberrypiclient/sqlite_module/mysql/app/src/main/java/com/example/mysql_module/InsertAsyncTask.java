package com.example.mysql_module;

import android.os.AsyncTask;

import com.example.mysql_module.Instructions;
import com.example.mysql_module.InstructionsDao;

public class InsertAsyncTask extends AsyncTask<Instructions, Void, Void> {
    private InstructionsDao mInstrDao;

    public InsertAsyncTask(InstructionsDao dao) {
        mInstrDao = dao;
    }

    @Override
    protected Void doInBackground(Instructions... runs){
        mInstrDao.insertInstructions(runs);
        return null;
    }
}
