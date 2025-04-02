package com.example.quizapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Quiz1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView welcomeText = findViewById(R.id.welcomeText);
        TextView progressText = findViewById(R.id.progressText);
        Button buttonAnswer1 = findViewById(R.id.buttonAnswer1);
        Button buttonAnswer2 = findViewById(R.id.buttonAnswer2);
        Button buttonAnswer3 = findViewById(R.id.buttonAnswer3);
        Button buttonSubmit = findViewById(R.id.buttonSubmit);

        String name = getIntent().getStringExtra("name");
        welcomeText.setText("Welcome " + name);
        progressText.setText("1/5");

        buttonAnswer1.setOnClickListener(v -> {


        }
    }
}