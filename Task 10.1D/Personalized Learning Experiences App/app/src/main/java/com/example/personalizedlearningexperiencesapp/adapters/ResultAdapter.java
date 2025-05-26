package com.example.personalizedlearningexperiencesapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalizedlearningexperiencesapp.R;
import com.example.personalizedlearningexperiencesapp.model.QuizResponse;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {

    private ArrayList<QuizResponse.Question> quizList;

    public ResultAdapter(ArrayList<QuizResponse.Question> quizList) {
        this.quizList = quizList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_result_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuizResponse.Question question = quizList.get(position);
        holder.questionTextView.setText((position + 1) + ". " + question.getQuestion());

        String correctOption = "";
        switch (question.getCorrectAnswer()) {
            case "A":
                correctOption = question.getOptions().get(0);
                break;
            case "B":
                correctOption = question.getOptions().get(1);
                break;
            case "C":
                correctOption = question.getOptions().get(2);
                break;
            case "D":
                correctOption = question.getOptions().get(3);
                break;
        }
        holder.correctAnswerTextView.setText("Correct Answer: " + correctOption);
    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView, correctAnswerTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.questionTextView);
            correctAnswerTextView = itemView.findViewById(R.id.correctAnswerTextView);
        }
    }
}