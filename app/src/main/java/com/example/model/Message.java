package com.example.model;


public class Message {
    private int id;
    private String name;
    private String lastMessage;
    private int unreadCount;

    public Message(int id, String contactName, String lastMessage, int unreadCount) {
        this.id = id;
        this.name = contactName;
        this.lastMessage = lastMessage;
        this.unreadCount = unreadCount;
    }

    public int getId() {
        return id;
    }



    public String getName() {
        return name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public int getUnreadCount() {
        return unreadCount;
    }





}

