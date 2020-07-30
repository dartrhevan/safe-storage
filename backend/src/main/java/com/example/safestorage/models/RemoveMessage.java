package com.example.safestorage.models;

import java.io.Serializable;

public class RemoveMessage implements Serializable {
    private String username;
    private String id;

    public RemoveMessage() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RemoveMessage(String username, String id) {
        this.username = username;
        this.id = id;
    }
}
