package com.example.safestorage.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.zip.DataFormatException;

@Service
public class EncodingServiceImpl implements EncodingService {


    private final EncryptionService encryptionService;
    private final CompressionService compressionService;

    @Autowired
    public EncodingServiceImpl(EncryptionService encryptionService, CompressionService compressionService) {
        this.encryptionService = encryptionService;
        this.compressionService = compressionService;
    }

    @Override
    public byte[] encode(String source, String key) {
        try {
            encryptionService.setKey( key );
            return compressionService.compress( encryptionService.encrypt( source ) );
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String decode(byte[] code, String key) {
        try {
            encryptionService.setKey( key );
            return encryptionService.decrypt( compressionService.decompress( code ) );
        }
        catch (IOException | DataFormatException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String key;

    @Override
    public byte[] encode(String source) {
        if(key == null) throw new IllegalStateException("Key wasn't set!");
        return encode( source, key );
    }

    @Override
    public String decode(byte[] code) {
        if(key == null) throw new IllegalStateException("Key wasn't set!");
        return decode( code, key );
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }
}
