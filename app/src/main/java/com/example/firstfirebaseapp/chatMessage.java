package com.example.firstfirebaseapp;

public class chatMessage {

    public String userPhoto;
    public String userName;
    public String userID;
    public String message;

    public chatMessage(String userPhoto, String userName, String userID, String message) {
        this.userPhoto = userPhoto;
        this.userName = userName;
        this.userID = userID;
        this.message = message;
    }
}
