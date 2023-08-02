package com.kstyles.korean.view.fragment.item;

import android.net.Uri;

public class UserAccount {

    private String email;
    private String password;
    private Uri profile;
    private String successMessage;
    private String failedMessage;

    public UserAccount(String email, String password, Uri profile, String successMessage, String failedMessage) {
        this.email = email;
        this.password = password;
        this.profile = profile;
        this.successMessage = successMessage;
        this.failedMessage = failedMessage;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Uri getProfile() {
        return profile;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public String getFailedMessage() {
        return failedMessage;
    }
}
