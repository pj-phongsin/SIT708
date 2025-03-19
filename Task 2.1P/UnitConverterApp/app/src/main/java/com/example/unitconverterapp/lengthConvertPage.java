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

public class lengthConvertPage extends AppCompatActivity {

    private ImageButton backButton;
    private EditText lengthSourceValue, lengthConvertValue;
    private Spinner lengthSourceUnit, lengthConvertUnit;
    private Button lengthConvertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_length_convert_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lengthSourceValue = findViewById(R.id.lengthSourceValue);
        lengthConvertValue = findViewById(R.id.lengthConvertValue);
        lengthSourceUnit = findViewById(R.id.lengthSourceUnit);
        lengthConvertUnit = findViewById(R.id.lengthConvertUnit);
        lengthConvertButton = findViewById(R.id.lentghConvertButton);
        backButton = findViewById(R.id.backButton);

        lengthConvertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sourceValue = lengthSourceValue.getText().toString();
                String sourceUnit = lengthSourceUnit.getSelectedItem().toString();
                String convertUnit = lengthConvertUnit.getSelectedItem().toString();

                if (sourceValue.isEmpty()) {
                    Toast.makeText(lengthConvertPage.this, "Please enter value", Toast.LENGTH_SHORT).show();
                    //lengthSourceValue.setText("Please enter value");
                    return;
                }

                double value = Double.parseDouble(sourceValue);
                double convertedValue = convertLength(value, sourceUnit, convertUnit);
                lengthConvertValue.setText(String.valueOf(convertedValue));
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(lengthConvertPage.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private double convertLength(double value, String fromUnit, String toUnit) {
        double meters;

        switch (fromUnit) {
            case "Centimeter": meters = value / 100; break;
            case "Meter": meters = value; break;
            case "Kilometer": meters = value * 1000; break;
            case "Inch": meters = value * 0.0254; break;
            case "Foot": meters = value * 0.3048; break;
            case "Yard": meters = value * 0.9144; break;
            case "Mile": meters = value * 1609.34; break;
            default: return 0;
        }

        switch (toUnit) {
            case "Centimeter": return meters * 100;
            case "Meter": return meters;
            case "Kilometer": return meters / 1000;
            case "Inch": return meters / 0.0254;
            case "Foot": return meters / 0.3048;
            case "Yard": return meters / 0.9144;
            case "Mile": return meters / 1609.34;
            default: return 0;
        }
    }
}
