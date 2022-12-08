package com.example.mad_project;

import android.net.Uri;

public class User_Detail_Model {

    private String UserName, Email, PhoneNumber, Image, UserID;

    public User_Detail_Model() {

    }

    public User_Detail_Model(String userName, String email, String phoneNumber, String image, String userID) {
        UserName = userName;
        Email = email;
        PhoneNumber = phoneNumber;
        Image = image;
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}
