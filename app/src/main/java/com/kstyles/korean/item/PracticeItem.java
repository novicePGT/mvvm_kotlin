package com.kstyles.korean.item;

public class PracticeItem {

    private String practiceImage;

    private String answer;
    public PracticeItem(String practiceImage, String answer) {
        this.practiceImage = practiceImage;
        this.answer = answer;
    }

    public String getPracticeImage() {
        return practiceImage;
    }

    public void setPracticeImage(String practiceImage) {
        this.practiceImage = practiceImage;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}