package com.example.personalizedlearningexperiencesapp.model;

import java.io.Serializable;
import java.util.List;

public class QuizResponse implements Serializable {

    private List<Question> quiz;

    public List<Question> getQuiz() {
        return quiz;
    }

    public void setQuiz(List<Question> quiz) {
        this.quiz = quiz;
    }

    public static class Question implements Serializable {  // <-- Important add Serializable here
        private String question;
        private List<String> options;
        private String correctAnswer;

        public Question(String question, List<String> options, String correctAnswer) {
            this.question = question;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }

        public String getQuestion() {
            return question;
        }

        public List<String> getOptions() {
            return options;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }
    }
}