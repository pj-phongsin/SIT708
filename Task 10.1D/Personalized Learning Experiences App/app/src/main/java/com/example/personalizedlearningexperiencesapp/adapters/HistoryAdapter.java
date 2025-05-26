package com.example.personalizedlearningexperiencesapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalizedlearningexperiencesapp.R;
import com.example.personalizedlearningexperiencesapp.model.HistoryItem;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<HistoryItem> historyList;

    public HistoryAdapter(List<HistoryItem> historyList) {
        this.historyList = historyList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView timestampTopicText, questionText, questionDescription;
        TextView answer1, answer2, answer3;

        public ViewHolder(View view) {
            super(view);
            timestampTopicText = view.findViewById(R.id.timestampTopicText);
            questionText = view.findViewById(R.id.questionText);
            questionDescription = view.findViewById(R.id.questionDescription);
            answer1 = view.findViewById(R.id.answer1);
            answer2 = view.findViewById(R.id.answer2);
            answer3 = view.findViewById(R.id.answer3);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryItem item = historyList.get(position);

        holder.timestampTopicText.setText(item.timestamp + " | " + item.topic);
        holder.questionText.setText(item.question);
        holder.questionDescription.setText("Select the correct answer below:");

        if (item.answers == null || item.answers.length < 3) {
            return;
        }

        for (int i = 0; i < item.answers.length; i++) {
            TextView answerView = null;
            if (i == 0) answerView = holder.answer1;
            if (i == 1) answerView = holder.answer2;
            if (i == 2) answerView = holder.answer3;

            if (answerView != null) {
                String symbol = "⚪"; // default
                if (i == item.userAnswerIndex) symbol = "🔴";
                if (i == item.correctAnswerIndex) symbol = "🟢";

                answerView.setText(symbol + " " + item.answers[i] +
                        (i == item.userAnswerIndex ? " (Your Answer)" : "") +
                        (i == item.correctAnswerIndex ? " (Correct Answer)" : ""));
            }
        }
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }
}