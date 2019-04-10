package com.example.mysql_module;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.mysql_module.DeleteAsyncTask;
import com.example.mysql_module.InsertAsyncTask;
import com.example.mysql_module.UpdateAsyncTask;
import com.example.mysql_module.Instructions;

import java.util.List;

public class InstructionRepository {

    private InstructionDB mInstructiondb;

    public InstructionRepository(Context context){
        mInstructiondb = InstructionDB.getInstance(context);
    }
    public void insertInstructionTask(Instructions instr){
        new InsertAsyncTask(mInstructiondb.getInstructionsDao()).execute(instr);
    }

    public void updateNoteTask(Instructions instr){
        new UpdateAsyncTask(mInstructiondb.getInstructionsDao()).execute(instr);
    }

    public LiveData<List<Instructions>> retrieveNotesTask() {
        return mInstructiondb.getInstructionsDao().getInstructions();
    }

    public void deleteNoteTask(Instructions instr){
        new DeleteAsyncTask(mInstructiondb.getInstructionsDao()).execute(instr);
    }
}
