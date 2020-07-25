package com.example.safestorage.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Objects;

@Document
public class Note {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;
        Note note = (Note) o;
        return Objects.equals( Id, note.Id ) &&
                encodedHeader.equals( note.encodedHeader ) &&
                encodedText.equals( note.encodedText );
    }

    @Override
    public int hashCode() {
        return Objects.hash( Id, encodedHeader, encodedText );
    }

    public Note(byte[] encodedHeader, byte[] encodedText, String ownerId, Date addingDate) {
        this.encodedHeader = encodedHeader;
        this.encodedText = encodedText;
        this.ownerId = ownerId;
        this.addingDate = addingDate;
    }

    public Note() {
    }

    public String getId() {
        return Id;
    }

    public byte[] getEncodedHeader() {
        return encodedHeader;
    }

    public void setEncodedHeader(byte[] encodedHeader) {
        this.encodedHeader = encodedHeader;
    }

    public byte[] getEncodedText() {
        return encodedText;
    }

    public void setEncodedText(byte[] encodedText) {
        this.encodedText = encodedText;
    }

    @Id
    private String Id;
    private byte[] encodedHeader;
    private byte[] encodedText;
    private String ownerId;
    private Date addingDate;

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Date getAddingDate() {
        return addingDate;
    }

    public void setAddingDate(Date addingDate) {
        this.addingDate = addingDate;
    }

    public void setId(String id) {
        Id = id;
    }
}
