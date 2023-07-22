package com.example.pama;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class GetStarted extends AppCompatActivity {

    private EditText editTextName, editTextPhoneNumber;
    private RadioGroup radioGroupColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        editTextName = findViewById(R.id.editTextName);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        radioGroupColor = findViewById(R.id.radioGroupColor);

        checkAndLoadPreferences();

        Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editTextName.getText().toString().trim().equals("") || editTextPhoneNumber.getText().toString().trim().equals("")) {
                    Toast.makeText(GetStarted.this, "Enter your Username and phone number to continue", Toast.LENGTH_SHORT).show();
                } else {
                    savePreferences();
                    proceedToMainActivity();
                }
            }
        });
    }

    private void savePreferences() {
        // Get user inputs
        String name = editTextName.getText().toString();
        String phoneNumber = editTextPhoneNumber.getText().toString();
        boolean isDarkTheme = radioGroupColor.getCheckedRadioButtonId() == R.id.radioButtonDark;

        // Save preferences using Shared Preferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("phoneNumber", phoneNumber);
        editor.putBoolean("isDarkTheme", isDarkTheme);
        editor.apply();

        Toast.makeText(GetStarted.this, "Theme changes will take effect on the next application start", Toast.LENGTH_SHORT).show();

    }

    private void checkAndLoadPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Check if saved preferences exist
        if (sharedPreferences.contains("name") && sharedPreferences.contains("phoneNumber") && sharedPreferences.contains("isDarkTheme")) {
            // Preferences exist, load them
            proceedToMainActivity();

            String name = sharedPreferences.getString("name", "");
            String phoneNumber = sharedPreferences.getString("phoneNumber", "");
            boolean isDarkTheme = sharedPreferences.getBoolean("isDarkTheme", false);

            // Use the loaded preferences
            editTextName.setText(name);
            editTextPhoneNumber.setText(phoneNumber);
            radioGroupColor.check(isDarkTheme ? R.id.radioButtonDark : R.id.radioButtonLight);

        } else {
            // Preferences do not exist, prompt the user to enter the data and save preferences
            savePreferences();
        }
    }

    private void proceedToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Close this activity so the user can't go back to it using the back button
    }
}
