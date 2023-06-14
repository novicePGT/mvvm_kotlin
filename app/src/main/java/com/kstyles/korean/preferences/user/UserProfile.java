package com.kstyles.korean.preferences.user;

import android.content.Context;
import android.content.SharedPreferences;

import com.kstyles.korean.repository.FirebaseManager;
import com.kstyles.korean.repository.user.User;

public class UserProfile {

    private String uid;

    public UserProfile() {
        FirebaseManager firebaseManager = new FirebaseManager();
        User user = firebaseManager.getUser();
        uid = user.getUid();
    }

    public String getUserProfileImageUrl(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(uid, Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_profile", "");
    }
}
