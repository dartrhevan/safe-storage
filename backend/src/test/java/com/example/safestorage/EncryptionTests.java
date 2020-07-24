package com.example.safestorage;

import com.example.safestorage.services.EncryptionServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
public class EncryptionTests {

    @Test
    void encryptionSimpleTest() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        var secret = "sdw";
        var encryptionService = new EncryptionServiceImpl( secret );
        var source = "TRYTGUHJKNM";
        var code = encryptionService.encrypt( source );
        System.out.println(code);
        assert !(new String(code).equals( source ));
        var source2 = encryptionService.decrypt( code );
        assert source.equals( source2 );
    }


    @Test
    void encryptionPerformanceTest() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        encryptIteration();
        var begin =  System.currentTimeMillis();
        for(var i = 0; i < 100; i++)
            encryptIteration();
        var end =  System.currentTimeMillis();
        var time = end - begin;
        assert time < 25;
        System.out.println(time);
    }

    private void encryptIteration() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        var secret = "sdw";
        var encryptionService = new EncryptionServiceImpl( secret );
        var source = "TRYTGUHJKNM";
        var code = encryptionService.encrypt( source );
        var source2 = encryptionService.decrypt( code );
    }

}
