package com.example.myapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database extends AppCompatActivity {
    @SuppressLint("NewApi")
    // Establish connection to database
    public Connection connectToDatabase(){
        Connection connection = null;
        StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);
        try {
            Class.forName("com.mysql.jdbc.Driver");

             /* Run ipconfig in windows command prompt and replace '192.168.1.8'
             with your computer's ip address, also replace 'myapp' with your
             database name, make sure XAMPP's mySQL service is enabled*/
            String url = "jdbc:mysql://192.168.137.1:3306/myapp?user=root&password=";

            connection = DriverManager.getConnection(url);
        } catch (Exception exception) {
            Log.e("Connection Error", exception.getMessage());
        }
        return connection;
    }

    public void createUserData(Context context, String name, String age) {
        Connection con = connectToDatabase();
        try {

            // If connection established successfully
            if (con != null) {

                // If any of the field is empty
                if (name.isEmpty() || age.isEmpty()) {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {

                    // mySQL Command
                    String sqlCommand = "INSERT INTO user(name, age) VALUES ('"+name+"','"+age+"')";
                    Statement st = con.createStatement();
                    st.executeUpdate(sqlCommand);

                    // Show Dialog
                    Toast.makeText(context, "Create Data Successful", Toast.LENGTH_SHORT).show();

                    // Close connection
                    con.close();
                    st.close();
                }
            }
        } catch (Exception exception) {
            Log.e("Create Error", exception.getMessage());
        }
    }

    public User readUserData(Context context, String id) {
        Connection con = connectToDatabase();
        try {
            if (con != null) {
                String sqlCommand = "SELECT * FROM user WHERE id = '"+id+"'";
                Statement st = con.createStatement();
                ResultSet result = st.executeQuery(sqlCommand);
                User user = new User(result.getString("name"), result.getInt("age"));
                con.close();
                st.close();
                result.close();
                if (user != null) {
                    Toast.makeText(context, "Read Data Successful", Toast.LENGTH_SHORT).show();
                    return user;
                } else {
                    Toast.makeText(context, "Read Data Failed", Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
        } catch (Exception exception) {
            Log.e("Read Error", exception.getMessage());
        }
        return null;
    }

    public void updateUserData (Context context, String id, String name, String age) {
        Connection con = connectToDatabase();
        try {
            if (con != null) {
                User user = readUserData(context, id);
                String tempName = user.getName();
                int tempAge = user.getAge();

                if (name.isEmpty()) {
                    name = tempName;
                }
                if (age.isEmpty()) {
                    age = String.valueOf(tempAge);
                }

                String sqlCommand = "UPDATE user SET name = '"+name+"', age = '"+age+"' WHERE id ='"+id+"'";
                Statement st = con.createStatement();
                st.executeUpdate(sqlCommand);

                Toast.makeText(context, "Update Data Successful", Toast.LENGTH_SHORT).show();

                con.close();
                st.close();
            }
        } catch (Exception exception) {
            Log.e("Update Error", exception.getMessage());
        }
    }

    public void deleteUserData(Context context, String id) {
        Connection con = connectToDatabase();
        try {
            if (con != null) {
                String sqlCommand = "DELETE FROM user WHERE id = '"+id+"'";
                Statement st = con.createStatement();
                st.executeUpdate(sqlCommand);

                Toast.makeText(context, "Delete Data Successful", Toast.LENGTH_SHORT).show();

                con.close();
                st.close();
            }
        } catch (Exception exception) {
            Log.e("Delete Error", exception.getMessage());
        }
    }

    public boolean register(Context context, String name, String phone, String email, String password) {
        Connection con = connectToDatabase();
        try {
            if (con != null) {
                if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    String hash = BCrypt.hashpw(password, BCrypt.gensalt(10));
                    String sqlCommand = "INSERT INTO users(name, phone, email, password) VALUES ('"+name+"', '"+phone+"', '"+email+"', '"+hash+"')";
                    Statement st = con.createStatement();
                    st.executeUpdate(sqlCommand);
                    con.close();
                    st.close();
                    Toast.makeText(context, "Register Successful", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        } catch (Exception exception) {
            Log.e("Register Error", exception.getMessage());
        }
        return false;
    }

    public boolean login(Context context, String email, String password) {
        Connection con = connectToDatabase();
        try {
            if (con != null) {
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    String sqlSelectCommand = "SELECT password FROM users WHERE email = '"+email+"'";
                    Statement selectStatement = con.createStatement();
                    ResultSet result = selectStatement.executeQuery(sqlSelectCommand);
                    if (result.next()) {
                        String hash = result.getString("password");
                        selectStatement.close();
                        result.close();
                        if (BCrypt.checkpw(password, hash)) {
                            Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show();
                            con.close();
                            return true;
                        } else {
                            Toast.makeText(context, "Email or Password is Incorrect", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Email not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (Exception exception) {
            Log.e("Login Error", "Error");
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
