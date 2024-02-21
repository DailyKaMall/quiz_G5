package com.example.myapplication;

import static com.example.myapplication.NetworkChecker.isNetworkAvailable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler(Looper.getMainLooper());
    private int timerValue = 0;
    private static final int INTERVAL = 10000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        getQuestion();

    }

    public void getQuestion()
    {

        if (!isNetworkAvailable(MainActivity.this)) {
            Intent intent =new Intent(MainActivity.this, Error_Activity.class);
            intent.putExtra("ERROR_MSG","Internet Is Not Available");

            startActivity(intent);
            finish();
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("eventdetails").document("event");

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Timestamp firestoreTimestamp = documentSnapshot.getTimestamp("end");
                    Timestamp currentTimestamp = Timestamp.now();

                    if(firestoreTimestamp.compareTo(currentTimestamp) < 0)
                    {
                        Intent intent =new Intent(MainActivity.this, Error_Activity.class);
                        intent.putExtra("ERROR_MSG","Please wait for event to resume");

                        startActivity(intent);
                        finish();
                        return;

                    }

                    else
                    {
                        updateUI();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Firestore", "Error getting document", e);
                Intent intent =new Intent(MainActivity.this, Error_Activity.class);
                intent.putExtra("ERROR_MSG","Servor Error");

                startActivity(intent);
                finish();
                return;
            }
        });
    }

    public void updateUI()
    {
        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);





        if (!isNetworkAvailable(MainActivity.this)) {
            Intent intent =new Intent(MainActivity.this, Error_Activity.class);
            intent.putExtra("ERROR_MSG","Internet Is Not Available");

            startActivity(intent);
            finish();
            return;
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("mangla99").document(androidId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String questionNumber = documentSnapshot.getString("QuestionNumber");
                    String score=documentSnapshot.getString("Score");
                    setparameters(questionNumber,score);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Firestore", "Error getting document", e);
                Intent intent =new Intent(MainActivity.this, Error_Activity.class);
                intent.putExtra("ERROR_MSG","Servor Error");

                startActivity(intent);
                finish();
                return;
            }
        });
    }

    private void setparameters(String questionNumber,String score) {


        if (!isNetworkAvailable(MainActivity.this)) {
            Intent intent =new Intent(MainActivity.this, Error_Activity.class);
            intent.putExtra("ERROR_MSG","Internet Is Not Available");

            startActivity(intent);
            finish();
            return;
        }

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("mangla99").document(questionNumber)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {



                        if (documentSnapshot.exists()) {


                            String Question = documentSnapshot.getString("question");
                            String option1 = documentSnapshot.getString("option1");
                            String option2 = documentSnapshot.getString("option2");
                            String option3 = documentSnapshot.getString("option3");
                            String option4 = documentSnapshot.getString("option4");
                            String answer = documentSnapshot.getString("answer");


                            RadioGroup optionsRadioGroup = findViewById(R.id.optionsRadioGroup);
                            Button submitButton = findViewById(R.id.submitButton);

                            TextView que = findViewById(R.id.questionTextView);
                            que.setText(Question);


                            RadioButton op1 = findViewById(R.id.optionARadioButton);
                            op1.setText(option1);
                            RadioButton op2 = findViewById(R.id.optionBRadioButton);
                            op2.setText(option2);
                            RadioButton op3 = findViewById(R.id.optionCRadioButton);
                            op3.setText(option3);
                            RadioButton op4 = findViewById(R.id.optionDRadioButton);
                            op4.setText(option4);

                            startTimer(questionNumber);

                            // Set click listener for the submit button
                            submitButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    if (!isNetworkAvailable(MainActivity.this)) {
                                        Intent intent =new Intent(MainActivity.this, Error_Activity.class);
                                        intent.putExtra("ERROR_MSG","Internet Is Not Available");

                                        startActivity(intent);
                                        finish();
                                        return;
                                    }
                                    int selectedRadioButtonId = optionsRadioGroup.getCheckedRadioButtonId();

                                    if (selectedRadioButtonId != -1) {

                                        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                                        String selectedOption = selectedRadioButton.getText().toString();

                                        if (selectedOption.equals(answer))
                                        {
                                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                                            String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                                            DocumentReference docRef = db.collection("mangla99").document(androidId);

                                            Map<String, Object> updates = new HashMap<>();
                                            int qn=Integer.parseInt(questionNumber);
                                            int sc=Integer.parseInt(score);

                                            String que=String.valueOf(qn+1);
                                            String sco=String.valueOf(sc+1);
                                            updates.put("QuestionNumber", que);
                                            updates.put("Score",sco);

                                            docRef.update(updates)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Log.d("Firestore", "Document successfully updated!");
                                                            updateUI();
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.e("Firestore", "Error updating document", e);
                                                        }
                                                    });
                                        }


                                    }
                                }
                            });

                        }
                    }
                });


    }

    private void startQuestionNumberUpdateTimer(String questionNumber) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                timerValue++;

                if (timerValue * 1000 >= INTERVAL) {
                    // Update question number after the fixed interval
                    updateQuestionNumber(questionNumber);

                    // Reset timer value
                    timerValue = 0;
                    updateUI();
                }

                // Schedule the next update after 1 second
                handler.postDelayed(this, 1000);
            }
        }, 1000); // Initial delay is 1 second
    }

    private void updateQuestionNumber(String currentQuestionNumber) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        DocumentReference docRef = db.collection("mangla99").document(androidId);

        Map<String, Object> updates = new HashMap<>();
        int currentQn = Integer.parseInt(currentQuestionNumber);

        String newQuestionNumber = String.valueOf(currentQn + 1);
        updates.put("QuestionNumber", newQuestionNumber);

        docRef.update(updates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firestore", "Question number successfully updated!");
                        // You may choose to update the UI here if needed
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Firestore", "Error updating question number", e);
                    }
                });
    }

    private void startTimer(String questionNumber) {
        startQuestionNumberUpdateTimer(questionNumber);
    }
}