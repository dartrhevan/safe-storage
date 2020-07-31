package com.example.safestorage.models;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Document
public class Note implements Serializable {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;
        Note note = (Note) o;
        return Objects.equals( id, note.id ) &&
                Arrays.equals( encodedHeader, note.encodedHeader ) &&
                Arrays.equals( encodedText, note.encodedText );
    }

    @Override
    public int hashCode() {
        return Objects.hash( id, encodedHeader, encodedText );
    }

    public Note(byte[] encodedHeader, byte[] encodedText, String ownerId, DateTime addingDate) {
        this.encodedHeader = encodedHeader;
        this.encodedText = encodedText;
        this.ownerId = ownerId;
        this.addingDate = addingDate;
    }

    public Note() {
    }

    public String getId() {
        return id;
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

    @Override
    public String toString() {
        return "Note{" +
                "id='" + id + '\'' +
                ", encodedHeader=" + Arrays.toString( encodedHeader ) +
                ", encodedText=" + Arrays.toString( encodedText ) +
                ", ownerId='" + ownerId + '\'' +
                ", addingDate=" + addingDate +
                '}';
    }

    @Id
    //@MongoId(value = FieldType.OBJECT_ID)
    private String id;
    private byte[] encodedHeader;
    private byte[] encodedText;
    private String ownerId;
    private DateTime addingDate;

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public DateTime getAddingDate() {
        return addingDate;
    }

    public void setAddingDate(DateTime addingDate) {
        this.addingDate = addingDate;
    }

    public void setId(String id) {
        this.id = id;
    }
}
