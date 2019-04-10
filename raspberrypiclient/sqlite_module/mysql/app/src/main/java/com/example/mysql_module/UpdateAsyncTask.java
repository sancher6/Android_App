package com.example.mysql_module;

import android.os.AsyncTask;

import com.example.mysql_module.Instructions;
import com.example.mysql_module.InstructionsDao;

public class UpdateAsyncTask extends AsyncTask<Instructions, Void, Void>{
    private InstructionsDao mInstrDao;

    public UpdateAsyncTask(InstructionsDao dao) {
        mInstrDao = dao;
    }

    @Override
    protected Void doInBackground(Instructions... runs){
        mInstrDao.updateInstructions(runs);
        return null;
    }
}

