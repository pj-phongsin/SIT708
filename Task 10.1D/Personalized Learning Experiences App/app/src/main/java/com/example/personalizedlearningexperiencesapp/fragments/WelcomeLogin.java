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
import com.example.personalizedlearningexperiencesapp.db.UserDatabaseHelper;

import java.util.ArrayList;

public class WelcomeLogin extends Fragment {


    public WelcomeLogin() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome_login, container, false);

        EditText usernameInput = view.findViewById(R.id.username);
        EditText passwordInput = view.findViewById(R.id.password);
        Button loginButton = view.findViewById(R.id.loginButton);
        TextView needAccountText = view.findViewById(R.id.needAccount);

        // Initialize DB helper
        UserDatabaseHelper dbHelper = new UserDatabaseHelper(getContext());

        loginButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (!username.isEmpty() && !password.isEmpty()) {
                if (dbHelper.checkUserCredentials(username, password)) {
                    // Get user interests (you must implement this method in UserDatabaseHelper)
                    ArrayList<String> selectedTopics = dbHelper.getUserInterests(username);

                    Home homeFragment = new Home();
                    Bundle bundle = new Bundle();
                    bundle.putString("username", username);
                    bundle.putStringArrayList("selectedTopics", selectedTopics);
                    homeFragment.setArguments(bundle);

                    ((MainActivity) getActivity()).switchFragment(homeFragment);
                } else {
                    passwordInput.setError("Invalid username or password");
                }
            } else {
                if (username.isEmpty()) {
                    usernameInput.setError("Username is required");
                }
                if (password.isEmpty()) {
                    passwordInput.setError("Password is required");
                }
            }
        });

        needAccountText.setOnClickListener(v -> {
            ((MainActivity) getActivity()).switchFragment(new CreateAccount());
        });

        return view;
    }
}