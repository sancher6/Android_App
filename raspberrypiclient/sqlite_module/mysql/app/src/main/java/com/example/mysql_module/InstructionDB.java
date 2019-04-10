package com.example.mysql_module;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.mysql_module.Instructions;

@Database(entities = {Instructions.class}, version = 1)
public abstract class InstructionDB extends RoomDatabase{
    public static final String DATABASE_NAME = "instruction_db";

    private static InstructionDB instance;

    static InstructionDB getInstance(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    InstructionDB.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }
    public abstract InstructionsDao getInstructionsDao();
}
