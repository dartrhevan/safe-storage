package com.example.safestorage.models;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document
public class User {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return (id == null && user.id == null || id.equals( user.id )) &&
                username.equals( user.username ) &&
                passwordHash.equals( user.passwordHash );
    }

    @Override
    public int hashCode() {
        return Objects.hash( id, username, passwordHash );
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public User() {
    }

    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    @Id
    private String id;
    private String username;
    private String passwordHash;

    @DBRef
    private List<Note> notes;

    public List<Note> getNotes() {
        if(notes == null)
            notes = new ArrayList<>();
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
