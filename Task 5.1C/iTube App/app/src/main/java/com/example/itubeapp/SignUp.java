package com.example.itubeapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUp extends AppCompatActivity {

    EditText fullNameEditText, usernameEditText, passwordEditText, confirmPasswordEditText;
    Button createAccountButton;
    iTubeDataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        iTubeDataBaseHelper dbHelper = new iTubeDataBaseHelper(this);
        dbHelper.getWritableDatabase();

        // Bind views
        fullNameEditText = findViewById(R.id.editTextText3);
        usernameEditText = findViewById(R.id.editTextText4);
        passwordEditText = findViewById(R.id.editTextText5);
        confirmPasswordEditText = findViewById(R.id.editTextText6);
        createAccountButton = findViewById(R.id.button6);

        createAccountButton.setOnClickListener(view -> {
            String fullName = fullNameEditText.getText().toString().trim();
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();

            // Validation
            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // Try to add user to DB
            UserData newUser = new UserData(0, username, password);
            boolean success = dbHelper.addUser(newUser);

            if (success) {
                Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                finish(); // or navigate to login screen
            } else {
                Toast.makeText(this, "Username already taken!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}