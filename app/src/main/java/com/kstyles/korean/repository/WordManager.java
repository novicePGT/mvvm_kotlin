package com.kstyles.korean.repository;

import com.google.firebase.database.DatabaseError;
import com.kstyles.korean.view.fragment.item.TranslationItem;

import java.util.TreeMap;

public class WordManager {

    private String TAG = "[WordManager]";
    private TreeMap<String, TranslationItem> wordsMap;
    private final TreeMap<String, TranslationItem> cloneWordsMap;
    private final onDataLoadedListener onDataLoaded;
    private final FirebaseManager firebaseManager;

    public interface onDataLoadedListener {
        void onDataLoaded(TreeMap<String, TranslationItem> cloneWordsMap);
    }

    public WordManager(onDataLoadedListener loadedListener) {
        firebaseManager = new FirebaseManager();
        wordsMap = new TreeMap<>();
        cloneWordsMap = new TreeMap<>();
        onDataLoaded = loadedListener;
        loadWords("Beginner");
    }

    public void loadWords(String level) {
        cloneWordsMap.clear();
        getWordByLevel(level, new FirebaseCallback() {
            @Override
            public void onSuccess(Object result) {
                TreeMap<String, TranslationItem> beginner = (TreeMap<String, TranslationItem>) result;
                cloneWordsMap.putAll(beginner);
                onDataLoaded.onDataLoaded(cloneWordsMap);
            }

            @Override
            public void onFailure(DatabaseError error) {
                // 실패 처리 로직 추가
            }
        });
    }

    public TreeMap<String, TranslationItem> getAllWords() {
        return cloneWordsMap;
    }

    private void getWordByLevel(String level, final FirebaseCallback callback) {
        wordsMap.clear();
        firebaseManager.getWordByLevel(level, new FirebaseCallback() {
            @Override
            public void onSuccess(Object result) {
                wordsMap = (TreeMap<String, TranslationItem>) result;
                callback.onSuccess(wordsMap);
            }

            @Override
            public void onFailure(DatabaseError error) {
                callback.onFailure(error);
            }
        });
    }
}
