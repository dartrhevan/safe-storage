package com.example.safestorage;

import com.example.safestorage.services.CompressionServiceImpl;
import com.example.safestorage.services.EncodingService;
import com.example.safestorage.services.EncodingServiceImpl;
import com.example.safestorage.services.EncryptionServiceImpl;
import org.junit.jupiter.api.Test;

public class EncodingTests {

    private final EncodingService encodingService = new EncodingServiceImpl( new EncryptionServiceImpl(), new CompressionServiceImpl() );

    @Test
    void simpleEncodingTest() {
        var source = "dwevoidsjvwe9";
        var key = "yhugr";
        encodingService.setKey( key );
        var code = encodingService.encode( source );
        //assert !source.equals( code );
        var result = encodingService.decode( code );
        assert result.equals( source );
    }
}
