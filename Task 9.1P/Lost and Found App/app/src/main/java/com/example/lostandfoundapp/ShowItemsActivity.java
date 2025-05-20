package com.example.lostandfoundapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostandfoundapp.db.DatabaseHelper;
import com.example.lostandfoundapp.model.Item;

import java.util.ArrayList;

public class ShowItemsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items);

        recyclerView = findViewById(R.id.recyclerView);
        dbHelper = new DatabaseHelper(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadItems(); // Initial load
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadItems(); // Refresh list
    }

    private void loadItems() {
        ArrayList<Item> itemList = dbHelper.getAllItems();
        itemAdapter = new ItemAdapter(itemList);
        recyclerView.setAdapter(itemAdapter);
    }
}