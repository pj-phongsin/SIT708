package com.example.lostandfoundapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lostandfoundapp.db.DatabaseHelper;
import com.example.lostandfoundapp.model.Item;

public class AddItemActivity extends AppCompatActivity {

    private RadioGroup radioGroupType;
    private RadioButton radioLost, radioFound;
    private EditText inputName, inputPhone, inputDescription, inputDate, inputLocation;
    private Button saveButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        radioGroupType = findViewById(R.id.radioGroupType);
        radioLost = findViewById(R.id.radioLost);
        radioFound = findViewById(R.id.radioFound);

        inputName = findViewById(R.id.inputName);
        inputPhone = findViewById(R.id.inputPhone);
        inputDescription = findViewById(R.id.inputDescription);
        inputDate = findViewById(R.id.inputDate);
        inputLocation = findViewById(R.id.inputLocation);
        saveButton = findViewById(R.id.saveButton);

        dbHelper = new DatabaseHelper(this);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupType.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(AddItemActivity.this, "Please select post type", Toast.LENGTH_SHORT).show();
                    return;
                }

                String type = (selectedId == R.id.radioLost) ? "Lost" : "Found";
                String name = inputName.getText().toString();
                String phone = inputPhone.getText().toString();
                String description = inputDescription.getText().toString();
                String date = inputDate.getText().toString();
                String location = inputLocation.getText().toString();

                if (name.isEmpty() || phone.isEmpty() || description.isEmpty()
                        || date.isEmpty() || location.isEmpty()) {
                    Toast.makeText(AddItemActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                Item item = new Item(type, name, phone, description, date, location);
                dbHelper.insertItem(item);
                Toast.makeText(AddItemActivity.this, "Advert saved!", Toast.LENGTH_SHORT).show();
                finish(); // go back to main
            }
        });
    }
}