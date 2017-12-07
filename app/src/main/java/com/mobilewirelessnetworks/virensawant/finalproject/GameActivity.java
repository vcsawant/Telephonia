package com.mobilewirelessnetworks.virensawant.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private GroupListRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.groups_recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//        List<Group> groupList = new ArrayList<Group>();
//        groupList.add(new Group("group 1",10));
//        groupList.add(new Group("group 2",5));
//        groupList.add(new Group("group 3",8));
//        mRecyclerView.setAdapter(new GroupListRecyclerViewAdapter(groupList));

        new UpdateGroupsTask().execute();

    }

    class UpdateGroupsTask extends AsyncTask<Void,Void,List<Group>> {

        @Override
        protected List<Group> doInBackground(Void... voids) {

            //TODO: fill in the data in groupList with actual data from server
            List<Group> groupList = new ArrayList<Group>();
            groupList.add(new Group("group 1",10));
            groupList.add(new Group("group 2",5));
            groupList.add(new Group("group 3",8));
            return groupList;
        }

        @Override
        protected void onPostExecute(List<Group> groups) {

            adapter = new GroupListRecyclerViewAdapter(groups);
            mRecyclerView.setAdapter(adapter);
            adapter.setOnGroupChooseListener(new GroupListRecyclerViewAdapter.OnGroupChooseListener() {
                @Override
                public void onGroupClick(Group group) {
                    Toast.makeText(getApplicationContext(),"Trying to join " + group.getName(),Toast.LENGTH_SHORT).show();

                    //TODO: actually join the group
                    startActivity(new Intent(getApplicationContext(),WaitingRoomActivity.class));
                }
            });

        }
    }

}
