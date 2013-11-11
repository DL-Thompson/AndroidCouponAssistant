package com.corylucasjeffery.couponassistant;


public class UserInfo {
    private final String userName;
    private final String password;

    public UserInfo(String user, String pw) {
        this.userName = user;
        this.password = pw;
    }

    public String getUserName() { return userName; }
    public String getPass() { return password; }
}
