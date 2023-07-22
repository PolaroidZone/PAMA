package com.example.pama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.MediaStore;
import android.database.Cursor;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.media.MediaPlayer;
// Add these imports at the top of your PlayMusic.java file
import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import java.util.ArrayList;
import java.util.Objects;

public class PlayMusic extends AppCompatActivity {
    private static final int READ_EXTERNAL_STORAGE_PERMISSION_CODE = 1;
    private ListView listViewSongs;
    private ArrayList<String> songList;
    private ArrayList<String> songPathList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        Objects.requireNonNull(getSupportActionBar()).hide();

        listViewSongs = findViewById(R.id.listViewSongs);
        songList = new ArrayList<>();
        songPathList = new ArrayList<>();

        // Check if the permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_EXTERNAL_STORAGE_PERMISSION_CODE);
        } else {
            // Permission is already granted, fetch and display songs
            fetchSongs();
            setupListView();
        }
    }

    private void fetchSongs() {
        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        Cursor cursor = getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String songTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                @SuppressLint("Range") String songPath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                songList.add(songTitle);
                songPathList.add(songPath);
            }
            cursor.close();
        }
    }

    private void setupListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                songList);

        listViewSongs.setAdapter(adapter);
        listViewSongs.setOnItemClickListener((parent, view, position, id) -> {
            // Implement code to play the selected song
            String selectedSongTitle = songList.get(position);
            String selectedSongPath = songPathList.get(position);
            playSelectedSong(selectedSongTitle, selectedSongPath);
        });
    }

    private void playSelectedSong(String songTitle, String songPath) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(songPath);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, fetch and display songs
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                fetchSongs();
                setupListView();
            } else {
                // Permission is denied, handle this case (e.g., show a message or request again)
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
