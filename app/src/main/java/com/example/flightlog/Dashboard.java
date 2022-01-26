package com.example.flightlog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {

    private static Button fetch;
    private static Button log;
    private static Button delete;
    private Integer count = new Integer(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        fetch = (Button) findViewById(R.id.fetch);
        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, FetchDetails.class);
                startActivity(intent);
            }
        });

        log = (Button) findViewById(R.id.log);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, LogDetails.class);
                startActivity(intent);
            }
        });

        delete = (Button) findViewById(R.id.del);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, DeleteInfo.class);
                startActivity(intent);
            }
        });

        TextView t = (TextView)findViewById(R.id.display) ;
        UserInfoDataBase db = new UserInfoDataBase(Dashboard.this);

        t.setText(count.toString());
        count++;
        t.setTextSize(20);
        t.setTypeface(null, Typeface.BOLD_ITALIC);
    }

    @Override
    public void onClick(View view) {

    }
}