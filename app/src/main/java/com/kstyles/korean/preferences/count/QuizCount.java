package com.kstyles.korean.preferences.count;

import android.content.Context;
import android.content.SharedPreferences;

public class QuizCount {

    private int wordCount;
    private int quizCount;
    private SharedPreferences sharedPreferences;

    public QuizCount(Context context) {
        sharedPreferences = context.getSharedPreferences("wordCount", 0);
        this.quizCount = sharedPreferences.getInt("quizCount", quizCount);
        this.wordCount = sharedPreferences.getInt("wordCount", wordCount);
    }

    public void increaseWordCount() {
        wordCount = wordCount + 1;
        if (wordCount % 10 == 0) {
            increaseQuizCount();
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("wordCount", wordCount);
        editor.apply();
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

