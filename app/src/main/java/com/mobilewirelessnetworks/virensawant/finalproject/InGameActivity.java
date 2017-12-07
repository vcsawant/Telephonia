package com.mobilewirelessnetworks.virensawant.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InGameActivity extends AppCompatActivity {


    final private String MYPREFS = "TELEPHONE_PREFS";
    private int playerTurn;
    SharedPreferences prefs;
    CardView currentPlayingCard;
    TextView timerView;

    private int currentTurn = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prefs = getSharedPreferences(MYPREFS,0);


        String username = prefs.getString("KEY_USERNAME",null);
        playerTurn = getOrderNumberFromServer(username);
        timerView = findViewById(R.id.timer);

        Toast.makeText(getApplicationContext(),"Username is " + username, Toast.LENGTH_LONG).show();
        currentTurn = 1;

        Button returnHome = (Button) findViewById(R.id.returnHome);
        returnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InGameActivity.this,LoginActivity.class));
            }
        });

        CountDownTimer timer = new CountDownTimer(11000,1000) {
            @Override
            public void onTick(long l) {

                String time = (l/1000 ) + "";
                timerView.setText(time);
            }

            @Override
            public void onFinish() {

                if (currentPlayingCard != null)
                    currentPlayingCard.setCardBackgroundColor(getResources().getColor(R.color.cardview_light_background));

                switch (currentTurn) {
                    case 1:
                        currentPlayingCard = findViewById(R.id.one);
                        break;
                    case 2:
                        currentPlayingCard = findViewById(R.id.two);
                        break;
                    case 3:
                        currentPlayingCard = findViewById(R.id.three);
                        break;
                    case 4:
                        currentPlayingCard = findViewById(R.id.four);
                        break;
                    case 5:
                        currentPlayingCard = findViewById(R.id.five);
                        break;
                    case 6:
                        break;
                    default:
                        currentPlayingCard = findViewById(R.id.one);
                }
                currentPlayingCard.setCardBackgroundColor(getResources().getColor(R.color.playerReady));

                EditText word = findViewById(R.id.word);
                if(currentTurn == playerTurn){

                    word.setVisibility(View.VISIBLE);
                }else
                    word.setVisibility(View.GONE);

                if (currentTurn <= 5){
                    currentTurn++;
                    start();
                }
                else {
                    findViewById(R.id.scoreOne).setVisibility(View.VISIBLE);
                    findViewById(R.id.scoreTwo).setVisibility(View.VISIBLE);
                    findViewById(R.id.scoreThree).setVisibility(View.VISIBLE);
                    findViewById(R.id.scoreFour).setVisibility(View.VISIBLE);
                    findViewById(R.id.scoreFive).setVisibility(View.VISIBLE);
                    findViewById(R.id.returnHome).setVisibility(View.VISIBLE);
                }

            }
        };

        timer.start();

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
