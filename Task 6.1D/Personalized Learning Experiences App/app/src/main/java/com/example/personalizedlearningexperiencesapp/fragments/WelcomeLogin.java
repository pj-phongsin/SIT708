package com.example.personalizedlearningexperiencesapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.personalizedlearningexperiencesapp.MainActivity;
import com.example.personalizedlearningexperiencesapp.R;

public class WelcomeLogin extends Fragment {


    public WelcomeLogin() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome_login, container, false);

        // Get reference to views
        EditText usernameInput = view.findViewById(R.id.username);
        EditText passwordInput = view.findViewById(R.id.password);
        Button loginButton = view.findViewById(R.id.loginButton);
        TextView needAccountText = view.findViewById(R.id.needAccount);

        // Login button logic
        loginButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            // Check if username and password are valid
            if (!username.isEmpty() && !password.isEmpty()) {
                // Navigate to the next fragment
                ((MainActivity) getActivity()).switchFragment(new Home());
            } else {
                if (username.isEmpty()) {
                    usernameInput.setError("Username is required");
                }
                if (password.isEmpty()) {
                    passwordInput.setError("Password is required");
                }
            }

        });
        // Need account text logic
        needAccountText.setOnClickListener(v -> {
            // Navigate to the next fragment
            ((MainActivity) getActivity()).switchFragment(new CreateAccount());
        });

        return view;
    }
}