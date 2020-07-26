package com.example.safestorage.models;

import java.io.Serializable;

public class EncodingMessage implements Serializable {
    private EncodingAction action;
    private Object data;
    private String username;

    public EncodingAction getAction() {
        return action;
    }

    public void setAction(EncodingAction action) {
        this.action = action;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public EncodingMessage() {
    }

    public EncodingMessage(EncodingAction action, Object data, String username) {
        this.action = action;
        this.data = data;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
