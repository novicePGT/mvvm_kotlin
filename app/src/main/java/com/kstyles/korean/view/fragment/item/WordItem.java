package com.kstyles.korean.view.fragment.item;

import android.content.Context;
import android.content.res.Resources;

public class WordItem {

    private String wordName;
    private String description;

    public WordItem(String wordName, String description) {
        this.wordName = wordName;
        this.description = description;
    }

    public String getWordName() {
        return wordName;
    }

    public String getDescription(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(wordName, "string", context.getPackageName());
        if (resourceId != 0) {
            return resources.getString(resourceId);
        }
        return "";
    }
}
