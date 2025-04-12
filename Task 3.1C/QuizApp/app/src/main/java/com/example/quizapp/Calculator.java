package com.example.quizapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Calculator extends AppCompatActivity {

    private EditText num1;
    private EditText num2;
    private TextView result;
    private Button addButton;
    private Button subButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        result = findViewById(R.id.result);
        addButton = findViewById(R.id.addButton);
        subButton = findViewById(R.id.subButton);
        backButton = findViewById(R.id.backButton);


        addButton.setOnClickListener(v -> {
            try {
                double num1Value = Double.parseDouble(num1.getText().toString());
                double num2Value = Double.parseDouble(num2.getText().toString());
                double sum = num1Value + num2Value;
                result.setText(String.valueOf(sum));
            } catch (NumberFormatException e) {
                result.setText("Please enter valid numbers");
            }
        });

        subButton.setOnClickListener(v -> {
            try {
                double num1Value = Double.parseDouble(num1.getText().toString());
                double num2Value = Double.parseDouble(num2.getText().toString());
                double sub = num1Value - num2Value;
                result.setText(String.valueOf(sub));
            } catch (NumberFormatException e) {
                result.setText("Please enter valid numbers");
            }
        });

        backButton.setOnClickListener(v -> finish());


    }
}