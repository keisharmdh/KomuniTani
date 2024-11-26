package com.example.model;

public class MsgModel {
    private String cnt;

    public String getCnt() {
        return cnt;
    }

    public String setCnt(String cnt) {
        this.cnt = cnt;
        return cnt;
    }

    public MsgModel(String cnt) {
        this.cnt = cnt;
    }
}