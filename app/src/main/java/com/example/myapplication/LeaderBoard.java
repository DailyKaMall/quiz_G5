package com.example.myapplication;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LeaderBoard extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LeaderboardAdapter leaderboardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter with an empty list
        leaderboardAdapter = new LeaderboardAdapter(new ArrayList<>());
        recyclerView.setAdapter(leaderboardAdapter);

        // Fetch scores from Firestore
        fetchScoresFromFirestore();
    }

    private void fetchScoresFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("mangla99")
                .orderBy("score", Query.Direction.DESCENDING)
                .limit(10) // Adjust the limit as needed
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<LeaderboardItem> leaderboardItems = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String teamName = document.getString("teamName");
                                String scoreString = document.getString("score");
                                long score = Long.parseLong(scoreString);
                                leaderboardItems.add(new LeaderboardItem(teamName, score));
                            }

                            // Sort the list in descending order
                            Collections.sort(leaderboardItems, new Comparator<LeaderboardItem>() {
                                @Override
                                public int compare(LeaderboardItem item1, LeaderboardItem item2) {
                                    return Long.compare(item2.getScore(), item1.getScore());
                                }
                            });

                            // Update the adapter with the fetched scores
                            leaderboardAdapter.setLeaderboardItems(leaderboardItems);
                        } else {
                            // Handle errors
                        }
                    }
                });
    }

}
