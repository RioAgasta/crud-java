package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class RegisterActivity extends AppCompatActivity {

    TextView regName;
    TextView regPhone;
    TextView regEmail;
    TextView regPassword;
    Button regBtn;
    ProgressBar progressBar;
    TextView txtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        name = (TextView)findViewById(R.id.regName);
//        phone = (TextView)findViewById(R.id.regPhone);
//        email = (TextView)findViewById(R.id.regEmail);
//        password = (TextView)findViewById(R.id.regPassword);
//        regBtn = (Button)findViewById(R.id.regBtn);

        regName = findViewById(R.id.regName);
        regPhone = findViewById(R.id.regPhone);
        regEmail = findViewById(R.id.regEmail);
        regPassword = findViewById(R.id.regPassword);
        regBtn = findViewById(R.id.regBtn);
        progressBar = findViewById(R.id.progressBar);
        txtLogin = findViewById(R.id.txtLogin);

        regBtn.setOnClickListener(v -> {
            boolean registerStatus = new Database().register(this, regName.getText().toString(), regPhone.getText().toString(), regEmail.getText().toString(), regPassword.getText().toString());
            if (registerStatus) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        });

        txtLogin.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }
}