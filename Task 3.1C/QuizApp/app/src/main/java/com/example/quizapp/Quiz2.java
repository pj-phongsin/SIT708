package com.example.quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Quiz2 extends AppCompatActivity {

    private Button selectedButton = null;
    private int correctAnswerId = R.id.buttonAnswer1;
    private boolean isAnswered = false;
    public int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        score = getIntent().getExtras().getInt("score");

        TextView welcomeText = findViewById(R.id.welcomeText);
        TextView progressText = findViewById(R.id.progressText);
        Button buttonAnswer1 = findViewById(R.id.buttonAnswer1);
        Button buttonAnswer2 = findViewById(R.id.buttonAnswer2);
        Button buttonAnswer3 = findViewById(R.id.buttonAnswer3);
        Button buttonSubmit = findViewById(R.id.buttonSubmit);

        String name = getIntent().getStringExtra("name");
        welcomeText.setText("Welcome " + name);
        progressText.setText("2/5");

        View.OnClickListener answerClickListener = v -> {
            resetButtonColors(buttonAnswer1, buttonAnswer2, buttonAnswer3);
            selectedButton = (Button) v;
            selectedButton.setBackgroundColor(Color.DKGRAY);
        };
        buttonAnswer1.setOnClickListener(answerClickListener);
        buttonAnswer2.setOnClickListener(answerClickListener);
        buttonAnswer3.setOnClickListener(answerClickListener);

        buttonSubmit.setOnClickListener(v -> {
            if (!isAnswered) {
                if (selectedButton != null) {
                    isAnswered = true;
                    if (selectedButton.getId() == correctAnswerId) {
                        selectedButton.setBackgroundColor(Color.parseColor("#00FF00"));
                        score++;
                    } else {
                        selectedButton.setBackgroundColor(Color.parseColor("#FF0000"));
                        Button correctButton = findViewById(correctAnswerId);
                        correctButton.setBackgroundColor(Color.parseColor("#00FF00"));
                    }
                    buttonSubmit.setText("Next");
                }
            }
            else {
                Intent intent = new Intent(Quiz2.this, Quiz3.class);
                intent.putExtra("score", score);
                startActivity(intent);
            }
        });
    }
    private void resetButtonColors(Button... buttons) {
        for (Button button : buttons) {
            button.setBackgroundColor(Color.parseColor("#A9A9A9"));
        }
    }
}

