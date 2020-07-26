package com.example.safestorage.services;

public interface EncodingService {
    byte[] encode(String source, String key);
    String decode(byte[] code, String key);

    byte[] encode(String source);
    String decode(byte[] code);

    void setKey(String key);
}
