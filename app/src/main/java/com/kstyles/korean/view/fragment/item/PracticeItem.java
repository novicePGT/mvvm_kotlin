package com.kstyles.korean.view.fragment.item;

public class PracticeItem {

    private String answer;
    private String imageUrl;

    public PracticeItem() {

    }
    public PracticeItem(String answer, String imageUrl) {
        this.answer = answer;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAnswer() {
        return answer;
    }
}
