package com.example.safestorage.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class Note {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;
        Note note = (Note) o;
        return Objects.equals( Id, note.Id ) &&
                header.equals( note.header ) &&
                text.equals( note.text );
    }

    @Override
    public int hashCode() {
        return Objects.hash( Id, header, text );
    }

    public Note(String header, String text) {
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
