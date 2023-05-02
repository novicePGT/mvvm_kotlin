package com.kstyles.korean.item;

public class PracticeItem {

    private String imageUrl;
    private String answer;

    public PracticeItem(String imageUrl, String answer) {
        this.imageUrl = imageUrl;
        this.answer = answer;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAnswer() {
        return answer;
    }
}
