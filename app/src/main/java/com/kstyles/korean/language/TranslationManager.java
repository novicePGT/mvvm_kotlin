package com.kstyles.korean.language;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.database.DatabaseError;
import com.kstyles.korean.repository.FirebaseCallback;
import com.kstyles.korean.repository.FirebaseManager;
import com.kstyles.korean.repository.user.User;
import com.kstyles.korean.view.fragment.item.TranslationItem;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class TranslationManager {

    private FirebaseManager firebaseManager;
    private TreeMap<String, TranslationItem> allWordItem;
    private int language;

    public TranslationManager(Context context) {
        firebaseManager = new FirebaseManager();
        allWordItem = new TreeMap<>();
        User user = firebaseManager.getUser();
        String uid = user.getUid();
        SharedPreferences sharedPreferences = context.getSharedPreferences(uid,Context.MODE_PRIVATE);
        language = sharedPreferences.getInt("language_num", 0);

        List<String> levels = Arrays.asList("Beginner", "Intermediate", "Advanced");
        fetchWordsForLevels(levels);
    }

    public String getTranslatedLanguage(String key) {
        TranslationItem translationItem = allWordItem.get(key);

        if (language == 0) {
            return translationItem.getEn();
        }
        if (language == 1) {
            return translationItem.getDe();
        }
        if (language == 2) {
            return translationItem.getFr();
        }
        if (language == 3) {
            return translationItem.getJa();
        }
        if (language == 4) {
            return translationItem.getTh();
        }
        if (language == 5) {
            return translationItem.getVi();
        }
        return null;
    }

    private void fetchWordsForLevels(List<String> levels) {
        for (String level : levels) {
            firebaseManager.getWordByLevel(level, new FirebaseCallback<TreeMap<String, TranslationItem>>() {
                @Override
                public void onSuccess(TreeMap<String, TranslationItem> result) {
                    allWordItem.putAll(result);
                }

                @Override
                public void onFailure(DatabaseError error) {
                    // 실패 시 처리
                }
            });
        }
    }
}
