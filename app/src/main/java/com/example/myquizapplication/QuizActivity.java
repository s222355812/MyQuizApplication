package com.example.myquizapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private TextView questionTextView;
    private AppCompatButton option1,option2,option3;
    private Button submitButton;
    private ProgressBar progressBar;
    private TextView progressTextView;
    private String userName;

    private Question[] questions;
    private int currentQuestionIndex = 0;
    private int numCorrectAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        // Get the user's name from the intent
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        // Initialize questions array
        questions = new Question[]{
                new Question("What is the capital of France?", new String[]{"Paris", "Berlin", "Rome"}, 0),
                new Question("What is the largest planet in our solar system?", new String[]{"Jupiter", "Saturn", "Uranus"}, 0),
                new Question("What is the tallest mountain in the world?", new String[]{"Mount Everest", "K2", "Lhotse"}, 0)
        };

// Initialize views
        questionTextView = findViewById(R.id.question_textview);
        LinearLayout optionsLinearLayout = findViewById(R.id.optionsLinearLayout);
        option1 = findViewById(R.id.option1_button);
        option2 = findViewById(R.id.option2_button);
        option3 = findViewById(R.id.option3_button);
        submitButton = findViewById(R.id.submit_button);
        progressBar = findViewById(R.id.progress_bar);
        progressTextView = findViewById(R.id.progress_textview);

// Set maximum value of progress bar
        progressBar.setMax(questions.length);


        // Display the first question
        showQuestion(currentQuestionIndex);
// Set OnClickListener for option buttons
        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option1.setSelected(true);
                option2.setSelected(false);
                option3.setSelected(false);
                option1.setBackgroundColor(Color.LTGRAY);
                option2.setBackgroundColor(Color.WHITE);
                option3.setBackgroundColor(Color.WHITE);
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option1.setSelected(false);
                option2.setSelected(true);
                option3.setSelected(false);
                option2.setBackgroundColor(Color.LTGRAY);
                option1.setBackgroundColor(Color.WHITE);
                option3.setBackgroundColor(Color.WHITE);
            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option1.setSelected(false);
                option2.setSelected(false);
                option3.setSelected(true);
                option3.setBackgroundColor(Color.LTGRAY);
                option2.setBackgroundColor(Color.WHITE);
                option1.setBackgroundColor(Color.WHITE);
            }
        });
        // Set click listener for submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current question
                Question currentQuestion = questions[currentQuestionIndex];

                // Check if the selected answer is correct
                boolean isCorrect = false;
                int selectedOptionIndex = -1;
                if (option1.isSelected()) {
                    selectedOptionIndex = 0;
                } else if (option2.isSelected()) {
                    selectedOptionIndex = 1;
                } else if (option3.isSelected()) {
                    selectedOptionIndex = 2;
                }
                if (selectedOptionIndex == currentQuestion.getCorrectOptionIndex()) {
                    isCorrect = true;
                }
                // Submit the answer
                submitAnswer(selectedOptionIndex);
            }

        });
    }
    private void submitAnswer(int selectedOptionIndex) {
        // Get the current question
        Question currentQuestion = questions[currentQuestionIndex];

        // Check if the selected answer is correct
        boolean isCorrect = selectedOptionIndex == currentQuestion.getCorrectOptionIndex();

        // Increment numCorrectAnswers if the selected answer is correct
        if (isCorrect) {
            numCorrectAnswers++;
        }

        // Update button color
        if (isCorrect) {
            switch (selectedOptionIndex) {
                case 0:
                    option1.setBackgroundColor(Color.GREEN);
                    break;
                case 1:
                    option2.setBackgroundColor(Color.GREEN);
                    break;
                case 2:
                    option3.setBackgroundColor(Color.GREEN);
                    break;

            }
        } else {
            switch (selectedOptionIndex) {
                case 0:
                    option1.setBackgroundColor(Color.RED);
                    break;
                case 1:
                    option2.setBackgroundColor(Color.RED);
                    break;
                case 2:
                    option3.setBackgroundColor(Color.RED);
                    break;
            }
            // Set the correct answer button color to green
            switch (currentQuestion.getCorrectOptionIndex()) {
                case 0:
                    option1.setBackgroundColor(Color.GREEN);
                    break;
                case 1:
                    option2.setBackgroundColor(Color.GREEN);
                    break;
                case 2:
                    option3.setBackgroundColor(Color.GREEN);
                    break;
            }
        }

        // Disable the option buttons
        option1.setEnabled(false);
        option2.setEnabled(false);
        option3.setEnabled(false);

        // Update progress bar
        int progress = (int) ((float) (currentQuestionIndex + 1) / questions.length * 100);
        progressBar.setProgress(progress);

        // Change the text and behavior of the submit button
        if (currentQuestionIndex == questions.length - 1) {
            // End of quiz
            submitButton.setText("End");
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Launch FinalScoreActivity and pass the final score and user name as extras
                    Intent intent = new Intent(QuizActivity.this, FinalScoreActivity.class);
                    intent.putExtra("finalScore", numCorrectAnswers);
                    intent.putExtra("userName", userName);
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            // Next question
            submitButton.setText("Next");
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Move to the next question
                    currentQuestionIndex++;
                    // Enable the option buttons again
                    option1.setEnabled(true);
                    option2.setEnabled(true);
                    option3.setEnabled(true);
                    // Show the next question
                    showQuestion(currentQuestionIndex);
                    // Update progress bar
                    progressBar.setProgress((currentQuestionIndex + 1) * 100 / questions.length);
                    // Reset the button color and text
                    resetUI();
                }
            });
        }
    }
    private void resetUI() {
        // Reset the button colors
        option1.setBackgroundColor(Color.WHITE);
        option2.setBackgroundColor(Color.WHITE);
        option3.setBackgroundColor(Color.WHITE);

        // Clear the button selection
        option1.setSelected(false);
        option2.setSelected(false);
        option3.setSelected(false);

        // Reset the submit button text and behavior
        submitButton.setText("Submit");
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected option index
                int selectedOptionIndex = -1;
                if (option1.isSelected()) {
                    selectedOptionIndex = 0;
                } else if (option2.isSelected()) {
                    selectedOptionIndex = 1;
                } else if (option3.isSelected()) {
                    selectedOptionIndex = 2;
                }
                if (selectedOptionIndex >= 0) {
                    submitAnswer(selectedOptionIndex);
                } else {
                    Toast.makeText(QuizActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void showQuestion(int questionIndex) {
        // Get the current question
        Question currentQuestion = questions[questionIndex];

        // Update the question text
        questionTextView.setText(currentQuestion.getQuestionText());

        // Update the option buttons
        option1.setText(currentQuestion.getOptions()[0]);
        option2.setText(currentQuestion.getOptions()[1]);
        option3.setText(currentQuestion.getOptions()[2]);

        // Reset button colors
        option1.setBackgroundColor(Color.WHITE);
        option2.setBackgroundColor(Color.WHITE);
        option3.setBackgroundColor(Color.WHITE);

        // Disable submit button
        // Disable submit button
        submitButton.setText("Submit");
        submitButton.setEnabled(true);


    }


}

