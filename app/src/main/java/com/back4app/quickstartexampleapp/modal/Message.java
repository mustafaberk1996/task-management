package com.back4app.quickstartexampleapp.modal;

public class Message {
    public static final int TYPE_SENDER = 0;
    public static final int TYPE_RECEIVER = 1;
    private String message, senderUserName, senderObjectId, time;

    public Message(String message, String senderUserName, String time, String senderObjectId) {
        this.message = message;
        this.senderObjectId = senderObjectId;
        this.senderUserName = senderUserName;
        this.time = time;
    }

    public String getSenderObjectId() {
        return senderObjectId;
    }

    public void setSenderObjectId(String senderObjectId) {
        this.senderObjectId = senderObjectId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderUserName() {
        return senderUserName;
    }

    public void setSenderUserName(String senderUserName) {
        this.senderUserName = senderUserName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
