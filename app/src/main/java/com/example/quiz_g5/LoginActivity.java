package com.example.quiz_g5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import android.provider.Settings.Secure;

public class LoginActivity extends AppCompatActivity {
    private EditText login_edit_text;
    private FirebaseFunctions mFunctions;
    private String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_edit_text = (EditText) findViewById(R.id.login_edit_text);

        mFunctions = FirebaseHelper.getFunctionsInstance();

        deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    private Task<String> login(String teamId){

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("teamId", teamId);
        data.put("deviceId", deviceId);

        return mFunctions
                .getHttpsCallable("login")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, String>() {
                    @Override
                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        Map<String, Object> result = (Map<String, Object>) task.getResult().getData();

//                        assert result != null;
                        if (result.isEmpty())
                            return "";

                        return (String) result.get("role");
                    }
                });
    }

    public void onClickLoginButton(View view){
        String teamId = login_edit_text.getText().toString();

        Toast.makeText(this, "This is you Team Id: " + teamId, Toast.LENGTH_SHORT).show();

        login(teamId)
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        String role = task.getResult();

                        if (!role.isEmpty()){
                            Toast.makeText(LoginActivity.this, role, Toast.LENGTH_SHORT).show();

                            Intent question_intent = new Intent(LoginActivity.this, QuestionActivity.class);
                            startActivity(question_intent);
                            finish();

                            return;
                        }

                        Toast.makeText(LoginActivity.this, "wrong teamId", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}