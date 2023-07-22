package com.example.pama;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.example.pama.adapters.MissedCallAdapter;
import com.example.pama.objects.MissedCallItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_READ_CALL_LOG = 1;

    private List<MissedCallItem> missedCallsList;
    private RecyclerView recyclerViewMissedCalls;
    private MissedCallAdapter missedCallAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sample data for missed calls (you can retrieve actual missed calls from the device's call log)
        List<MissedCallItem> missedCallsList = new ArrayList<>();
        missedCallsList.add(new MissedCallItem("John Doe", "+267 777 817 89", "123-456-7890", "3 mins ago"));
        // Add more missed call items to the list as needed

        // Set up the RecyclerView for missed calls
        missedCallsList = new ArrayList<>();
        recyclerViewMissedCalls = findViewById(R.id.recyclerViewMissedCalls);
        recyclerViewMissedCalls.setLayoutManager(new LinearLayoutManager(this));
        missedCallAdapter = new MissedCallAdapter(missedCallsList);
        recyclerViewMissedCalls.setAdapter(missedCallAdapter);

        // Check and request READ_CALL_LOG permission if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG}, REQUEST_READ_CALL_LOG);
            } else {
                fetchMissedCalls();
            }
        } else {
            fetchMissedCalls();
        }

        // Set up click listeners for the floating action buttons
        FloatingActionButton fabMakePhoneCall = findViewById(R.id.fabMakePhoneCall);
        fabMakePhoneCall.setOnClickListener(v -> openMakePhoneCallActivity());

        FloatingActionButton fabPlayMusic = findViewById(R.id.fabPlayMusic);
        fabPlayMusic.setOnClickListener(v -> openPlayMusicActivity());

        FloatingActionButton fabOtherFeature = findViewById(R.id.fabOtherFeature);
        fabOtherFeature.setOnClickListener(v -> openOtherFeatureActivity());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_settings) {
            openSettingsActivity();
            return true;
        } else if (itemId == R.id.action_help) {
            openHelpActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openMakePhoneCallActivity() {
        // Implement the code to open the activity for making phone calls
        // For example:
        Intent intent = new Intent(this, Phone.class);
        startActivity(intent);
    }

    private void openPlayMusicActivity() {
        // Implement the code to open the activity for playing music
        // For example:
        Intent intent = new Intent(this, PlayMusic.class);
        startActivity(intent);
    }

    private void openOtherFeatureActivity() {
        // Implement the code to open the activity for the other feature
        // For example:
        Intent intent = new Intent(this, MessageSender.class);
        startActivity(intent);
    }

    private void openSettingsActivity() {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    private void openHelpActivity() {
        Intent intent = new Intent(this, Help.class);
        startActivity(intent);
    }

    private void fetchMissedCalls() {
        String[] projection = {
                CallLog.Calls._ID,
                CallLog.Calls.CACHED_NAME,
                CallLog.Calls.NUMBER,
                CallLog.Calls.DATE
        };

        String selection = CallLog.Calls.TYPE + " = ? AND " + CallLog.Calls.NEW + " = ?";
        String[] selectionArgs = {
                String.valueOf(CallLog.Calls.MISSED_TYPE),
                "1"
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
            Cursor cursor = getContentResolver().query(
                    CallLog.Calls.CONTENT_URI,
                    projection,
                    selection,
                    selectionArgs,
                    CallLog.Calls.DATE + " DESC"
            );

            if (cursor != null) {
                int nameIndex = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
                int numberIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER);
                int dateIndex = cursor.getColumnIndex(CallLog.Calls.DATE);

                while (cursor.moveToNext()) {
                    String name = cursor.getString(nameIndex);
                    String number = cursor.getString(numberIndex);
                    long timestamp = cursor.getLong(dateIndex);

                    // Format timestamp to display "X mins ago" or "X hours ago"
                    String timeAgo = formatTimeAgo(timestamp);

                    MissedCallItem missedCallItem = new MissedCallItem(name, number, timeAgo);
                    missedCallsList.add(missedCallItem);
                }

                cursor.close();
                missedCallAdapter.notifyDataSetChanged();
            }
        }
    }

    private String formatTimeAgo(long timestamp) {
        long now = new Date().getTime();
        long diffMillis = now - timestamp;
        long minutes = diffMillis / (60 * 1000);
        if (minutes < 60) {
            return minutes + " mins ago";
        } else {
            long hours = minutes / 60;
            return hours + " hours ago";
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_CALL_LOG) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchMissedCalls();
            } else {
                // Permission denied, handle it accordingly (e.g., show a message or disable the missed call feature)
            }
        }
    }

}