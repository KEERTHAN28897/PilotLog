package com.example.flightlog;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.time.ZoneId;

public class FetchDetails extends AppCompatActivity {

    private TableLayout mTableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_details);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        String str="";
        if(b != null) {
            Integer val = (Integer) b.get("Data");
            if(val == 0) {
                UserInfoDataBase db = new UserInfoDataBase(FetchDetails.this);
                str = db.getEveryone();
            }
            if(val == 1) {
                String date1 = (String) b.get("Date1");
                String date2 = (String) b.get("Date2");
                UserInfoDataBase db = new UserInfoDataBase(FetchDetails.this);
                str = db.getByDate(date1, date2);
            }

            if(val == 2) {
                LoginDetailsDatabase db1 = new LoginDetailsDatabase(FetchDetails.this);
                String pos = db1.getDesignation();
                UserInfoDataBase db = new UserInfoDataBase(FetchDetails.this);
                String name = (String) b.get("Name");
                str = db.getByDesignation(name, pos);

            }

            if(val == 3) {
                LoginDetailsDatabase db1 = new LoginDetailsDatabase(FetchDetails.this);
                String pos = db1.getDesignation();
                UserInfoDataBase db = new UserInfoDataBase(FetchDetails.this);
                Integer month = (Integer) b.get("Month");
                str = db.getByMonth(month);

            }
        }

        mTableLayout = (TableLayout) findViewById(R.id.tableInvoices);

        if(str == null) {

            //Dummy row, should find a better way!
            for(int i = 0; i < 5; i++ ) {
                TableRow row1 = new TableRow(FetchDetails.this);
                TextView view1 = new TextView(getApplicationContext());
                view1.setPadding(0, 0, 0, 100);
                view1.setTextColor(Color.BLACK);
                //view1.setBackgroundColor(Color.BLACK);
                //view1.append(" ");
                row1.addView(view1);
                mTableLayout.addView(row1);
            }

            str = "No Data found for following result";
            TextView textView = new TextView(getApplicationContext());
            textView.setTextSize(20);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setText(str);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setTextColor(Color.BLACK);

            TableRow row = new TableRow(FetchDetails.this);
            row.addView(textView);
            mTableLayout.addView(row);


        } else {

            String[] rows = str.split("_");
            // mTableLayout.removeAllViews();
            for (int i = 0; i < rows.length; i++) {

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
                date.append(day.toString() + " " + month.toString() + " " + year.toString());
                mTableLayout.addView(date);


                for (int j = 1; j < cols.length; j++) {

                    TableRow row = new TableRow(FetchDetails.this);
                    TextView view = new TextView(getApplicationContext());
                    view.setTextSize(20);
                    view.setBackgroundColor(Color.LTGRAY);
                    view.setTextColor(Color.BLUE);
                    view.append(cols[j]);
                    row.addView(view);
                    mTableLayout.addView(row);

                }

                TableRow row = new TableRow(FetchDetails.this);
                Button delete = new Button(getApplicationContext());
                delete.setTextColor(Color.BLUE);
                delete.setText("Delete");
                row.addView(delete);
                mTableLayout.addView(row);

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        UserInfoDataBase db = new UserInfoDataBase(FetchDetails.this);
                        db.deleteRow(cols[0]);
                    }
                });

                //Dummy row, should find a better way!
                TableRow row1 = new TableRow(FetchDetails.this);
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

}