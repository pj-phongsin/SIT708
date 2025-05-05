package com.example.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lostandfoundapp.db.DatabaseHelper;

public class ItemDetailActivity extends AppCompatActivity {

    private TextView textType, textName, textPhone, textDescription, textDate, textLocation;
    private Button btnRemove;
    private int itemId;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        dbHelper = new DatabaseHelper(this);

        textType = findViewById(R.id.textType);
        textName = findViewById(R.id.textName);
        textPhone = findViewById(R.id.textPhone);
        textDescription = findViewById(R.id.textDescription);
        textDate = findViewById(R.id.textDate);
        textLocation = findViewById(R.id.textLocation);
        btnRemove = findViewById(R.id.btnRemove);

        Intent intent = getIntent();
        itemId = intent.getIntExtra("id", -1);
        textType.setText(intent.getStringExtra("type"));
        textName.setText("Name: " + intent.getStringExtra("name"));
        textPhone.setText("Phone: " + intent.getStringExtra("phone"));
        textDescription.setText("Description: " + intent.getStringExtra("description"));
        textDate.setText("Date: " + intent.getStringExtra("date"));
        textLocation.setText("Location: " + intent.getStringExtra("location"));

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteItem(itemId);
                Toast.makeText(ItemDetailActivity.this, "Item removed", Toast.LENGTH_SHORT).show();
                finish(); // go back
            }
        });
    }
}