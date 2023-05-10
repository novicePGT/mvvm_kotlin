package com.kstyles.korean.item;

public class UserAccount {

    private int seq;
    private String userEmail;
    private String userPassword;
    private String userName;
    private String date;

    private String idToken;

    public UserAccount(String userEmail, String userPassword, String userName, String idToken) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userName = userName;
        this.idToken = idToken;
    }

    public UserAccount() {}

    /**
     * GETTER & SETTER
     * @return
     */
    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}
