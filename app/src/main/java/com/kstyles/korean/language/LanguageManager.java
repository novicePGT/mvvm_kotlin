package com.kstyles.korean.language;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.kstyles.korean.repository.FirebaseManager;
import com.kstyles.korean.repository.user.User;

import java.util.Locale;

public class LanguageManager {

    private Context context;
    private String currentLanguage;
    private FirebaseManager firebaseManager;

    public LanguageManager(Context context) {
        this.context = context;
        firebaseManager = new FirebaseManager();
        User user = firebaseManager.getUser();
        String uid = user.getUid();
        SharedPreferences sharedPreferences = context.getSharedPreferences(uid, Context.MODE_PRIVATE);
        currentLanguage = sharedPreferences.getString("language", "ko");
    }

    public void setLanguage() {
        Locale locale = new Locale(currentLanguage);
        Locale.setDefault(locale);

        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    public String getTranslatedString(int stringResourceId) {
        return context.getResources().getString(stringResourceId);
    }
}
