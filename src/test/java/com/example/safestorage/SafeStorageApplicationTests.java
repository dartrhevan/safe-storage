package com.example.safestorage;

import com.example.safestorage.models.Note;
import com.example.safestorage.models.User;
import com.example.safestorage.services.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

// TODO: refactor - split to classes
@SpringBootTest
class SafeStorageApplicationTests {

    @Test
    void contextLoads() {
    }
    //TODO: refactor change DI type
    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    @Autowired
    private MongoTemplate template;

    @Autowired
    private CompressionService compressionService;

    @Test
    void noteTest() {
        //template.getCollection( "user" ).aggregate( new ArrayList<>() {} )
    }

    @Test
    void mongoSimpleTest() {
        assert userService.getClass().equals( UserServiceImpl.class );
        var name = "dsad";
        var pass = "ewf";
        var user = new User(name, pass);
        userService.saveUser( user );
        var entity = userService.findUserByName( name );
        assert name.equals( entity.getUsername() );
        assert pass.equals( entity.getPasswordHash() );

        userService.removeUser( user.getUsername() );
    }

    @Test
    void mongoDBRefTest() {
        assert userService.getClass().equals( UserServiceImpl.class );
        var name = "dsad";
        var pass = "ewf";
        var head = "grea";
        var text = "ew";
        var user = new User(name, pass);
        userService.saveUser( user );
        var note = new Note(head, text);
        noteService.saveNote( note, user );
        var entity = userService.findUserByName( name );
        assert name.equals( entity.getUsername() );
        assert entity.getNotes().get( 0 ).getText().equals( note.getText() );
        userService.removeUser( user.getUsername() );
        noteService.removeNote( note, user );
    }

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

    @Test
    void compressionTest() throws IOException, DataFormatException {
        var source = "djwnwejfewjklfewoifwekvnkjxinxiolcano eju3in839j2wiuen8fw id8948fg bjdvzhkewIUH2IUBh98";
        var code = compressionService.compress( source.getBytes( StandardCharsets.UTF_8 ) );
        assert source.getBytes().length >= code.length;
        var decode = new String( compressionService.decompress( code ) );
        assert decode.equals( source );
    }

}
