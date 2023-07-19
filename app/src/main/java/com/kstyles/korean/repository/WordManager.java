package com.kstyles.korean.repository;

import android.content.Context;
import android.content.res.Resources;

import com.google.firebase.database.DatabaseError;
import com.kstyles.korean.R;
import com.kstyles.korean.view.fragment.item.TranslationItem;

import java.util.TreeMap;

public class WordManager {

    private String TAG = "[WordManager]";
    private TreeMap<String, TranslationItem> wordsMap;
    private TreeMap<String, TranslationItem> cloneWordsMap;

    public interface onDataLoadedListener {
        void onDataLoaded(TreeMap<String, TranslationItem> cloneWordsMap);
    }

    public WordManager(Context context, onDataLoadedListener loadedListener) {
        FirebaseManager firebaseManager = new FirebaseManager();
        wordsMap = new TreeMap<>();
        cloneWordsMap = new TreeMap<>();
        firebaseManager.getAllWordItem(new FirebaseCallback<TreeMap<String, TranslationItem>>() {
            @Override
            public void onSuccess(TreeMap<String, TranslationItem> result) {
                wordsMap = result;
                loadWordsFromResources(context, loadedListener);
            }

            @Override
            public void onFailure(DatabaseError error) {

            }
        });
    }

    private void loadWordsFromResources(Context context, onDataLoadedListener loadedListener) {
        Resources resources = context.getResources();
        String[] wordNames = resources.getStringArray(R.array.beginner);

        for (String wordName : wordNames) {
            cloneWordsMap.put(wordName, wordsMap.get(wordName));
        }
        loadedListener.onDataLoaded(cloneWordsMap);
    }

    public void loadBeginnerWords(Context context) {
        cloneWordsMap.clear();
        Resources resources = context.getResources();
        String[] wordNames = resources.getStringArray(R.array.beginner);

        for (String wordName : wordNames) {
            cloneWordsMap.put(wordName, wordsMap.get(wordName));
        }
    }

    public void loadIntermediateWords(Context context) {
        cloneWordsMap.clear();
        Resources resources = context.getResources();
        String[] wordNames = resources.getStringArray(R.array.intermediate);

        for (String wordName : wordNames) {
            cloneWordsMap.put(wordName, wordsMap.get(wordName));
        }
    }

    public void loadAdvancedWords(Context context) {
        cloneWordsMap.clear();
        Resources resources = context.getResources();
        String[] wordNames = resources.getStringArray(R.array.advanced);

        for (String wordName : wordNames) {
            cloneWordsMap.put(wordName, wordsMap.get(wordName));
        }
    }

    public TreeMap<String, TranslationItem> getAllWords() {
        return cloneWordsMap;
    }
}
