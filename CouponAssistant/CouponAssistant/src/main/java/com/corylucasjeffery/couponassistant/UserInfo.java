package com.corylucasjeffery.couponassistant;


public class UserInfo {
    private static String userName;
    private static String password;
    private static String firstName;
    private static String lastName;

    public UserInfo(String user, String pw, String first, String last) {
        this.userName = user;
        this.password = pw;
        this.firstName = first;
        this.lastName = last;
    }

    public String getUserName() { return userName; }
    public String getPass() { return password; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
}
