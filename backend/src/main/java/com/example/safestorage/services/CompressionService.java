package com.example.safestorage.services;

import java.io.IOException;
import java.util.zip.DataFormatException;

public interface CompressionService {
    byte[] compress(byte[] data) throws IOException;
    byte[] decompress(byte[] data) throws IOException, DataFormatException;
}
