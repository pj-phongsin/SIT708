package com.example.personalizedlearningexperiencesapp.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.personalizedlearningexperiencesapp.MainActivity;
import com.example.personalizedlearningexperiencesapp.R;
import com.example.personalizedlearningexperiencesapp.adapters.ResultAdapter;
import com.example.personalizedlearningexperiencesapp.model.QuizResponse;

import java.util.ArrayList;

public class Result extends Fragment {

    private RecyclerView recyclerView;
    private Button continueButton;

    public Result() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        recyclerView = view.findViewById(R.id.resultRecyclerView);
        continueButton = view.findViewById(R.id.continueButton);

        TextView resultTitle = view.findViewById(R.id.resultTitle);
        Bundle args = getArguments();
        if (args != null) {
            int score = args.getInt("score", 0);  // 0 is the default if not found
            resultTitle.setText("Your Score: " + score);  // <-- update the title with score
        }

        ArrayList<QuizResponse.Question> quizList = null;
        if (args != null) {
            quizList = (ArrayList<QuizResponse.Question>) args.getSerializable("quizData");
        }

        if (quizList != null) {
            ArrayList<String> questions = new ArrayList<>();
            ArrayList<String> responses = new ArrayList<>();

            for (QuizResponse.Question question : quizList) {
                questions.add(question.getQuestion());
                responses.add("Generated response for: " + question.getQuestion());
            }

            ResultAdapter adapter = new ResultAdapter(quizList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
        }

        continueButton.setOnClickListener(v -> {
            // Go back to Home or another action
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).switchFragment(new Home());
            }
        });

        return view;
    }
}