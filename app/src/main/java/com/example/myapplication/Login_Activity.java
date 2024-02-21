package com.example.myapplication;

import static android.content.ContentValues.TAG;

import static com.example.myapplication.NetworkChecker.isNetworkAvailable;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.WriteResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Login_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Button b2 = (Button) findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText pwd = (EditText) findViewById(R.id.editTextTextPassword);
                String pass = pwd.getText().toString();

                if (!isNetworkAvailable(Login_Activity.this)) {
                    Intent intent =new Intent(Login_Activity.this, Error_Activity.class);
                    intent.putExtra("ERROR_MSG","Internet Is Not Available");

                    startActivity(intent);
                    finish();
                    return;
                }

                validatePassword(pass);
            }
        });


    }

    public void validatePassword(String valueToCheck) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String collectionName = "mangla99";
        String documentId = "Password";
        String fieldName = "password";

        db.collection(collectionName)
                .document(documentId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Document exists
                        Object arrayField = documentSnapshot.get(fieldName);

                        if (arrayField instanceof List) {
                            List<String> arrayValues = (List<String>) arrayField;

                            if (arrayValues.contains(valueToCheck)) {

                                // Data to be added to the document
                                Map<String, Object> data = new HashMap<>();
                                data.put("TeamName", valueToCheck);
                                data.put("QuestionNumber", "1");
                                data.put("Score", "1");



                                String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);


                                if(valueToCheck.equals("admin"))
                                {
                                    data.put("userType","admin");
                                    addDocument(collectionName, androidId, data);

                                    Intent intent = new Intent(Login_Activity.this, Admin_View.class);

                                    startActivity(intent);
                                    finish();
                                }

                                else
                                {
                                    data.put("userType","notadmin");
                                    addDocument(collectionName, androidId, data);
                                    Intent intent = new Intent(Login_Activity.this, MainActivity.class);

                                    startActivity(intent);
                                    finish();
                                }



                            } else {
                                Toast.makeText(getApplicationContext(), "Please enter Correct pwd", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error querying document", e);
                    Intent intent =new Intent(Login_Activity.this, Error_Activity.class);
                    intent.putExtra("ERROR_MSG","Servor Error");

                    startActivity(intent);
                    finish();
                    return;

                });
    }

    public void addDocument(String collectionName, String customDocumentId, Map<String, Object> data) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference document = db.collection(collectionName).document(customDocumentId);
        document.set(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            System.out.println("Document added successfully with custom ID: " + customDocumentId);
                        } else {
                            Exception e = task.getException();
                            e.printStackTrace();

                            Intent intent =new Intent(Login_Activity.this, Error_Activity.class);
                            intent.putExtra("ERROR_MSG","Servor Error");

                            startActivity(intent);
                            finish();
                            return;
                        }
                    }
                });
    }

}
