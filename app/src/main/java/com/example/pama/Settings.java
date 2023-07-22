package com.example.pama;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Objects;

public class Settings extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPhoneNumber;
    private RadioGroup radioGroupColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Objects.requireNonNull(getSupportActionBar()).hide();

        editTextName = findViewById(R.id.editTextName);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        radioGroupColor = findViewById(R.id.radioGroupColor);

        Button buttonSaveChanges = findViewById(R.id.buttonSaveChanges);
        Button buttonResetPreferences = findViewById(R.id.buttonResetPreferences);

        buttonSaveChanges.setOnClickListener(v -> savePreferences());
        buttonResetPreferences.setOnClickListener(v -> resetPreferences());

        loadPreferences(); // Load saved preferences to populate the views with existing data
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
    }

    private void resetPreferences() {
        // Reset preferences using Shared Preferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Clear the input fields and reset the radio group
        editTextName.setText("");
        editTextPhoneNumber.setText("");
        radioGroupColor.clearCheck();
    }

    private void loadPreferences() {
        // Load preferences using Shared Preferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        String phoneNumber = sharedPreferences.getString("phoneNumber", "");
        boolean isDarkTheme = sharedPreferences.getBoolean("isDarkTheme", false);

        // Set the loaded preferences to the corresponding views
        editTextName.setText(name);
        editTextPhoneNumber.setText(phoneNumber);
        radioGroupColor.check(isDarkTheme ? R.id.radioButtonDark : R.id.radioButtonLight);
    }
}