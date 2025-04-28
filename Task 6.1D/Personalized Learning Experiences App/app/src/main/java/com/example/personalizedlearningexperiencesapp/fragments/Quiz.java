package com.example.personalizedlearningexperiencesapp.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personalizedlearningexperiencesapp.MainActivity;
import com.example.personalizedlearningexperiencesapp.R;
import com.example.personalizedlearningexperiencesapp.model.QuizResponse;

import java.util.ArrayList;

public class Quiz extends Fragment {

    private TextView questionText;
    private RadioGroup optionGroup;
    private RadioButton optionA, optionB, optionC, optionD;
    private Button nextButton;

    private ArrayList<QuizResponse.Question> quizList;
    private int currentQuestionIndex = 0;
    private int score = 0;

    public Quiz() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        questionText = view.findViewById(R.id.questionText);
        optionGroup = view.findViewById(R.id.optionGroup);
        optionA = view.findViewById(R.id.optionA);
        optionB = view.findViewById(R.id.optionB);
        optionC = view.findViewById(R.id.optionC);
        optionD = view.findViewById(R.id.optionD);
        nextButton = view.findViewById(R.id.nextButton);

        Bundle args = getArguments();
        if (args != null) {
            quizList = (ArrayList<QuizResponse.Question>) args.getSerializable("quizData");
        }

        if (quizList != null && !quizList.isEmpty()) {
            loadQuestion(currentQuestionIndex);
        } else {
            Toast.makeText(getContext(), "Quiz data not available", Toast.LENGTH_SHORT).show();
        }

        nextButton.setOnClickListener(v -> {
            if (optionGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(getContext(), "Please select an option", Toast.LENGTH_SHORT).show();
                return;
            }

            checkAnswer();

            currentQuestionIndex++;
            if (currentQuestionIndex < quizList.size()) {
                loadQuestion(currentQuestionIndex);
            } else {
                // End of quiz
                Toast.makeText(getContext(), "Quiz finished! Your score: " + score, Toast.LENGTH_LONG).show();
                goToResultFragment();
            }
        });

        return view;
    }

    private void loadQuestion(int index) {
        QuizResponse.Question question = quizList.get(index);

        questionText.setText(question.getQuestion());
        ArrayList<String> options = new ArrayList<>(question.getOptions());

        optionA.setText(options.get(0));
        optionB.setText(options.get(1));
        optionC.setText(options.get(2));
        optionD.setText(options.get(3));

        optionGroup.clearCheck();
    }

    private void checkAnswer() {
        int selectedId = optionGroup.getCheckedRadioButtonId();
        RadioButton selectedButton = optionGroup.findViewById(selectedId);

        String selectedAnswer = selectedButton.getText().toString();
        String correctAnswer = "";

        // Match correctAnswer to option text
        QuizResponse.Question currentQuestion = quizList.get(currentQuestionIndex);
        switch (currentQuestion.getCorrectAnswer()) {
            case "A":
                correctAnswer = currentQuestion.getOptions().get(0);
                break;
            case "B":
                correctAnswer = currentQuestion.getOptions().get(1);
                break;
            case "C":
                correctAnswer = currentQuestion.getOptions().get(2);
                break;
            case "D":
                correctAnswer = currentQuestion.getOptions().get(3);
                break;
        }

        if (selectedAnswer.equals(correctAnswer)) {
            score++;
        }
    }

    private void goToResultFragment() {
        Result resultFragment = new Result();
        Bundle bundle = new Bundle();
        bundle.putSerializable("quizData", quizList);
        bundle.putInt("score", score);
        resultFragment.setArguments(bundle);

        ((MainActivity) getActivity()).switchFragment(resultFragment);

    }
}