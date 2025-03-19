package com.example.unitconverterapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class weightConvertPage extends AppCompatActivity {

    private ImageButton backButton;
    private EditText weightSourceValue, weightConvertValue;
    private Spinner weightSourceUnit, weightConvertUnit;
    private Button weightConvertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_weight_convert_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        weightSourceValue = findViewById(R.id.weightSourceValue);
        weightConvertValue = findViewById(R.id.weightConvertValue);
        weightSourceUnit = findViewById(R.id.weightSourceUnit);
        weightConvertUnit = findViewById(R.id.weightConvertUnit);
        weightConvertButton = findViewById(R.id.weightConvertButton);
        backButton = findViewById(R.id.backButton);

        weightConvertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sourceValue = weightSourceValue.getText().toString();
                String sourceUnit = weightSourceUnit.getSelectedItem().toString();
                String convertUnit = weightConvertUnit.getSelectedItem().toString();

                if (sourceValue.isEmpty()) {
                    Toast.makeText(weightConvertPage.this, "Please enter value", Toast.LENGTH_SHORT).show();
                    return;
                }

                double value = Double.parseDouble(sourceValue);
                double convertedValue = convertWeight(value, sourceUnit, convertUnit);
                weightConvertValue.setText(String.valueOf(convertedValue));
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(weightConvertPage.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private double convertWeight(double value, String fromUnit, String toUnit) {
        double kilograms;

        switch (fromUnit) {
            case "Kilogram": kilograms = value; break;
            case "Gram": kilograms = value / 1000; break;
            case "Pound": kilograms = value * 0.453592; break;
            case "Ounce": kilograms = value * 0.0283495; break;
            case "Ton": kilograms = value * 907.185; break;
            default: return 0;
        }

        switch (toUnit) {
            case "Kilogram": return kilograms;
            case "Gram": return kilograms * 1000;
            case "Pound": return kilograms / 0.453592;
            case "Ounce": return kilograms / 0.0283495;
            case "Ton": return kilograms / 907.185;
            default: return 0;
        }
    }
}