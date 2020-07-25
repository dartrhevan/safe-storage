package com.example.safestorage.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    public byte[] encode(String source) {
        try {
            return compressionService.compress( encryptionService.encrypt( source ) );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String decode(byte[] code) {
        try {
            return encryptionService.decrypt( compressionService.decompress( code ) );
        }
        catch (IOException | DataFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}
