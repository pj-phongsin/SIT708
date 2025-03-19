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

public class tempConvertPage extends AppCompatActivity {

    private ImageButton backButton;
    private EditText tempSourceValue, tempConvertValue;
    private Spinner tempSourceUnit, tempConvertUnit;
    private Button tempConvertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_temp_convert_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tempSourceValue = findViewById(R.id.tempSourceValue);
        tempConvertValue = findViewById(R.id.tempConvertValue);
        tempSourceUnit = findViewById(R.id.tempSourceUnit);
        tempConvertUnit = findViewById(R.id.tempConvertUnit);
        tempConvertButton = findViewById(R.id.tempConvertButton);
        backButton = findViewById(R.id.backButton);

        tempConvertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sourceValue = tempSourceValue.getText().toString();
                String sourceUnit = tempSourceUnit.getSelectedItem().toString();
                String convertUnit = tempConvertUnit.getSelectedItem().toString();

                if (sourceValue.isEmpty()) {
                    Toast.makeText(tempConvertPage.this, "Please enter value", Toast.LENGTH_SHORT).show();
                    return;
                }

                double value = Double.parseDouble(sourceValue);
                double convertedValue = convertTemp(value, sourceUnit, convertUnit);
                tempConvertValue.setText(String.valueOf(convertedValue));
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tempConvertPage.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private double convertTemp(double value, String fromUnit, String toUnit) {
        double celsius;

        switch (fromUnit) {
            case "Celsius": celsius = value; break;
            case "Fahrenheit": celsius = (value - 32) * 5 / 9; break;
            case "Kelvin": celsius = value - 273.15; break;
            default: return 0;
        }

        switch (toUnit) {
            case "Celsius": return celsius;
            case "Fahrenheit": return celsius * 9 / 5 + 32;
            case "Kelvin": return celsius + 273.15;
            default: return 0;
        }
    }
}