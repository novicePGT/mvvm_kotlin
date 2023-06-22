package com.kstyles.korean.repository;

import android.content.Context;
import android.content.res.Resources;

import com.kstyles.korean.R;
import com.kstyles.korean.view.fragment.item.WordItem;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordManager {

    private String TAG = "[WordManager]";
    private HashMap<String, WordItem> wordsMap;

    public WordManager(Context context) {
        wordsMap = new HashMap<>();
        loadWordsFromResources(context);
    }

    private void loadWordsFromResources(Context context) {
        Resources resources = context.getResources();
        String packageName = context.getPackageName();

        String[] wordNames = resources.getStringArray(R.array.beginner);

        for (String wordName : wordNames) {
            int resourceIdString = resources.getIdentifier(wordName, "string", packageName);
            if (resourceIdString != 0) {
                String description = resources.getString(resourceIdString);
                WordItem wordItem = new WordItem(wordName, description);
                wordsMap.put(wordName, wordItem);
            }
        }
    }

    public void loadBeginnerWords(Context context) {
        wordsMap.clear();
        Resources resources = context.getResources();
        String packageName = context.getPackageName();

        String[] wordNames = resources.getStringArray(R.array.beginner);

        for (String wordName : wordNames) {
            int resourceIdString = resources.getIdentifier(wordName, "string", packageName);
            if (resourceIdString != 0) {
                String description = resources.getString(resourceIdString);
                WordItem wordItem = new WordItem(wordName, description);
                wordsMap.put(wordName, wordItem);
            }
        }
    }

    public void loadIntermediateWords(Context context) {
        wordsMap.clear();
        Resources resources = context.getResources();
        String packageName = context.getPackageName();

        String[] wordNames = resources.getStringArray(R.array.intermediate);

        for (String wordName : wordNames) {
            int resourceIdString = resources.getIdentifier(wordName, "string", packageName);
            if (resourceIdString != 0) {
                String description = resources.getString(resourceIdString);
                WordItem wordItem = new WordItem(wordName, description);
                wordsMap.put(wordName, wordItem);
            }
        }
    }

    public void loadAdvancedWords(Context context) {
        wordsMap.clear();
        Resources resources = context.getResources();
        String packageName = context.getPackageName();

        String[] wordNames = resources.getStringArray(R.array.advanced);

        for (String wordName : wordNames) {
            int resourceIdString = resources.getIdentifier(wordName, "string", packageName);
            if (resourceIdString != 0) {
                String description = resources.getString(resourceIdString);
                WordItem wordItem = new WordItem(wordName, description);
                wordsMap.put(wordName, wordItem);
            }
        }
    }

    public WordItem getWord(String name) {
        return wordsMap.get(name);
    }

    public HashMap<String, WordItem> getAllWords() {
        return wordsMap;
    }
}
