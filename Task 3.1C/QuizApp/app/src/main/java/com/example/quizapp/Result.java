package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Result extends AppCompatActivity {

    public int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        score = getIntent().getExtras().getInt("score");

        TextView congratText = findViewById(R.id.congratText);
        TextView resultScore = findViewById(R.id.result);
        Button retakeQuizButton = findViewById(R.id.retakeQuizButton);
        Button finishButton = findViewById(R.id.finishButton);

        String name = getIntent().getStringExtra("name");
        congratText.setText("Congratulations " + name);
        resultScore.setText(score + "/5");

        retakeQuizButton.setOnClickListener(v -> {
            Intent intent = new Intent(Result.this, Quiz1.class);
            startActivity(intent);
        });
        finishButton.setOnClickListener(v -> {
            finishAffinity();
            System.exit(0);
        });

    }
}