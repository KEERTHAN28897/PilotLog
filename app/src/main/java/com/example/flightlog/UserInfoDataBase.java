package com.example.flightlog;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserInfoDataBase extends SQLiteOpenHelper{

    public static final String TABLE_NAME = "LOG_TABLE";
    public static final String COL2 = "Commander";
    public static final String COL3 = "CoPilot";
    public static final String COL1 = "DATE";
    public static final String COL4 = "Source";
    public static final String COL5 = "Destination";
    public static final String COL6 = "ATD";
    public static final String COL7 = "ATA";
    public static final String COL8 = "DayTime";
    public static final String COL9 = "NightTime";
    public static final String COL10 = "AircraftType";
    public static final String COL11 = "FlightNumber";
    public static final String COL12 = "Time1";
    public static final String COL13 ="Time2";
    public static final String COL14 = "Time3";


    public UserInfoDataBase(@Nullable Context context) {
        super(context, "data.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    //this is called when version changes, say when you change db design
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long addOne(UserInfo Info) {
        Log.w("Warning", "In add one");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //Insert the date in milliseconds format in db
        LocalDate d = LocalDate.of(Info.getYear(), Info.getMonth(), Info.getDay());
        Instant instant = d.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Long milliseconds = instant.toEpochMilli();

        cv.put(COL1, milliseconds);
        cv.put(COL2, Info.getCommander());
        cv.put(COL3, Info.getCoPilot());
        cv.put(COL4, Info.getSource());
        cv.put(COL5, Info.getDestination());
        cv.put(COL6, Info.getStartHr() + ":" + Info.getStartMin());
        cv.put(COL7, Info.getStopHr() + ":" + Info.getStopMin());
        cv.put(COL8, Info.getDayTimeHr() + ":" + Info.getDayTimeMin());
        cv.put(COL9, Info.getNightTimeHr() + ":" + Info.getNightTimeMin());
        cv.put(COL10, Info.getAircraftType());
        cv.put(COL11, Info.getFlightNumber());
        cv.put(COL12, Info.getTime1());
        long row = db.insert(TABLE_NAME, null, cv);
        return row;
    }

    public String getEveryone() {
        StringBuilder sb = new StringBuilder();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY DATE DESC" ;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        String name = "not init";

        if(!cursor.moveToFirst()) {
            return null;
        }
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            sb.append(cursor.getLong(0)).append(";").append(COL2+ ": " + cursor.getString(1)).append(";").append(COL3+ ": " +
                    cursor.getString(2)).append(";").append("From: " + cursor.getString(3)).append(";").append("To: " +
                    cursor.getString(4)).append(";").append("ATA: " + cursor.getString(5) + ";").append("ATD: " +
                    cursor.getString(6) + ";").append("DAY: " + cursor.getString(7) + ";").append("NIGHT: " +
                    cursor.getString(8)+ ";").append("AIRCRAFT TYPE: " + cursor.getString(9) + ";").append("FLIGHT NUMBER: " +
                    cursor.getString(10) + "_");
        }
        cursor.close();
        db.close();
        return sb.toString();
    }

    public String getByDesignation(String name, String pos) {
        StringBuilder sb = new StringBuilder();
        String query = "";
        name = "'" + name + "'";
        if(pos.equals("Commander")) {
            query = "SELECT * FROM " + TABLE_NAME + " WHERE CoPilot = " + name + " ORDER BY DATE DESC" ;
        } else {
            query = "SELECT * FROM " + TABLE_NAME + " WHERE Commander = " + name + " ORDER BY DATE DESC" ;
        }
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        if(!cursor.moveToFirst()) {
            return null;
        }
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            sb.append(cursor.getLong(0)).append(";").append(COL2+ ": " + cursor.getString(1)).append(";").append(COL3+ ": " +
                    cursor.getString(2)).append(";").append("From: " + cursor.getString(3)).append(";").append("To: " +
                    cursor.getString(4)).append(";").append("ATA: " + cursor.getString(5) + ";").append("ATD: " +
                    cursor.getString(6) + ";").append("DAY: " + cursor.getString(7) + ";").append("NIGHT: " +
                    cursor.getString(8) +  ";").append("AIRCRAFT TYPE: " + cursor.getString(9) + ";").append("FLIGHT NUMBER: " +
                    cursor.getString(10) + "_");
        }
        cursor.close();
        db.close();
        return sb.toString();
    }

    public String getByDate(String date1, String date2) {
        StringBuilder sb = new StringBuilder();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE DATETIME(DATE/1000,  'unixepoch', 'localtime') BETWEEN " + date1 +  " AND " + date2 +
                                " ORDER BY DATE DESC";
        Log.e("query", query);
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        String name = "not init";

        if(!cursor.moveToFirst()) {
            return null;
        }
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            sb.append(cursor.getLong(0)).append(";").append(COL2+ ": " + cursor.getString(1)).append(";").append(COL3+ ": " +
                    cursor.getString(2)).append(";").append("From: " + cursor.getString(3)).append(";").append("To: " +
                    cursor.getString(4)).append(";").append("ATA: " + cursor.getString(5) + ";").append("ATD: " +
                    cursor.getString(6) + ";").append("DAY: " + cursor.getString(7) + ";").append("NIGHT: " + cursor.getString(8)+ "_");
        }
        cursor.close();
        db.close();
        return sb.toString();
    }

    public String getByMonth(Integer month) {
        String smonth = "";
        LocalDate currentdate = LocalDate.now();
        Integer year = currentdate.getYear();
        if(month == -1) {
            month = currentdate.getMonthValue();
        }

        //In correct format
        if(month < 10) {
            smonth = "0" + month.toString();
        } else {
            smonth = month.toString();
        }
        String date1 = "'" + year + "-" + smonth + "-" + "01" + " 00:00:00'";
        String date2 = "'" + year + "-" + smonth + "-" + "31" + " 23:59:59'";
        StringBuilder sb = new StringBuilder();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE DATETIME(DATE/1000,  'unixepoch', 'localtime') BETWEEN " + date1 +  " AND " + date2 +
                " ORDER BY DATE DESC";
        Log.e("query", query);
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        String name = "not init";

        if(!cursor.moveToFirst()) {
            return null;
        }
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            sb.append(cursor.getLong(0)).append(";").append(COL2+ ": " + cursor.getString(1)).append(";").append(COL3+ ": " +
                    cursor.getString(2)).append(";").append("From: " + cursor.getString(3)).append(";").append("To: " +
                    cursor.getString(4)).append(";").append("ATA: " + cursor.getString(5) + ";").append("ATD: " +
                    cursor.getString(6) + ";").append("DAY: " + cursor.getString(7) + ";").append("NIGHT: " + cursor.getString(8)+ "_");
        }
        cursor.close();
        db.close();
        return sb.toString();
    }

    public String getDayTimeByMonth(Integer month) {
        Long mins = new Long(0);
        String smonth = "";
        LocalDate currentdate = LocalDate.now();
        Integer year = currentdate.getYear();
        if(month == -1) {
            month = currentdate.getMonthValue();
        }

        //In correct format
        if(month < 10) {
            smonth = "0" + month.toString();
        } else {
            smonth = month.toString();
        }
        String date1 = "'" + year + "-" + smonth + "-" + "01" + " 00:00:00'";
        String date2 = "'" + year + "-" + smonth + "-" + "31" + " 23:59:59'";
        StringBuilder sb = new StringBuilder();
        String query = "SELECT DayTime FROM " + TABLE_NAME + " WHERE DATETIME(DATE/1000,  'unixepoch', 'localtime') BETWEEN " + date1 +  " AND " + date2 +
                " ORDER BY DATE DESC";
        Log.e("query", query);
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        if(!cursor.moveToFirst()) {
            return null;
        }
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String time = cursor.getString(0) ;
            Log.e("time", time);
            String[] time_split = time.split(":");
            mins = Long.valueOf(time_split[0])*60 + Long.valueOf(time_split[1]) + mins;
        }
        cursor.close();
        db.close();
        return mins.toString();
    }

    public String getNightTimeByMonth(Integer month) {
        Long mins = new Long(0);
        String smonth = "";
        LocalDate currentdate = LocalDate.now();
        Integer year = currentdate.getYear();
        if(month == -1) {
            month = currentdate.getMonthValue();
        }

        //In correct format
        if(month < 10) {
            smonth = "0" + month.toString();
        } else {
            smonth = month.toString();
        }
        String date1 = "'" + year + "-" + smonth + "-" + "01" + " 00:00:00'";
        String date2 = "'" + year + "-" + smonth + "-" + "31" + " 23:59:59'";
        StringBuilder sb = new StringBuilder();
        String query = "SELECT NightTime FROM " + TABLE_NAME + " WHERE DATETIME(DATE/1000,  'unixepoch', 'localtime') BETWEEN " + date1 +  " AND " + date2 +
                " ORDER BY DATE DESC";
        Log.e("query", query);
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        if(!cursor.moveToFirst()) {
            return null;
        }
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String time = cursor.getString(0) ;
            Log.e("time", time);
            String[] time_split = time.split(":");
            mins = Long.valueOf(time_split[0])*60 + Long.valueOf(time_split[1]) + mins;
        }
        cursor.close();
        db.close();
        return mins.toString();
    }

    public String getHours() {
        Long mins = new Long(0);
        Integer integer = 0;
        String query = "SELECT DayTime FROM LOG_TABLE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(!cursor.moveToFirst()) {
            return null;
        }
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String time = cursor.getString(0) ;
            Log.e("time", time);
            String[] time_split = time.split(":");
            mins = Long.valueOf(time_split[0])*60 + Long.valueOf(time_split[1]) + mins;
        }

        query = "SELECT NightTime FROM LOG_TABLE";
        cursor = db.rawQuery(query, null);
        if(!cursor.moveToFirst()) {
            return null;
        }
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String time = cursor.getString(0) ;
            Log.e("time", time);
            String[] time_split = time.split(":");
            mins = Long.valueOf(time_split[0])*60 + Long.valueOf(time_split[1]) + mins;
        }
        return mins.toString();
    }

    public String getAllPos(String pos) {
        StringBuffer sb = new StringBuffer();
        String query = "SELECT " + pos + " FROM LOG_TABLE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (!cursor.moveToFirst()) {
            return null;
        }
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String name = cursor.getString(0);
            sb.append(name);
            sb.append(":");
        }
        return sb.toString();
    }

    public String[] getAllFlightNumber() {
        StringBuffer sb = new StringBuffer();
        String query = "SELECT FlightNumber FROM LOG_TABLE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (!cursor.moveToFirst()) {
            return null;
        }
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String name = cursor.getString(0);
            sb.append(name);
            sb.append(";");
        }
        String [] ret = {};
        if(sb != null) {
            ret = sb.toString().split(";");
        }
        return ret;
    }

    public String getEveryoneByFlightNumber(String FlightNumber) {
        StringBuilder sb = new StringBuilder();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE FlightNumber='" + FlightNumber + "' LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        String name = "not init";

        if(!cursor.moveToFirst()) {
            return null;
        }
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            sb.append(cursor.getLong(0)).append(";").append(cursor.getString(1)).append(";").append(
                    cursor.getString(2)).append(";").append(cursor.getString(3)).append(";").append(
                    cursor.getString(4)).append(";").append(cursor.getString(5) + ";").append(
                    cursor.getString(6) + ";").append(cursor.getString(7) + ";").append(
                    cursor.getString(8)+ ";").append(cursor.getString(9) + ";").append(
                    cursor.getString(10) + ";");
        }
        cursor.close();
        db.close();
        return sb.toString();
    }

    public String getLastFlight() {
        Long mins = new Long(0);
        Integer integer = 0;
        String query = "SELECT FlightNumber, Source, Destination FROM LOG_TABLE ORDER BY DATE DESC LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(!cursor.moveToFirst()) {
            return null;
        }
        String FlightNumber = "NONE";
        for (cursor.moveToFirst(); !cursor.isAfterLast  (); cursor.moveToNext()) {
            FlightNumber = cursor.getString(0) + ";" + cursor.getString(1)  + ";" + cursor.getString(2) ;

        }
        return FlightNumber;

    }

    public void deleteRow(String date) {
        StringBuilder sb = new StringBuilder();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE DATE=" + date;
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);
    }


}

