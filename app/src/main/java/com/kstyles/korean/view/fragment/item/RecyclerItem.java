package com.kstyles.korean.view.fragment.item;

public class RecyclerItem {

    private String level;

    private String name;

    public RecyclerItem() {

    }

    public RecyclerItem(String level, String name) {
        this.level = level;
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }
}
