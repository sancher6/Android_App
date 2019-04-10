package com.example.mysql_module;

import android.os.AsyncTask;

import com.example.mysql_module.Instructions;
import com.example.mysql_module.InstructionsDao;

public class DeleteAsyncTask extends AsyncTask<Instructions, Void, Void>{
    private InstructionsDao mInstrDao;

    public DeleteAsyncTask(InstructionsDao dao) {
        mInstrDao = dao;
    }

    @Override
    protected Void doInBackground(Instructions... runs){
        mInstrDao.delete(runs);
        return null;
    }
}

