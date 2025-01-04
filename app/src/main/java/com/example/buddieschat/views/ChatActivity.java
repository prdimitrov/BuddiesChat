package com.example.buddieschat.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buddieschat.R;
import com.example.buddieschat.databinding.ActivityChatBinding;
import com.example.buddieschat.model.ChatMessage;
import com.example.buddieschat.viewmodel.MyViewModel;
import com.example.buddieschat.views.adapters.ChatAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private ActivityChatBinding binding;
    private MyViewModel myViewModel;
    private RecyclerView recyclerView;
    private ChatAdapter myAdapter;
    private List<ChatMessage> messagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        // RecyclerView with DataBinding
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // Getting the group name from the clicked item in the GroupsActivity
        String groupName = getIntent().getStringExtra("GROUP_NAME");

        myViewModel.getMessagesLiveData(groupName).observe(this, new Observer<List<ChatMessage>>() {
            @Override
            public void onChanged(List<ChatMessage> chatMessages) {
                messagesList = new ArrayList<>();
                messagesList.addAll(chatMessages);

                myAdapter = new ChatAdapter(messagesList, getApplicationContext());

                recyclerView.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();

                // Scroll to the latest message added:
                int latestPosition = myAdapter.getItemCount() - 1;
                if (latestPosition > 0) {
                    recyclerView.smoothScrollToPosition(latestPosition);
                }
            }
        });

        binding.setVModel(myViewModel);

        binding.sendButton.setOnClickListener(v -> {
            String msg = binding.edittextChatMessage.getText().toString().trim();

            myViewModel.sendMessage(msg, groupName);

            binding.edittextChatMessage.getText().clear();
        });

    }
}