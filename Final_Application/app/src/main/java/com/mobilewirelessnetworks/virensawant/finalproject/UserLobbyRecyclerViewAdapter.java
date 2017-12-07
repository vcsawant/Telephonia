package com.mobilewirelessnetworks.virensawant.finalproject;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by virensawant on 12/4/17.
 */

public class UserLobbyRecyclerViewAdapter extends RecyclerView.Adapter<UserLobbyRecyclerViewAdapter.CustomViewHolder>{

    private List<PlayerData> userList;


    public UserLobbyRecyclerViewAdapter(List<PlayerData> groupList) {
        this.userList = groupList;
    }


    @Override
    public UserLobbyRecyclerViewAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row_lobby, parent,false);
        return new UserLobbyRecyclerViewAdapter.CustomViewHolder(view);
    }


    @Override
    public void onBindViewHolder(UserLobbyRecyclerViewAdapter.CustomViewHolder holder, int position) {

        final PlayerData item = userList.get(position);
        Resources res = holder.itemView.getContext().getResources();
        boolean ready = true;

        String number = position+"";
        holder.userName.setText(item.getUsername());
        holder.userNumber.setText(number);

        //TODO: query server to see if player is actually ready
        if(ready)
            holder.cardView.setCardBackgroundColor(res.getColor(R.color.playerReady));

    }

    @Override
    public int getItemCount() {
        return (null != userList ? userList.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView userNumber;
        CardView cardView;
        CustomViewHolder(View view) {
            super(view);
            this.cardView = view.findViewById(R.id.card_view);
            this.userName = view.findViewById(R.id.userName);
            this.userNumber = view.findViewById(R.id.userNumber);

        }
    }

    public boolean checkIfAllReady(){
        boolean ready = false;
        for(PlayerData p:userList){
            //TODO: check if user is ready here

        }
        return ready;
    }
}