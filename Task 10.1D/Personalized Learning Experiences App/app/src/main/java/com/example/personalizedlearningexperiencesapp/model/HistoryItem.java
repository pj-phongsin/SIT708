package com.example.personalizedlearningexperiencesapp.model;

public class HistoryItem {
    public String timestamp;
    public String topic;
    public String question;
    public String[] answers;
    public int userAnswerIndex;
    public int correctAnswerIndex;

    public HistoryItem(String timestamp, String topic, String question,
                       String[] answers, int userAnswerIndex, int correctAnswerIndex) {
        this.timestamp = timestamp;
        this.topic = topic;
        this.question = question;
        this.answers = answers;
        this.userAnswerIndex = userAnswerIndex;
        this.correctAnswerIndex = correctAnswerIndex;
    }
}