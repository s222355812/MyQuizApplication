package com.example.myquizapplication;

public class Question {
    private String questionText;
    private String[] options;
    private int correctOptionIndex;
    private int userAnswerIndex;

    public Question(String questionText, String[] options, int correctOptionIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }

    public int getUserAnswerIndex() {
        return userAnswerIndex;
    }

    public void setUserAnswerIndex(int userAnswerIndex) {
        this.userAnswerIndex = userAnswerIndex;
    }

    public boolean isAnsweredCorrectly() {
        return userAnswerIndex == correctOptionIndex;
    }
}

