package com.example.myapplication;

import static com.example.myapplication.NetworkChecker.isNetworkAvailable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Error_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.error_activity);
        Intent intent = getIntent();
        String str = intent.getStringExtra("ERROR_MSG");
        ((TextView)findViewById(R.id.lblErrorMsg)).setText(str);

        findViewById(R.id.retry_button).setOnClickListener(this::onClick);
    }

    public void onClick(View v) {
        if (isNetworkAvailable(this)) {
            finish(); // Close error activity
            startActivity(new Intent(Error_Activity.this, Activity_welcome_screen.class));
        } else {
            Toast.makeText(Error_Activity.this, "Internet still unavailable!", Toast.LENGTH_SHORT).show();
        }
    }


}