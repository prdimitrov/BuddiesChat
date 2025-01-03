package com.example.buddieschat.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.buddieschat.model.ChatGroup;
import com.example.buddieschat.views.GroupsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseManager {

    MutableLiveData<List<ChatGroup>> chatGroupMutableLiveData;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public FirebaseManager() {
        this.chatGroupMutableLiveData = new MutableLiveData<>();
        this.firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    /**
     * This method does authenticate the anonymous user, that will log in to the application
     */
    public void firebaseAnonymousAuth(Context context) {
        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    // If Auth is successful
                    Intent i = new Intent(context, GroupsActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                    Toast.makeText(context, "Hello "
                                    + Build.MANUFACTURER
                                    + " user!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * This method returns the ID of the current user
     */
    public String getCurrentUserId() {
        return FirebaseAuth.getInstance().getUid();
    }

    /**
     * This method is used to sign out the user from the application
     */
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    // Create a new group
    public void createNewChatGroup(String groupName) {
        databaseReference.child(groupName).setValue(groupName);
    }

    /**
     * This getter method is used to get the available chat groups from Firebase realtime Database
     */

    public MutableLiveData<List<ChatGroup>> getChatGroupMutableLiveData() {
        List<ChatGroup> chatGroupsList = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatGroupsList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ChatGroup group = new ChatGroup(dataSnapshot.getKey());
                    chatGroupsList.add(group);
                }

                chatGroupMutableLiveData.setValue(chatGroupsList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return chatGroupMutableLiveData;
    }
}
