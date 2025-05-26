package com.example.personalizedlearningexperiencesapp.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.util.Log;

import com.example.personalizedlearningexperiencesapp.R;
import com.example.personalizedlearningexperiencesapp.db.UserDatabaseHelper;

public class ProfileFragment extends Fragment {

    private TextView usernameText, emailText, totalText, correctText, incorrectText;
    private Button shareButton;
    private UserDatabaseHelper dbHelper;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        usernameText = view.findViewById(R.id.profileUsername);
        emailText = view.findViewById(R.id.profileEmail);
        totalText = view.findViewById(R.id.totalQuestions);
        correctText = view.findViewById(R.id.correctAnswers);
        incorrectText = view.findViewById(R.id.incorrectAnswers);
        shareButton = view.findViewById(R.id.shareProfileButton);

        dbHelper = new UserDatabaseHelper(getContext());

        Bundle args = getArguments();
        if (args != null) {
            String username = args.getString("username", "N/A");
            Log.d("ProfileFragment", "Received username: " + username);
            usernameText.setText(username);

            // Display saved upgrade plan from SharedPreferences
            TextView planText = view.findViewById(R.id.profileUpgradeLevel);
            SharedPreferences prefs = requireActivity().getSharedPreferences("AppPrefs", getContext().MODE_PRIVATE);
            String plan = prefs.getString("upgradePlan", "Free");
            planText.setText("Your Plan: " + plan);
            if (!"N/A".equals(username)) {
                String email = dbHelper.getEmailByUsername(username);
                emailText.setText(email != null ? email : "Email not found");

                int total = dbHelper.getTotalQuestionsForUser(username);
                int correct = dbHelper.getCorrectAnswersForUser(username);
                int incorrect = total - correct;

                totalText.setText(String.valueOf(total));
                correctText.setText(String.valueOf(correct));
                incorrectText.setText(String.valueOf(incorrect));

                shareButton.setOnClickListener(v -> {
                    String profileData = "My Quiz Stats from Personalized Learning App:\n" +
                            "• Username: " + username + "\n" +
                            "• Email: " + (email != null ? email : "Email not found") + "\n" +
                            "• Total Questions: " + total + "\n" +
                            "• Correct Answers: " + correct + "\n" +
                            "• Incorrect Answers: " + incorrect + "\n" +
                            "Try it out yourself!";

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, profileData);
                    startActivity(Intent.createChooser(shareIntent, "Share your profile"));
                });
            } else {
                emailText.setText("N/A");
                totalText.setText("0");
                correctText.setText("0");
                incorrectText.setText("0");
            }
        }

        return view;
    }
}