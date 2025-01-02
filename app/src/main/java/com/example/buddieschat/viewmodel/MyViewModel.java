package com.example.buddieschat.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.buddieschat.auth.FirebaseManager;

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
}
