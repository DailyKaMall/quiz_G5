package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class Activity_welcome_screen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);


        ImageView logo = (ImageView) findViewById(R.id.imageView);
        TextView textLogo = (TextView) findViewById((R.id.textView));


        Animation logoanimation = AnimationUtils.loadAnimation(this, R.anim.mysplashanimation);
        logo.startAnimation(logoanimation);

        Animation textanimation = AnimationUtils.loadAnimation(this, R.anim.splash_animation_text);
        textLogo.startAnimation(textanimation);
        // ((Button)findViewById(R.id.button)).setVisibility(View.GONE);

        Button b = findViewById(R.id.button);

        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);



                checkDeviceIdRegistered("mangla99",androidId).thenAccept(data -> {
                    if (data != null) {
                        Intent intent=new Intent(Activity_welcome_screen.this, MainActivity.class);

                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent=new Intent(Activity_welcome_screen.this, Login_Activity.class);

                        startActivity(intent);
                        finish();
                    }
                })
                        .exceptionally(e -> {
                            // Handle exception
                            System.err.println("Error: " + e.getMessage());
                            return null;
                        });

            }
        });


    }

    public CompletableFuture<Map<String, Object>> checkDeviceIdRegistered(String collectionName, String documentId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection(collectionName).document(documentId);

        CompletableFuture<Map<String, Object>> future = new CompletableFuture<>();

        docRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Map<String, Object> data = documentSnapshot.getData();
                        future.complete(data);
                    } else {
                        future.complete(null);
                    }
                })
                .addOnFailureListener(e -> {
                    future.completeExceptionally(e);
                });

        return future;
    }

    }
