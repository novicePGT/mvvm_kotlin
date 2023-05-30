package com.kstyles.korean.preferences.user;

import android.content.Context;
import android.content.SharedPreferences;

public class UserProfile {

    public UserProfile() {

    }

    public String getUserProfileImageUrl(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_profile", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_profile", "");
    }
}
