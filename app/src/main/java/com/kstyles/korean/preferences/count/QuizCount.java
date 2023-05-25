package com.kstyles.korean.preferences.count;

import android.content.Context;
import android.content.SharedPreferences;

public class QuizCount {

    private int wordCount = 0;
    private int quizCount = 0;
    private int levelWord = 0;
    private SharedPreferences sharedPreferences;

    public QuizCount(Context context, String level) {
        sharedPreferences = context.getSharedPreferences("wordCount", 0);
        this.quizCount = sharedPreferences.getInt("quizCount", quizCount);
        this.wordCount = sharedPreferences.getInt("wordCount", wordCount);
        this.levelWord = sharedPreferences.getInt(level+"Word", levelWord);
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

    public void increaseWordCount(String selectLevel) {
        String selectLevelWord = selectLevel + "Word";
        wordCount = wordCount + 1;
        levelWord++;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("wordCount", wordCount);
        editor.putInt(selectLevelWord, levelWord);
        editor.apply();

        int levelComplete = sharedPreferences.getInt(selectLevelWord, 0);
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
        return levelWord;
    }

    public int setLevelPosition() {
        levelWord = 0;
        return 0;
    }
    public int getQuizCount() {
        return quizCount;
    }

    public int getWordCount() {
        return wordCount;
    }
}
