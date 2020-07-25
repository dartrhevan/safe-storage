package com.example.safestorage.services;

public interface EncodingService {
    byte[] encode(String source);
    String decode(byte[] code);
}
