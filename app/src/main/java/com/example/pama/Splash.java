package com.example.pama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.Objects;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Objects.requireNonNull(getSupportActionBar()).hide();
        // Delayed action to close the activity after 5 seconds (5000 milliseconds)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the main activity and close the splash activity
                Intent intent = new Intent(Splash.this, GetStarted.class);
                startActivity(intent);
                finish();
            }
        }, 5000); // 5000 milliseconds = 5 seconds
    }
}