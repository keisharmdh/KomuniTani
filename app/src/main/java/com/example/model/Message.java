package com.example.model;


public class Message {
    private String contactName;
    private String lastMessage;
    private int unreadCount;

    public Message(String contactName, String lastMessage, int unreadCount) {
        this.contactName = contactName;
        this.lastMessage = lastMessage;
        this.unreadCount = unreadCount;
    }

    public String getContactName() {
        return contactName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public int getUnreadCount() {
        return unreadCount;
    }




}

