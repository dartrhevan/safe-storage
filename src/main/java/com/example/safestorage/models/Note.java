package com.example.safestorage.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Note {

    public Note( String header, String text) {
        this.header = header;
        this.text = text;
    }

    public Note() {
    }

    public String getId() {
        return Id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Id
    private String Id;
    private String header;
    private String text;
}
