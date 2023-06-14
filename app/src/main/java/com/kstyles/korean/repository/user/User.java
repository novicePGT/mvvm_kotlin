package com.kstyles.korean.repository.user;

public class User {

    private String userUid;

    public User(String uid) {
        this.userUid = uid;
    }

    public String getUid() {
        return userUid;
    }
}
