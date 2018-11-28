package edu.sancher6tcnj.raspberrypiclient;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.Dictionary;

public class MyDBHandler extends SQLiteOpenHelper {
    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "runDB.db";
    public static final String TABLE_NAME = "Runs";
    public static final String COLUMN_ID = "runID";
    public static final String COLUMN_NAME = "runName";
    public static final String COLUMN_NAME = "ATTRIBUTES";
    public static final String COLUMN_NAME = "ATTRIBUTES";

    //initialize database
    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE" + TABLE_NAME + "(" + COLUMN_ID +
                "STRING PRIMARYKEY," + COLUMN_NAME + "TEXT )";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){}

    public String loadHandler(){
        String result = "";
        String query = "Select*FROM" + TABLE_NAME;
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
        values.put(COLUMN_NAME, run.);
    }

    public Run findHandler(Dictionary runID, String runName){}

    public boolean deleteHandler(int ID){}

    public boolean updateHandler(int ID, String runName){}

}
