package com.example.quiz_g5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.HashMap;
import java.util.Map;

public class WelcomeActivity extends AppCompatActivity {
    TextView welcome_text_view;
    Button ok_button;
    private String deviceId;
    private FirebaseFunctions mFunctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        welcome_text_view = (TextView) findViewById(R.id.welcome_text_view);
        ok_button = (Button) findViewById(R.id.ok_button);
        mFunctions = FirebaseFunctions.getInstance();
        deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    private Task<Boolean> isLoggedIn(){

        Map<String, Object> data = new HashMap<>();
        data.put("deviceId", deviceId);

        return mFunctions
                .getHttpsCallable("isLoggedIn")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, Boolean>() {
                    @Override
                    public Boolean then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        return (Boolean) task.getResult().getData();
                    }
                });
    }

    public void onClickOkButton(View view){

        isLoggedIn()
                .addOnCompleteListener(new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        Boolean isLoggedIn = task.getResult();

                        if (isLoggedIn){
                            Toast.makeText(WelcomeActivity.this, "You are already logged in", Toast.LENGTH_SHORT).show();
                        }
                        Intent login_intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                        startActivity(login_intent);
                    }
                });
    }
}