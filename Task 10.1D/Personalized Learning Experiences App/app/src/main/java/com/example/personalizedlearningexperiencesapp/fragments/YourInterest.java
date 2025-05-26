package com.example.personalizedlearningexperiencesapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.personalizedlearningexperiencesapp.MainActivity;
import com.example.personalizedlearningexperiencesapp.R;
import com.example.personalizedlearningexperiencesapp.db.UserDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class YourInterest extends Fragment {

    private final List<String> selectedTopics = new ArrayList<>();
    private final int MAX_SELECTED_TOPICS = 10;

    public YourInterest() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        String username = args.getString("username");
        String email = args.getString("email");
        String password = args.getString("password");
        String phone = args.getString("phone");
        View view = inflater.inflate(R.layout.fragment_your_interest, container, false);

        int[] buttonIds = {
                R.id.algorithms, R.id.testing, R.id.backEnd, R.id.cloud, R.id.machineLearning,
                R.id.appDevelopment, R.id.vr, R.id.blockchain, R.id.frontEnd, R.id.dataAnalysis,
                R.id.network, R.id.ai, R.id.computerArchitecture, R.id.cyberSecurity,
                R.id.webDevelopment, R.id.dataStructure
        };

        for (int id : buttonIds) {
            Button btn = view.findViewById(id);
            btn.setOnClickListener(v -> toggleSelection(btn));
        }

        Button nextButton = view.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(v -> {

            if (selectedTopics.isEmpty()) {
                Toast.makeText(getContext(), "Please select at least one topic.", Toast.LENGTH_SHORT).show();
            } else {
                // Convert list to comma-separated string
                String interests = String.join(",", selectedTopics);

                // Save interests to database
                UserDatabaseHelper dbHelper = new UserDatabaseHelper(getContext());

                boolean success = dbHelper.insertUser(username, email, password, phone, interests);

                if (success) {
                    Toast.makeText(getContext(), "Interests saved!", Toast.LENGTH_SHORT).show();
                    ((MainActivity) getActivity()).switchFragment(new Home());
                } else {
                    Toast.makeText(getContext(), "Failed to save interests", Toast.LENGTH_SHORT).show();
                }
            }

            Home homeFragment = new Home();

            Bundle bundle = new Bundle();
            bundle.putStringArrayList("selectedTopics", new ArrayList<>(selectedTopics));
            bundle.putString("username", username);
            homeFragment.setArguments(bundle);

            ((MainActivity) getActivity()).switchFragment(homeFragment);
        });
        return view;
    }

    private void toggleSelection(Button btn) {
        String topic = btn.getText().toString();
        if (selectedTopics.contains(topic)) {
            selectedTopics.remove(topic);
            btn.setAlpha(1f); // deselected
        } else {
            if (selectedTopics.size() >= MAX_SELECTED_TOPICS) {
                Toast.makeText(getContext(), "You can select up to " + MAX_SELECTED_TOPICS + " topics.", Toast.LENGTH_SHORT).show();
                return;
            }
            selectedTopics.add(topic);
            btn.setAlpha(0.5f); // selected
        }
    }
}