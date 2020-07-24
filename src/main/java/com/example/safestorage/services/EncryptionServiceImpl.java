package com.example.safestorage.services;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class EncryptionServiceImpl implements EncryptionService {

    private final byte[] key;
    private final SecretKeySpec secretKey;

    public EncryptionServiceImpl(String myKey) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //key = myKey.getBytes("UTF-8");
        var sha = MessageDigest.getInstance( "SHA-1" );
        //key = sha.digest(myKey.getBytes("UTF-8"));
        key = Arrays.copyOf( myKey.getBytes( "UTF-8" ), 16 );
        secretKey = new SecretKeySpec( key, "AES" );
    }

    @Override
    public String encrypt(String source) {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(source.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    @Override
    public String decrypt(String source) {
        try
        {
            //setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(source)));
        }
        catch (Exception e)
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}
