package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class Login_Activity extends AppCompatActivity {

    String pass1="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);


        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        // Reference to the collection
        firestore.collection("mangla99")
                .document("password")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                pass1 = document.getString("password");


                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.w(TAG, "Error getting document", task.getException());
                        }
                    }
                });


        EditText pwd=(EditText)findViewById(R.id.editTextTextPassword);
        String pass = pwd.getText().toString();

        Button b2=(Button)findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText pwd=(EditText)findViewById(R.id.editTextTextPassword);
                String pass = pwd.getText().toString();
                if(pass.trim().equals(pass1))
                {
                    Intent intent=new Intent(Login_Activity.this, MainActivity.class);

                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please enter given password",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
