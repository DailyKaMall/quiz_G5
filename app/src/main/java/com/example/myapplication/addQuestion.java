package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class addQuestion extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addquestion);

        Button submit=(Button) findViewById(R.id.button5);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText questionNumber = (EditText) findViewById(R.id.questionNumber);
                String qn = questionNumber.getText().toString();

                EditText question = (EditText) findViewById(R.id.editTextQuestion);
                String que = question.getText().toString();

                EditText option1 = (EditText) findViewById(R.id.editTextOption1);
                String op1 = option1.getText().toString();

                EditText option2 = (EditText) findViewById(R.id.editTextOption2);
                String op2 = option2.getText().toString();

                EditText option3 = (EditText) findViewById(R.id.editTextOption3);
                String op3 = option3.getText().toString();

                EditText option4 = (EditText) findViewById(R.id.editTextOption4);
                String op4 = option4.getText().toString();

                EditText answer = (EditText) findViewById(R.id.editTextAnswer);
                String ans= answer.getText().toString();

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                Map<String, Object> data = new HashMap<>();
                data.put("question", que);
                data.put("option1", op1);
                data.put("option2", op2);
                data.put("option3", op3);
                data.put("option4", op4);
                data.put("answer", ans);
                String documentId = qn;
                addDocument("mangla99", qn, data);

            }
        });
    }

    private void addDocument(String collection, String documentId, Map<String, Object> data) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(collection)
                .document(documentId)
                .set(data)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Question added", Toast.LENGTH_SHORT).show();
                    clearEditTextFields();
                })
                .addOnFailureListener(e -> {
                    // Handle errors
                });
    }


    private void clearEditTextFields() {
        EditText questionNumber = findViewById(R.id.questionNumber);
        EditText question = findViewById(R.id.editTextQuestion);
        EditText option1 = findViewById(R.id.editTextOption1);
        EditText option2 = findViewById(R.id.editTextOption2);
        EditText option3 = findViewById(R.id.editTextOption3);
        EditText option4 = findViewById(R.id.editTextOption4);
        EditText answer = findViewById(R.id.editTextAnswer);

        // Set empty string to clear the EditText fields
        questionNumber.setText("");
        question.setText("");
        option1.setText("");
        option2.setText("");
        option3.setText("");
        option4.setText("");
        answer.setText("");
    }
}
