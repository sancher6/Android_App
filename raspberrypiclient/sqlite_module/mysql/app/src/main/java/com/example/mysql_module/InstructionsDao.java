package com.example.mysql_module;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.mysql_module.Instructions;

import java.util.List;

@Dao
public interface InstructionsDao {

    @Insert
    long[] insertInstructions(Instructions... notes);

    @Query("SELECT * FROM runs")
    LiveData<List<Instructions>> getInstructions();

    @Delete
    int delete(Instructions... runs);

    @Update
    int updateInstructions(Instructions... runs);
}
