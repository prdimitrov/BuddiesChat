package com.example.buddieschat.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buddieschat.R;
import com.example.buddieschat.databinding.ItemCardBinding;
import com.example.buddieschat.model.ChatGroup;

import java.util.ArrayList;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    private ArrayList<ChatGroup> groupsArrayList;

    public GroupAdapter(ArrayList<ChatGroup> groupsArrayList) {
        this.groupsArrayList = groupsArrayList;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_card,
                parent, false);

        return new GroupViewHolder(binding);
    }

    /**
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        ChatGroup currentGroup = groupsArrayList.get(position);
        holder.itemCardBinding.setChatGroup(currentGroup);
    }

    @Override
    public int getItemCount() {
        return groupsArrayList.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {
        // Cache references to the individual views with in an item layout of a recyclerView list
        private ItemCardBinding itemCardBinding;

        public GroupViewHolder(ItemCardBinding itemCardBinding) {
            super(itemCardBinding.getRoot());
            this.itemCardBinding = itemCardBinding;

            itemCardBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();


                }
            });

        }
    }
}
