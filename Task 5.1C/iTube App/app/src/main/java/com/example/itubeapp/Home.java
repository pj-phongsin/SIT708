package com.example.itubeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        int currentUserId = getIntent().getIntExtra("user_id", -1);

        Button playlistButton = findViewById(R.id.button5);
        playlistButton.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, MyPlaylist.class);
            intent.putExtra("user_id", currentUserId);
            startActivity(intent);
        });

        Button playBtn = findViewById(R.id.button3);
        EditText urlInput = findViewById(R.id.editTextText2);

        playBtn.setOnClickListener(v -> {
            String videoUrl = urlInput.getText().toString().trim();

            if (!videoUrl.isEmpty()) {
                Intent intent = new Intent(Home.this, Player.class);
                intent.putExtra("video_url", videoUrl);
                startActivity(intent);
            } else {
                Toast.makeText(Home.this, "Please enter a YouTube URL", Toast.LENGTH_SHORT).show();
            }
        });



        Button addToPlaylistBtn = findViewById(R.id.button4);
        iTubeDataBaseHelper dbHelper = new iTubeDataBaseHelper(this);

        addToPlaylistBtn.setOnClickListener(v -> {
            String videoUrl = urlInput.getText().toString().trim();

            if (!videoUrl.isEmpty()) {
                boolean inserted = dbHelper.addToPlaylist(currentUserId, videoUrl);
                if (inserted) {
                    Toast.makeText(Home.this, "Video added to playlist", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Home.this, "Failed to add video", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Home.this, "Please enter a video URL", Toast.LENGTH_SHORT).show();
            }
        });
    }
}