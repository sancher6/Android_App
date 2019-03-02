package edu.sancher6tcnj.raspberrypiclient;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.Dictionary;

public class MyRunHandler extends SQLiteOpenHelper {
    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "runDB.db";
    public static final String TABLE_NAME = "Runs";
    public static final String COLUMN_ID = "RunID";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_TIME = "Time";
    public static final String COLUMN_INSTR = "Instructions";

    //initialize database
    public MyRunHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID +
                "STRING PRIMARYKEY," + COLUMN_NAME + "TEXT )" + COLUMN_TIME + " TEXT )"+ COLUMN_INSTR + "TEXT )";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){}

    public String loadHandler(){
        String result = "";
        String query = "Select * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        while(cursor.moveToNext()){
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            result += String.valueOf(result_0) + " " + result_1 +
                    System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;
    }

    public void addHandler (Run run){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, run.getRunID());
        values.put(COLUMN_NAME, run.getRunName());
        values.put(COLUMN_TIME, run.getTime());
        values.put(COLUMN_INSTR, run.getInstr());
    }

    public Run findHandler(String runID, String runName){
        String query = "Select * FROM " + TABLE_NAME + "WHERE" + COLUMN_NAME + " = " + "'" + runName + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Run run = new Run();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            run.setRunID(cursor.getString(0));
            run.setRunName(cursor.getString(1));
            cursor.close();
        } else {
            run = null;
        }
        db.close();
        return run;
    }

    public boolean deleteHandler(String ID){
        boolean result = false;
        String query = "Select * FROM " + TABLE_NAME + "WHERE" + COLUMN_ID + "= '" + ID + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Run run = new Run();
        if (cursor.moveToFirst()) {
            run.setRunID(cursor.getString(0));
            db.delete(TABLE_NAME, COLUMN_ID + "=?",
                    new String[] {
                String.valueOf(run.getRunID())
            });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public boolean updateHandler(String ID, String runName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(COLUMN_ID, ID);
        args.put(COLUMN_NAME, runName);
        args.put();
        return db.update(TABLE_NAME, args, COLUMN_ID + "=" + ID, null) > 0;
    }

}
