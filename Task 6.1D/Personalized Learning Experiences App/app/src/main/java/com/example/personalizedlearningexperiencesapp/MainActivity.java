package com.example.personalizedlearningexperiencesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

import com.example.personalizedlearningexperiencesapp.fragments.WelcomeLogin;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load WelcomeLogin fragment by default
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new WelcomeLogin())
                    .commit();
        }
    }

    // Public method to allow fragment switching from anywhere
    public void switchFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        android.R.anim.fade_in,    // enter animation
                        android.R.anim.fade_out,   // exit animation
                        android.R.anim.fade_in,    // popEnter animation
                        android.R.anim.fade_out    // popExit animation
                )
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}