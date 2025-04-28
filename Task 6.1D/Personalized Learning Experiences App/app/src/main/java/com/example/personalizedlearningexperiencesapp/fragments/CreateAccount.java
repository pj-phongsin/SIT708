package com.example.personalizedlearningexperiencesapp.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.personalizedlearningexperiencesapp.MainActivity;
import com.example.personalizedlearningexperiencesapp.R;

public class CreateAccount extends Fragment {

    public CreateAccount() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_account, container, false);

        // Bind views
        EditText username = view.findViewById(R.id.username2);
        EditText email = view.findViewById(R.id.email);
        EditText confirmEmail = view.findViewById(R.id.confirmEmail);
        EditText password = view.findViewById(R.id.password2);
        EditText confirmPassword = view.findViewById(R.id.confirmPassword);
        EditText phoneNumber = view.findViewById(R.id.phoneNumber);
        Button createAccountButton = view.findViewById(R.id.createAccount);

        createAccountButton.setOnClickListener(v -> {
            String user = username.getText().toString().trim();
            String mail = email.getText().toString().trim();
            String confirmMail = confirmEmail.getText().toString().trim();
            String pass = password.getText().toString().trim();
            String confirmPass = confirmPassword.getText().toString().trim();
            String phone = phoneNumber.getText().toString().trim();

            // Validation
            if (TextUtils.isEmpty(user)) {
                username.setError("Username required");
                return;
            }
            if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(confirmMail)) {
                email.setError("Email required");
                confirmEmail.setError("Confirm email required");
                return;
            }
            if (!mail.equals(confirmMail)) {
                confirmEmail.setError("Emails do not match");
                return;
            }
            if (TextUtils.isEmpty(pass) || TextUtils.isEmpty(confirmPass)) {
                password.setError("Password required");
                confirmPassword.setError("Confirm password required");
                return;
            }
            if (!pass.equals(confirmPass)) {
                confirmPassword.setError("Passwords do not match");
                return;
            }
            if (TextUtils.isEmpty(phone)) {
                phoneNumber.setError("Phone number required");
                return;
            }

            YourInterest interestFragment = new YourInterest();

            Bundle bundle = new Bundle();
            bundle.putString("username", user);
            bundle.putString("email", mail);
            bundle.putString("password", pass);
            bundle.putString("phone", phone);
            interestFragment.setArguments(bundle);

            ((MainActivity) getActivity()).switchFragment(interestFragment);
        });

        return view;
    }
}