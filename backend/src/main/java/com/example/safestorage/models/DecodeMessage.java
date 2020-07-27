package com.example.safestorage.models;

public class DecodeMessage {
    private NoteDTO note;
    private String username;

    public NoteDTO getNote() {
        return note;
    }

    public void setNote(NoteDTO note) {
        this.note = note;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public DecodeMessage(NoteDTO note, String username) {
        this.note = note;
        this.username = username;
    }
}
