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
import android.util.Log;

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
    private String username;

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
            username = args.getString("username", "guest");
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

        ArrayList<String> options = new ArrayList<>(currentQuestion.getOptions());

        int userAnswerIndex = options.indexOf(selectedAnswer);
        int correctAnswerIndex = 0;
        switch (currentQuestion.getCorrectAnswer()) {
            case "A": correctAnswerIndex = 0; break;
            case "B": correctAnswerIndex = 1; break;
            case "C": correctAnswerIndex = 2; break;
            case "D": correctAnswerIndex = 3; break;
        }

        boolean isCorrect = userAnswerIndex == correctAnswerIndex;
        String answerOptions = String.join(",", options);
        String topic = "General"; // You can replace this with actual topic if available
        String timestamp = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault()).format(new java.util.Date());

        com.example.personalizedlearningexperiencesapp.db.UserDatabaseHelper dbHelper =
                new com.example.personalizedlearningexperiencesapp.db.UserDatabaseHelper(getContext());

        dbHelper.insertQuizResult(username,
            currentQuestion.getQuestion(),
            isCorrect,
            userAnswerIndex,
            correctAnswerIndex,
            answerOptions,
            topic,
            timestamp);

        Log.d("QuizDebug", "Inserted quiz result for user: " + username);
    }

    private void goToResultFragment() {
        Log.d("QuizDebug", "Navigating to result with username: " + username);
        Result resultFragment = new Result();
        Bundle bundle = new Bundle();
        bundle.putSerializable("quizData", quizList);
        bundle.putInt("score", score);
        bundle.putString("username", username);
        resultFragment.setArguments(bundle);

        ((MainActivity) getActivity()).switchFragment(resultFragment);

    }
}