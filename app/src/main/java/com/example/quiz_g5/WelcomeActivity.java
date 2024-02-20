package com.example.quiz_g5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {
    TextView welcome_text_view;
    Button login_page_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        welcome_text_view = (TextView) findViewById(R.id.welcome_text_view);
        login_page_button = (Button) findViewById(R.id.login_page_button);
    }

    public void onClickLoginPageButton(View view){
        Intent login_intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(login_intent);
    }
}