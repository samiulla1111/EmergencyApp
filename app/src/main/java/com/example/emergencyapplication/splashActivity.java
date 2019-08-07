package com.example.emergencyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.content.SharedPreferences;

import java.util.ResourceBundle;

public class splashActivity extends AppCompatActivity {
    String name1;
    String pass1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences sharedPreferences =getSharedPreferences("loginref",MODE_PRIVATE);
        name1=sharedPreferences.getString("username","");
        pass1=sharedPreferences.getString("password","");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if((name1.isEmpty()&& pass1.isEmpty())==false)
                {
                    Intent intent3 = new Intent(splashActivity.this,welcomeActivity.class);
                    startActivity(intent3);
                }
                else {
                    Intent intent5 = new Intent(splashActivity.this,MainActivity.class);
                    startActivity(intent5);

                }
                finish();
            }
        },5000);



    }
}
