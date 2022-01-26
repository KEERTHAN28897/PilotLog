package com.example.flightlog;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class LoginDetailsDatabase extends SQLiteOpenHelper{

    public static final String TABLE_NAME = "LOGIN";
    public static final String COL2 = "EMAILID";
    public static final String COL3 = "PASSWORD";
    public static final String COL1 = "NAME";
    public static final String COL4 = "DESIGNATION";
    public static final String COL5 = "PHONENO";

    public LoginDetailsDatabase(@Nullable Context context) {
        super(context, "data.db", null, 1);
    }

    //first db, so create everything here
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + TABLE_NAME + "(NAME STRING , " +
                "EMAILID STRING, PASSWORD STRING, DESIGNATION TEXT, PHONENO STRING )";
        db.execSQL(createTableStatement);

        createTableStatement = "CREATE TABLE  LOG_TABLE (DATE INTEGER , " +
                "Commander STRING, CoPilot STRING, Source STRING, Destination STRING, ATA STRING, ATD STRING, DayTime STRING, " +
                "NightTime STRING, AircraftType STRING, FlightNumber STRING, Time1 STRING, Time2 STRING, Time3 STRING)";
        db.execSQL(createTableStatement);


    }

    //this is called when version changes, say when you change db design
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public long addOne(LoginInfo Info) {
        Log.w("Warning", "In add one");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL1, Info.getName());
        Log.e("Conternt values ", "valur ther?" + Info.getName());
        cv.put(COL2, Info.getEmailId());
        cv.put(COL3, Info.getPassword());
        cv.put(COL4, Info.getDesignation());
        cv.put(COL5, Info.getPhoneNumber());
        long row = db.insert(TABLE_NAME, null, cv);
        return row;
    }

    public String getEveryone() {
        StringBuilder sb = new StringBuilder();
        String query = "SELECT * FROM " + TABLE_NAME ;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        String name = "not init";
        if(!cursor.moveToFirst()) {
            return null;
        }
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            sb.append(cursor.getLong(0)).append(";").append(COL2+ ": " + cursor.getString(1)).append(";").append(COL3+ ": " +
                    cursor.getString(2)).append(";").append("From: " + cursor.getString(3)).append(";").append("To: " +
                    cursor.getString(4)).append("-");
        }
        cursor.close();
        db.close();
        return sb.toString();
    }

    public String getPassword() {
        String query = "SELECT * FROM " + TABLE_NAME ;
        SQLiteDatabase db = this.getReadableDatabase();
        StringBuilder sb = new StringBuilder();

        Cursor cursor = db.rawQuery(query, null);
        if(!cursor.moveToFirst()) {
            return null;
        }
        cursor.moveToFirst();
        sb.append(cursor.getString(2));
        Log.d("getPassword is ", sb.toString());
        db.close();
        String s = sb.toString();
        return s;

    }

    public String getName() {
        String query = "SELECT NAME FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        db.close();
        return cursor.getString(0);
    }

    public String getMail() {
        String query = "SELECT EMAILID FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, null);
        cursor.moveToFirst();
        db.close();
        return  cursor.getString(0);
    }

    public String getDesignation() {
        String query = "SELECT DESIGNATION FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, null);
        cursor.moveToFirst();
        db.close();
        return  cursor.getString(0);
    }

    public Integer getHours() {
        Integer integer = 0;
        //String query = "SELECT COUNT(*) FROM LOG_TABLE WHERE date BETWEEN datetime('now', 'start of month') AND datetime('now', 'localtime')";
        String query = "SELECT COUNT(*) FROM LOG_TABLE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        integer = cursor.getCount();
        db.close();
        return integer;
    }
}

