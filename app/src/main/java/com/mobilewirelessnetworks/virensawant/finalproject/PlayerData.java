package com.mobilewirelessnetworks.virensawant.finalproject;

import java.util.ArrayList;

/**
 * Created by virensawant on 12/3/17.
 */

public class PlayerData {

    private String username;
    private int score;
    private ArrayList<String> gamesPlayed;

    public PlayerData(String username) {
        this.username = username;
        score = 0;
        gamesPlayed = new ArrayList<String>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addGame(String game){
        gamesPlayed.add(game);
    }

    public ArrayList<String> getGamesPlayed(){
        return gamesPlayed;
    }
}
