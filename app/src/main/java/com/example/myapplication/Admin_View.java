package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Admin_View extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_view);

        Button leaderboard=findViewById(R.id.button4);
        Button addQuestion=findViewById(R.id.button3);
        Button play=findViewById(R.id.button6);

        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_View.this, LeaderBoard.class);


                startActivity(intent);
                finish();
            }
        });

        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_View.this, addQuestion.class);


                startActivity(intent);
                finish();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_View.this, MainActivity.class);


                startActivity(intent);
                finish();
            }
        });
    }
}