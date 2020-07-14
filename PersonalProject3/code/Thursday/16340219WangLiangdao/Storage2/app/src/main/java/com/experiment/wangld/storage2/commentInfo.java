package com.experiment.wangld.storage2;

import android.graphics.drawable.Drawable;

public class commentInfo {
    Drawable head;
    String username;
    String content;
    String time;
    public commentInfo(){}
    public commentInfo(Drawable head, String username, String content, String time){
        this.head = head;
        this.username = username;
        this.content = content;
        this.time = time;
    }

    public void setHead(Drawable head) {
        this.head = head;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Drawable getHead() {
        return head;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }
}
