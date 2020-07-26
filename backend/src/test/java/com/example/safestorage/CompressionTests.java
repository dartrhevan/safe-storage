package com.example.safestorage;

import com.example.safestorage.services.CompressionService;
import com.example.safestorage.services.CompressionServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.DataFormatException;

public class CompressionTests {

    private final CompressionService compressionService = new CompressionServiceImpl();

    @Test
    void compressionTest() throws IOException, DataFormatException {
        var source = "djwnwejfewjklfewoifwekvnkjxinxiolcano eju3in839j2wiuen8fw id8948fg bjdvzhkewIUH2IUBh98";
        var code = compressionService.compress( source.getBytes( StandardCharsets.UTF_8 ) );
        assert source.getBytes().length >= code.length;
        var decode = new String( compressionService.decompress( code ) );
        assert decode.equals( source );
    }
}
