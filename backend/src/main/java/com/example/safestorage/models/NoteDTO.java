package com.example.safestorage.models;

import java.util.Date;
import java.util.Objects;

public class NoteDTO {
    private String head;
    private String text;
    private String id;
    private Date date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NoteDTO)) return false;
        NoteDTO noteDTO = (NoteDTO) o;
        return head.equals( noteDTO.head ) &&
                Objects.equals( text, noteDTO.text ) &&
                Objects.equals( id, noteDTO.id ) &&
                Objects.equals( date, noteDTO.date );
    }

    @Override
    public int hashCode() {
        return Objects.hash( head, text, id, date );
    }

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
