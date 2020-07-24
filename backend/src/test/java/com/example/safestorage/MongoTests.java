package com.example.safestorage;

import com.example.safestorage.models.Note;
import com.example.safestorage.models.User;
import com.example.safestorage.services.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@SpringBootTest
class MongoTests {

    private final NoteService noteService;

    private final UserService userService;

    private final MongoTemplate template;

    @Autowired
    MongoTests(NoteService noteService, UserService userService, MongoTemplate template) {
        this.noteService = noteService;
        this.userService = userService;
        this.template = template;
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

        //TODO: move to after test
        userService.removeUser( user.getUsername() );
    }

    @Test
    void mongoDBRefTest() {
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
        //TODO: move to after test
        userService.removeUser( user.getUsername() );
        noteService.removeNote( note, user );
    }

    @Test
    void mongoExcludeTest() {
        var name = "dsad";
        var pass = "ewf";
        var head = "grea";
        var text = "ew";
        var user = new User(name, pass);
        userService.saveUser( user );
        var note = new Note(head, text);
        noteService.saveNote( note, user );
        var query = new Query();
        var entity = template.findOne( query, User.class );// userService.findUserByName( name );
        assert entity.getNotes().get( 0 ).getText().equals( text );
        query.fields().exclude( "notes.text" );
        var entity2 = template.findOne( query, User.class );// userService.findUserByName( name );
        assert entity2.getNotes().get( 0 ).getText() == null;
        //TODO: move to after test
        userService.removeUser( user.getUsername() );
        noteService.removeNote( note, user );
    }


}