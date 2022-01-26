package com.example.flightlog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.security.AlgorithmParameterGenerator;
import java.time.LocalDate;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static EditText UserName;
    private static EditText Password;
    private static Button Login;
    private static ImageButton AddUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LoginDetailsDatabase db = new LoginDetailsDatabase(MainActivity.this);
        String password = db.getPassword();

        if(password == null) {
            Intent intent = new Intent(MainActivity.this, AddUser.class);
            startActivity(intent);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("getPassword is ", "in on create");
        UserName = (EditText)findViewById(R.id.editTextTextPersonName);
        Password = (EditText)findViewById(R.id.editTextTextPassword);
        Login = (Button)findViewById(R.id.Submit);
        Login.setOnClickListener(this);
        AddUser = findViewById(R.id.adduser);
        AddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddUser.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        LoginDetailsDatabase db = new LoginDetailsDatabase(MainActivity.this);
        String password = db.getPassword();


        Toast.makeText(getApplicationContext(), password,
                Toast.LENGTH_LONG).show();
        switch (view.getId()){
            case R.id.Submit: {
                if (UserName.getText().toString().equals("admin") && Password.getText().toString().equals(password)) {
                    Toast.makeText(getApplicationContext(), "Login Success",
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, Dashboard_main.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Login Denied",
                            Toast.LENGTH_LONG).show();
                }
            }
        }

    }
}