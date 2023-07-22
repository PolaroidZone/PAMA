package com.example.pama;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
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

        checkAndLoadPreferences();
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


    private void checkAndLoadPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Check if saved preferences exist
        if (sharedPreferences.contains("name") && sharedPreferences.contains("phoneNumber") && sharedPreferences.contains("isDarkTheme")) {
            // Preferences exist, load them
            loadPreferences();
        }
    }

    private void loadPreferences() {
        // Load preferences using Shared Preferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isDarkTheme = sharedPreferences.getBoolean("isDarkTheme", false);

        // Set the app theme based on the saved preference
        if (isDarkTheme) {
            // Set dark theme
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            // Set light theme
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}