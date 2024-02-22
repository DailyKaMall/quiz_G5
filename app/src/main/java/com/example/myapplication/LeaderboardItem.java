package com.example.myapplication;

public class LeaderboardItem {

    private String teamName;
    private long score;

    public LeaderboardItem(String teamName, long score) {
        this.teamName = teamName;
        this.score = score;
    }

    public String getTeamName() {
        return teamName;
    }

    public long getScore() {
        return score;
    }
}