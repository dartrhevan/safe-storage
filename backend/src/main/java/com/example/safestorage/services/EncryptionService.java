package com.example.safestorage.services;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public interface EncryptionService {
    void setKey(String myKey) throws NoSuchAlgorithmException, UnsupportedEncodingException;

    byte[] encrypt(String source);
    String decrypt(byte[] source);
}
