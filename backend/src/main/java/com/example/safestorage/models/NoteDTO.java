package com.example.safestorage.models;

import java.util.Date;

public class NoteDTO {
    private String head;
    private String text;
    private String id;
    private Date date;


    public NoteDTO(String head, String text, String id, Date date) {
        this.head = head;
        this.text = text;
        this.id = id;
        this.date = date;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
