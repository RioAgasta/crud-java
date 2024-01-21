package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    TextView id;
    TextView name;
    TextView age;
    Button addBtn;
    Button getBtn;
    Button updateBtn;
    Button deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = (TextView)findViewById(R.id.id);
        name = (TextView)findViewById(R.id.name);
        age = (TextView)findViewById(R.id.age);
        addBtn = (Button)findViewById(R.id.addBtn);
        getBtn = (Button)findViewById(R.id.getBtn);
        updateBtn = (Button)findViewById(R.id.updateBtn);
        deleteBtn = (Button)findViewById(R.id.deleteBtn);

        // Create
        addBtn.setOnClickListener(v -> {
            new Database().createUserData(this, name.getText().toString(), age.getText().toString());
        });

        // Read
        getBtn.setOnClickListener(v -> {
            new Database().readUserData(this, id.getText().toString());
        });

        // Update
        updateBtn.setOnClickListener(v -> {
            new Database().updateUserData(this, id.getText().toString(), name.getText().toString(), age.getText().toString());
        });

        // Delete
        deleteBtn.setOnClickListener(v -> {
            new Database().deleteUserData(this, id.getText().toString());
        });
    }

//    @SuppressLint("NewApi")
//    // Establish connection to database
//    public Connection connectionClass(){
//        Connection connection = null;
//        StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(tp);
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            String url = "jdbc:mysql://192.168.1.8:3306/myapp?user=root&password=";
//            connection = DriverManager.getConnection(url);
//        } catch (Exception exception) {
//            Log.e("Connection Error", exception.getMessage());
//        }
//        return connection;
//    }

//    public void readData(){
//        Connection connection = connectionClass();
//        try {
//            if (connection != null) {
//                String sqlSelect = "SELECT * FROM user WHERE id = '"+id.getText().toString()+"'";
//                Statement st = connection.createStatement();
//                ResultSet result = st.executeQuery(sqlSelect);
//                if (result.next()){
//                    do {
//                        name.setText(result.getString("name"));
//                        age.setText(result.getString("age"));
//                    } while (result.next());
//                    Toast.makeText(getApplicationContext(), "Read Data Successful", Toast.LENGTH_SHORT).show();
//                } else {
//                    name.setText("");
//                    age.setText("");
//                    Toast.makeText(this, "Read Data Failed", Toast.LENGTH_SHORT).show();
//                }
//            }
//        } catch (Exception exception) {
//            Log.e("Read Error", exception.getMessage());
//        }
//    }
//
//    public void createData(){
//        Connection connection = connectionClass();
//        try {
//            if (connection != null){
//                if (name.getText().toString().isEmpty() || age.getText().toString().isEmpty()){
//                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
//                } else {
//                    String sqlInsert = "INSERT INTO user(name, age) VALUES ('"+name.getText().toString()+"','"+age.getText().toString()+"')";
//                    Statement st = connection.createStatement();
//                    st.executeUpdate(sqlInsert);
//                    Toast.makeText(this, "Create Data Successful", Toast.LENGTH_SHORT).show();
//                }
//            }
//        } catch (Exception exception) {
//            Log.e("Create Error", exception.getMessage());
//        }
//    }
//
//    public void updateData(){
//        Connection connection = connectionClass();
//        try {
//            if (connection != null) {
//                String sqlUpdate = "UPDATE user SET name = '"+name.getText().toString()+"', age = '"+age.getText().toString()+"' WHERE id = '"+id.getText().toString()+"'";
//                Statement st = connection.createStatement();
//                st.executeUpdate(sqlUpdate);
//            }
//        } catch (Exception exception) {
//            Log.e("Update Error", exception.getMessage());
//        }
//    }
//
//    public void deleteData(){
//        Connection connection = connectionClass();
//        try {
//            if (connection != null) {
//                String sqlDelete = "DELETE FROM user WHERE id = '"+id.getText().toString()+"'";
//                Statement st = connection.createStatement();
//                int result = st.executeUpdate(sqlDelete);
//                if (result == 0){
//                    Toast.makeText(this, "Data doesn't exists", Toast.LENGTH_SHORT).show();
//                }
//            }
//        } catch (Exception exception) {
//            Log.e("Delete Error", exception.getMessage());
//        }
//    }
}