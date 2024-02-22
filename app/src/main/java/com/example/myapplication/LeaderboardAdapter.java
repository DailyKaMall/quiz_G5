package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    private List<LeaderboardItem> leaderboardItems;

    public LeaderboardAdapter(List<LeaderboardItem> leaderboardItems) {
        this.leaderboardItems = leaderboardItems;
    }

    public void setLeaderboardItems(List<LeaderboardItem> leaderboardItems) {
        this.leaderboardItems = leaderboardItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_leaderboard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LeaderboardItem item = leaderboardItems.get(position);
        holder.teamNameTextView.setText(item.getTeamName());
        holder.scoreTextView.setText(String.valueOf(item.getScore()));
    }

    @Override
    public int getItemCount() {
        return leaderboardItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView teamNameTextView;
        public TextView scoreTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teamNameTextView = itemView.findViewById(R.id.teamNameTextView);
            scoreTextView = itemView.findViewById(R.id.scoreTextView);
        }
    }
}
