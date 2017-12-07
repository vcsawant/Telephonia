package com.mobilewirelessnetworks.virensawant.finalproject;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by virensawant on 12/3/17.
 */

public class GroupListRecyclerViewAdapter extends RecyclerView.Adapter<GroupListRecyclerViewAdapter.CustomViewHolder>{

    private List<Group> groupList;


    public GroupListRecyclerViewAdapter(List<Group> groupList) {
        this.groupList = groupList;
    }


    @Override
    public GroupListRecyclerViewAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_row, parent,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupListRecyclerViewAdapter.CustomViewHolder holder, int position) {

        final Group item = groupList.get(position);

        String size = String.valueOf(item.getSize()) + "/" + String.valueOf(item.getCapacity());
        holder.groupName.setText(item.getName());
        holder.groupSize.setText(size);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onGroupClick(item);
            }
        };

        holder.cardView.setOnClickListener(listener);
        holder.groupName.setOnClickListener(listener);
        holder.groupSize.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return (null != groupList ? groupList.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView groupName;
        TextView groupSize;
        CardView cardView;
        CustomViewHolder(View view) {
            super(view);
            this.cardView = view.findViewById(R.id.card_view);
            this.groupName = view.findViewById(R.id.groupName);
            this.groupSize = view.findViewById(R.id.groupSize);
        }
    }

    private GroupListRecyclerViewAdapter.OnGroupChooseListener onItemClickListener;
    public GroupListRecyclerViewAdapter.OnGroupChooseListener getOnGroupChooseListener() {
        return onItemClickListener;
    }

    public void setOnGroupChooseListener(GroupListRecyclerViewAdapter.OnGroupChooseListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnGroupChooseListener {
        void onGroupClick(Group group);
    }
}
