package com.example.safestorage.services;


import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class CompressionServiceImpl implements CompressionService {

    @Override
    public byte[] compress(byte[] data) throws IOException {
        var deflater = new Deflater();
        deflater.setInput(data);
        var outputStream = new ByteArrayOutputStream(data.length);
        deflater.finish();
        var buffer = new byte[1024];
        while (!deflater.finished()) {
            var count = deflater.deflate(buffer); // returns the generated code... index
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        return outputStream.toByteArray();
    }

    @Override
    public byte[] decompress(byte[] data) throws IOException, DataFormatException {
        var inflater = new Inflater();
        inflater.setInput(data);
        var outputStream = new ByteArrayOutputStream(data.length);
        var buffer = new byte[1024];
        while (!inflater.finished()) {
            var count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        return outputStream.toByteArray();
    }
}
