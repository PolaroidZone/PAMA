package com.example.pama;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Objects.requireNonNull(getSupportActionBar()).hide();

        Button buttonSearchHelp = findViewById(R.id.buttonSearchHelp);
        buttonSearchHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open an internet browser to search for help related to the app
                String searchQuery = "Personal Assistant Management App help"; // Customize the search query if needed
                performSearchOnInternet(searchQuery);
            }
        });
    }

    private void performSearchOnInternet(String query) {
        Uri searchUri = Uri.parse("https://www.google.com/search?q=" + Uri.encode(query));
        Intent intent = new Intent(Intent.ACTION_VIEW, searchUri);
        startActivity(intent);
    }
}
