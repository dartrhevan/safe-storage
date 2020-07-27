package com.example.safestorage.models;

import java.io.Serializable;

public class DecodeMessage implements Serializable {
    private DataType dataType;
    private Object data;

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public DecodeMessage(DataType dataType, Object data) {
        this.dataType = dataType;
        this.data = data;
    }

}
