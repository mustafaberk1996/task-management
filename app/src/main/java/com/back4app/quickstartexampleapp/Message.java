package com.back4app.quickstartexampleapp;

public class Message {
    private  String message,senderObjectId,receiverObjectId;


    public Message(String message, String senderObjectId, String receiverObjectId) {
        this.message = message;
        this.senderObjectId = senderObjectId;
        this.receiverObjectId = receiverObjectId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderObjectId() {
        return senderObjectId;
    }

    public void setSenderObjectId(String senderObjectId) {
        this.senderObjectId = senderObjectId;
    }

    public String getReceiverObjectId() {
        return receiverObjectId;
    }

    public void setReceiverObjectId(String receiverObjectId) {
        this.receiverObjectId = receiverObjectId;
    }
}
