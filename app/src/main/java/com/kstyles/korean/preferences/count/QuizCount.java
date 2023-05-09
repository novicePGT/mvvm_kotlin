package com.kstyles.korean.preferences.count;

import android.content.Context;
import android.content.SharedPreferences;

public class QuizCount {

    private int wordCount = 0;
    private int quizCount = 0;
    private SharedPreferences sharedPreferences;

    public QuizCount(Context context) {
        sharedPreferences = context.getSharedPreferences("wordCount", 0);
        this.quizCount = sharedPreferences.getInt("quizCount", quizCount);
        this.wordCount = sharedPreferences.getInt("wordCount", wordCount);
    }

    public void increaseWordCount(String selectLevel) {
        wordCount = wordCount + 1;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("wordCount", wordCount);
        editor.putInt(selectLevel, wordCount);
        editor.apply();

        int levelComplete = sharedPreferences.getInt(selectLevel, 0);
        if (levelComplete == 9) {
            increaseQuizCount();
        }
    }

    private void increaseQuizCount() {
        quizCount++;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("quizCount", quizCount);
        editor.apply();
    }

    public int getQuizCount() {
        return quizCount;
    }

    public int getWordCount() {
        return wordCount;
    }
}
