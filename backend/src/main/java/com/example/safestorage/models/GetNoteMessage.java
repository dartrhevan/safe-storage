package com.example.safestorage.models;

import java.io.Serializable;

public class GetNoteMessage implements Serializable {
    private DataType dataType;

    public GetNoteMessage() {
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public String getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = this.data;
    }

    public GetNoteMessage(DataType dataType, String data) {
        this.dataType = dataType;
        this.data = data;
    }

    private String data;
}
