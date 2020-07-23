package com.example.safestorage;

import com.example.safestorage.models.Note;
import com.example.safestorage.models.User;
import com.example.safestorage.services.NoteService;
import com.example.safestorage.services.UserService;
import com.example.safestorage.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SafeStorageApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

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
        //userService.removeUser( user.getUsername() );
    }

}
