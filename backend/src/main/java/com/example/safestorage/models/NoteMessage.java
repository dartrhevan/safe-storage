package com.example.safestorage.models;

import java.io.Serializable;

public class NoteMessage implements Serializable {
    private NoteAction action;

    public NoteMessage() {
    }

    public NoteAction getAction() {
        return action;
    }

    public void setAction(NoteAction action) {
        this.action = action;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public NoteMessage(NoteAction action, Object data) {
        this.action = action;
        this.data = data;
    }

    private Object data;
}
