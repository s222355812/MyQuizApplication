package com.example.myquizapplication;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText nameEditText;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.nameEditText);
        startButton = findViewById(R.id.startButton);

        // Set click listener for the start button
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                if (name.isEmpty()) {
                    nameEditText.setError("Please enter your name");
                } else {
                    // Start the quiz activity with the user's name as an extra
                    Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                    intent.putExtra("name", name);
                    startActivity(intent);
                }
            }
        });
    }
}
