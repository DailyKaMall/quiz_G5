package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        firestore.collection("mangla99").document(androidId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            // Access the values in the document
                            String fieldValue = documentSnapshot.getString("QuestionNumber");
                            setparameters(fieldValue);
                        }
                    }
                });


    }

    private void setparameters(String fieldValue) {

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("mangla99").document(fieldValue)
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

                            // Set click listener for the submit button
                            submitButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Get the selected RadioButton's ID
                                    int selectedRadioButtonId = optionsRadioGroup.getCheckedRadioButtonId();

                                    if (selectedRadioButtonId != -1) {
                                        // Find the RadioButton by its ID
                                        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);

                                        // Get the text of the selected option
                                        String selectedOption = selectedRadioButton.getText().toString();

                                        // Process the selected option (e.g., show a toast)

                                        if (selectedOption.equals(answer))
                                            Toast.makeText(MainActivity.this, "Selected Option: " + selectedOption, Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                        }
                    }
                });
    }
}