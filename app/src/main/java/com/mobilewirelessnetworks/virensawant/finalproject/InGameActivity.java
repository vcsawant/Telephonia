package com.mobilewirelessnetworks.virensawant.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class InGameActivity extends AppCompatActivity {


    final private String MYPREFS = "TELEPHONE_PREFS";
    private int playerTurn;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prefs = getSharedPreferences(MYPREFS,0);


        String username = prefs.getString("KEY_USERNAME",null);

        playerTurn = getOrderNumberFromServer(username);
        Toast.makeText(getApplicationContext(),"Username is " + username, Toast.LENGTH_LONG).show();

    }

    private int getOrderNumberFromServer(String username){

        //TODO: get the players position in the order
        if(username == null){
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
        return 1;
    }

    class LoadNextTurn extends AsyncTask<Integer,Void,Void>{

        int turn;

        @Override
        protected Void doInBackground(Integer... integers) {

            turn = integers[0];
            if(playerTurn == turn){
                //TODO: play the player's turn
            }

            while(true){
                //TODO: query the server to see if the turn is finished
                break;
            }
            return null;
        }
    }
}
