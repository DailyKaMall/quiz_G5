package com.example.quiz_g5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class QuestionActivity extends AppCompatActivity {
    private TextView question_text_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        question_text_view = (TextView) findViewById(R.id.question_text_view);

        question_text_view.setText("perfetto");
    }
}