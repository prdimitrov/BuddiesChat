package com.example.buddieschat.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.buddieschat.views.GroupsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseManager {

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
                            + Build.MODEL
                            + " user!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     *
     * This method returns the ID of the current user
     */
    public String getCurrentUserId() {
        return FirebaseAuth.getInstance().getUid();
    }

    /**
     *
     * This method is used to sign out the user from the application
     */
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }
}
