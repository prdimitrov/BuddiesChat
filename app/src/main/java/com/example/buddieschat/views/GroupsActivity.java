package com.example.buddieschat.views;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buddieschat.R;
import com.example.buddieschat.databinding.ActivityGroupsBinding;
import com.example.buddieschat.model.ChatGroup;
import com.example.buddieschat.viewmodel.MyViewModel;
import com.example.buddieschat.views.adapters.GroupAdapter;

import java.util.ArrayList;
import java.util.List;

public class GroupsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ActivityGroupsBinding activityGroupsBinding;
    private MyViewModel myViewModel;
    private ArrayList<ChatGroup> chatGroupArrayList;
    private GroupAdapter groupAdapter;

    private Dialog chatGroupDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        activityGroupsBinding = DataBindingUtil.setContentView(this, R.layout.activity_groups);
        activityGroupsBinding.setActivity(this);

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        // Recycler view with Data Binding

        recyclerView = activityGroupsBinding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Setup an observer to listen for changes in a "Live Data" object
        myViewModel.getGroupsList().observe(this, new Observer<List<ChatGroup>>() {
            @Override
            public void onChanged(List<ChatGroup> chatGroups) {
                // The updated data is received as "chatGroups" parameter in onChanged()
                chatGroupArrayList = new ArrayList<>();
                chatGroupArrayList.addAll(chatGroups);

                groupAdapter = new GroupAdapter(chatGroupArrayList);

                recyclerView.setAdapter(groupAdapter);

                groupAdapter.notifyDataSetChanged();
            }
        });
    }

    public void showDialog() {
        chatGroupDialog = new Dialog(this);
        chatGroupDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        View view = LayoutInflater.from(this)
                .inflate(R.layout.dialog_layout, null);

        chatGroupDialog.setContentView(view);
        chatGroupDialog.show();

        Button submitButton = view.findViewById(R.id.createGroupSubmitButton);
        EditText editText = view.findViewById(R.id.createGroupEditText);


        submitButton.setOnClickListener(v -> {
            String groupName = editText.getText().toString().trim();
            Toast.makeText(GroupsActivity.this, "Group name: " + groupName, Toast.LENGTH_SHORT).show();

            myViewModel.createNewGroup(groupName);

            chatGroupDialog.dismiss();
        });

    }
}