package com.example.myquizapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FinalScoreActivity extends AppCompatActivity {
    private TextView scoreTextView;
    private Button takeNewQuizButton;
    private Button finishButton;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_score);

        // Get the user's name from the intent
        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        // Get the final score from the intent
        int finalScore = intent.getIntExtra("finalScore", 0);

        // Set the score text view
        scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText("Congratulations " + name + "! Your final score is " + finalScore);

        // Set up the take new quiz button
        takeNewQuizButton = findViewById(R.id.takeNewQuizButton);
        takeNewQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch the main activity with the user's name as an extra
                Intent intent = new Intent(FinalScoreActivity.this, QuizActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });

        // Set up the finish button
        finishButton = findViewById(R.id.finishButton);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear the name
                name = null;

                finish();
            }
        });
    }
}
