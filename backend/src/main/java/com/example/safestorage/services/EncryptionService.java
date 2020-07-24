package com.example.safestorage.services;

public interface EncryptionService {
    byte[] encrypt(String source);
    String decrypt(byte[] source);
}
