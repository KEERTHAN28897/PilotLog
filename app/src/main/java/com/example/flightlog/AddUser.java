package com.example.flightlog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddUser extends AppCompatActivity {

    private Button submit;
    private EditText Name;
    private EditText Password;
    private EditText EmailId;
    private EditText Designation;
    private EditText PhoneNumber;
    private LoginInfo loginInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        submit = (Button) findViewById(R.id.addsubmit);
        Name = (EditText) findViewById(R.id.username);
        Password = (EditText) findViewById(R.id.password);
        EmailId = (EditText) findViewById(R.id.email);
        Designation = (EditText) findViewById(R.id.designation);
        PhoneNumber = (EditText) findViewById(R.id.phonenumber);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginInfo = new LoginInfo(Name.getText().toString(), Password.getText().toString(), EmailId.getText().toString(),
                        Designation.getText().toString(), PhoneNumber.getText().toString());
                LoginDetailsDatabase db = new LoginDetailsDatabase(AddUser.this);
                Long row = db.addOne(loginInfo);
                Toast.makeText(getApplicationContext(), row.toString(),
                        Toast.LENGTH_LONG).show();

                String pwd = db.getPassword().toString();
            }
        });
    }
}