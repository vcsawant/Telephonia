package com.mobilewirelessnetworks.virensawant.finalproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class WaitingRoomActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private UserLobbyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_room);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mRecyclerView = (RecyclerView) findViewById(R.id.players_recyler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        new LoadPlayersTask().execute();
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


    }

    class LoadPlayersTask extends AsyncTask<Void,Void,List<PlayerData>> {

        @Override
        protected List<PlayerData> doInBackground(Void... voids) {

            //TODO: fill in the data in groupList with actual data from server
            List<PlayerData> playerList = new ArrayList<PlayerData>();
            playerList.add(new PlayerData("matched_player 1"));
            playerList.add(new PlayerData("matched_player 2"));
            playerList.add(new PlayerData("matched_player 3"));
            playerList.add(new PlayerData("matched_player 4"));
            playerList.add(new PlayerData("matched_player 5"));
            return playerList;
        }

        @Override
        protected void onPostExecute(List<PlayerData> players) {

            adapter = new UserLobbyRecyclerViewAdapter(players);
            mRecyclerView.setAdapter(adapter);

        }
    }


    class UpdatePlayerStatusTask extends AsyncTask<Void,Void,Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            if(adapter.checkIfAllReady())
                startActivity(new Intent(getApplicationContext(),InGameActivity.class));
            //adapter.notifyDataSetChanged();
            return null;
        }
    }
}
