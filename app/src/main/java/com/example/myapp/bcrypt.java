package com.example.myapp;

import android.util.Log;
import android.widget.Toast;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class bcrypt {
    private String password = "thomas123";

    private String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));

    public void checkPassword (String password) {
        if (BCrypt.checkpw(password, hashedPassword)) {
            Log.i("Info", "checkPassword: Password Match");
        } else {
            Log.i("Info", "checkPassword: No Match");
        }
    }
}
