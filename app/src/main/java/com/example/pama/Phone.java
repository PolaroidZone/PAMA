package com.example.pama;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

public class Phone extends AppCompatActivity {

    private EditText editTextPhoneNumber;
    private Button buttonCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        Objects.requireNonNull(getSupportActionBar()).hide();

        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        buttonCall = findViewById(R.id.buttonCall);

        buttonCall.setOnClickListener(v -> makePhoneCall());
    }

    private void makePhoneCall() {
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        if (!phoneNumber.isEmpty()) {
            // Check if the CALL_PHONE permission is granted
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                // If permission is granted, make the phone call
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            } else {
                // If permission is not granted, request the CALL_PHONE permission
                requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE}, 1);
            }
        }
    }
}
