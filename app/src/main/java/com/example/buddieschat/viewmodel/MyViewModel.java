package com.example.buddieschat.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.buddieschat.auth.FirebaseManager;
import com.example.buddieschat.model.ChatGroup;

import java.util.List;

public class MyViewModel extends AndroidViewModel {

    FirebaseManager firebaseManager = new FirebaseManager();

    public MyViewModel(@NonNull Application application) {
        super(application);
    }

    // Auth
    public void signUpAnonymousUser() {
        Context c = this.getApplication();
        firebaseManager.firebaseAnonymousAuth(c);
    }

    // Get user ID
    public String getCurrentUserId() {
        return firebaseManager.getCurrentUserId();
    }

    // Sign out
    public void signOut() {
        firebaseManager.signOut();
    }

    public MutableLiveData<List<ChatGroup>> getGroupsList() {
        return firebaseManager.getChatGroupMutableLiveData();
    }

    public void createNewGroup(String groupName) {
        firebaseManager.createNewChatGroup(groupName);
    }
}
