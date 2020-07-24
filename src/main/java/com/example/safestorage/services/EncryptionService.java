package com.example.safestorage.services;

public interface EncryptionService {
    String encrypt(String source);
    String decrypt(String source);
}
