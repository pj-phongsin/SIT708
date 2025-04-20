package com.example.itubeapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MyPlaylist extends AppCompatActivity {

    RecyclerView recyclerView;
    iTubeDataBaseHelper dbHelper;
    PlaylistAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_playlist);

        recyclerView = findViewById(R.id.recyclerViewPlaylist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new iTubeDataBaseHelper(this);


        int userId = getIntent().getIntExtra("user_id", -1);

        List<String> playlist = dbHelper.getUserPlaylist(userId);
        adapter = new PlaylistAdapter(this, playlist);
        recyclerView.setAdapter(adapter);
    }
}