package com.kstyles.korean.position;

public class SeekbarPosition {

    private int position;

    public SeekbarPosition(int seekbarPosition) {
        this.position = seekbarPosition;
    }

    public int increasePosition(int currentPosition) {
        position = currentPosition + 1;
        if (position >= 9) {
            position = 9;
        }
        return position;
    }

    public int decreasePosition(int currentPosition) {
        position = currentPosition - 1;
        if (position <= 0) {
            position = 0;
        }
        return position;
    }

    public int getPosition() {
        if (position > 9) {
            position = 0;
        }
        if (position < 0) {
            position = 9;
        }
        return position;
    }
}
