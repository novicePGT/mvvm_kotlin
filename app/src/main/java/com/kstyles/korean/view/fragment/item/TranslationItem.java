package com.kstyles.korean.view.fragment.item;

import android.content.Context;
import android.content.SharedPreferences;

public class TranslationItem {

    String en = "";
    String de = "";
    String fr = "";
    String ja = "";
    String th = "";
    String vi = "";

    public TranslationItem() {

    }

    public TranslationItem(String translation, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("manager", Context.MODE_PRIVATE);
        int manager = sharedPreferences.getInt("manager", 0);

        if (manager == 0) {
            this.en = translation;
        }
        if (manager == 1) {
            this.de = translation;
        }
        if (manager == 2) {
            this.fr = translation;
        }
        if (manager == 3) {
            this.ja = translation;
        }
        if (manager == 4) {
            this.th = translation;
        }
        if (manager == 5) {
            this.vi = translation;
        }
    }

    public String getEn() {
        return en;
    }

    public String getDe() {
        return de;
    }

    public String getFr() {
        return fr;
    }

    public String getJa() {
        return ja;
    }

    public String getTh() {
        return th;
    }

    public String getVi() {
        return vi;
    }
}
