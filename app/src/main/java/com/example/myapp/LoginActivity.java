package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    TextView logEmail;
    TextView logPassword;
    Button logBtn;
    TextView txtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logEmail = (TextView)findViewById(R.id.logEmail);
        logPassword = (TextView)findViewById(R.id.logPassword);
        logBtn = (Button)findViewById(R.id.logBtn);
        txtRegister = (TextView)findViewById(R.id.txtRegister);

        logBtn.setOnClickListener(v -> {
            boolean loginStatus = new Database().login(this, logEmail.getText().toString(), logPassword.getText().toString());
            if (loginStatus) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        });

        txtRegister.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}