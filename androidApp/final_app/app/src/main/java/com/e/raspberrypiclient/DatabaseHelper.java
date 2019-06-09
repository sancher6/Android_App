package com.e.raspberrypiclient;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mylist.db";
    public static final String TABLE_NAME = "mylist_data";
    public static final String COL1 = "ID";
    public static final String COL2 = "NAME";
    public static final String COL3 = "INSTRUCTIONS";

    public DatabaseHelper(Context context){super(context,DATABASE_NAME,null,1);}


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NAME TEXT, INSTRUCTIONS TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addData(String name, String instructions){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,name);
        contentValues.put(COL3,instructions);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);

        return data;
    }

    public boolean findName(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);

        while(data.moveToNext()){
            //name is in database return false
            if(name.equals(data.getString(1))){
                data.close();
                return false;
            }
        }
        //name not in database, return true;
        data.close();
        return true;
    }

    public String getId(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        String temp = "";

        while(data.moveToNext()){
            if(name.equals(data.getString(1))){
                temp = data.getString(0);
                data.close();
                return temp;
            }
        }
        return temp;
    }

    public boolean nameReplace(String id, String name, String instructions){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL1,id);
        contentValues.put(COL2,name);
        contentValues.put(COL3,instructions);

        long result = db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {id});

        if(result == -1){
            return false;
        }else{
            return true;
        }

    }

    public String getInstr(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);

        String temp = "";

        while(data.moveToNext()){
            if(name.equals(data.getString(1))){
                temp = data.getString(2);
                data.close();
                return temp;
            }
        }
        return temp;
    }
    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }
}
