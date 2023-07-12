package com.kstyles.korean.language;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.database.DatabaseError;
import com.kstyles.korean.repository.FirebaseCallback;
import com.kstyles.korean.repository.FirebaseManager;
import com.kstyles.korean.repository.user.User;
import com.kstyles.korean.view.fragment.item.TranslationItem;

import java.util.TreeMap;

public class TranslationManager {

    private FirebaseManager firebaseManager;
    private TreeMap<String, TranslationItem> allWordItem;
    private String language;

    public TranslationManager(Context context) {
        firebaseManager = new FirebaseManager();
        User user = firebaseManager.getUser();
        String uid = user.getUid();
        SharedPreferences sharedPreferences = context.getSharedPreferences(uid,Context.MODE_PRIVATE);
        language = sharedPreferences.getString("language", "en");

        firebaseManager.getAllWordItem(new FirebaseCallback<TreeMap<String, TranslationItem>>() {
            @Override
            public void onSuccess(TreeMap<String, TranslationItem> wordItems) {
                allWordItem = wordItems;
            }

            @Override
            public void onFailure(DatabaseError error) {

            }
        });
    }

    public String getTranslatedLanguage(String key) {
        TranslationItem translationItem = allWordItem.get(key);

        if (language.equals("en")) {
            return translationItem.getEn();
        }
        if (language.equals("de")) {
            return translationItem.getDe();
        }
        if (language.equals("fr")) {
            return translationItem.getFr();
        }
        if (language.equals("ja")) {
            return translationItem.getJa();
        }
        if (language.equals("th")) {
            return translationItem.getTh();
        }
        if (language.equals("vi")) {
            return translationItem.getVi();
        }
        return null;
    }
}
