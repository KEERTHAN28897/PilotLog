package com.example.flightlog;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;

public class DeleteInfo extends AppCompatActivity {

    private TableLayout mTableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_info);
        UserInfoDataBase db = new UserInfoDataBase(DeleteInfo.this);
        String str = db.getEveryone();
        mTableLayout = (TableLayout) findViewById(R.id.deltable);



        String[] rows  = str.split("_");
        // mTableLayout.removeAllViews();
        for(int  i = 0; i < rows.length; i++) {

            //show date
            String[] cols = rows[i].split(";");
            TextView date = new TextView(getApplicationContext());
            date.setTextColor(Color.BLACK);
            date.setTypeface(null, Typeface.BOLD_ITALIC);
            date.setBackgroundColor(Color.YELLOW);
            Long milliseconds = Long.parseLong(cols[0]);
            Instant instant = Instant.ofEpochMilli(milliseconds);
            LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            Integer day = localDate.getDayOfMonth();
            Month month = localDate.getMonth();
            Integer year = localDate.getYear();
            date.append(day.toString()+ " " + month.toString() + " " + year.toString());
            mTableLayout.addView(date);


            for(int j = 0; j < cols.length; j++) {

                TableRow row = new TableRow( DeleteInfo.this);
                TextView view = new TextView(getApplicationContext());
                view.setTextSize(20);
                view.setBackgroundColor(Color.LTGRAY);
                view.setTextColor(Color.BLUE);
                view.append(cols[j]);
                row.addView(view);
                mTableLayout.addView(row);

            }

            //Dummy row, should find a better way!
            TableRow row1 = new TableRow( DeleteInfo.this);
            TextView view1 = new TextView(getApplicationContext());
            view1.setPadding(0, 0, 0, 100);
            view1.setTextColor(Color.BLACK);
            //view1.setBackgroundColor(Color.BLACK);
            //view1.append(" ");
            row1.addView(view1);
            mTableLayout.addView(row1);
        }



    }
}