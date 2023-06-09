package com.kstyles.korean.preferences.count;

import android.content.Context;
import android.content.SharedPreferences;

public class QuizCount {

    private int wordCount = 0;
    private int quizCount = 0;
    private int levelWord = 0;
    private int levelTotalCount = 0;
    private String selectLevel;
    private SharedPreferences sharedPreferences;

    public QuizCount(Context context, String selectLevel) {
        sharedPreferences = context.getSharedPreferences("wordCount", 0);
        this.selectLevel = selectLevel;
        this.quizCount = sharedPreferences.getInt("quizCount", quizCount);
        this.wordCount = sharedPreferences.getInt("wordCount", wordCount);
        this.levelWord = sharedPreferences.getInt(selectLevel, levelWord);
        this.levelTotalCount = sharedPreferences.getInt(selectLevel + "WordTotal", levelTotalCount);
        if (this.quizCount == 0) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("quizCount", 0);
            editor.apply();
        }
        if (this.wordCount == 0) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("wordCount", 0);
            editor.apply();
        }
    }

    public void increaseWordCount() {
        wordCount = wordCount + 1;
        levelWord++;
        levelTotalCount++;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("wordCount", wordCount);
        editor.putInt(selectLevel, levelWord);
        editor.putInt(selectLevel + "WordTotal", levelTotalCount);
        editor.apply();

        int levelComplete = sharedPreferences.getInt(selectLevel + "WordTotal", levelTotalCount);
        if (levelComplete == 10) {
            increaseQuizCount();
        }
    }

    private void increaseQuizCount() {
        quizCount++;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("quizCount", quizCount);
        editor.apply();
    }

    public int getLevelPosition() {
        levelWord = levelWord % 11;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(selectLevel, levelWord);
        editor.apply();
        return levelWord;
    }

    public int setLevelPosition() {
        levelWord = 0;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(selectLevel, levelWord);
        editor.apply();
        return levelWord;
    }
    public int getQuizCount() {
        return quizCount;
    }

    public int getWordCount() {
        return wordCount;
    }
}
