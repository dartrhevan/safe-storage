package com.example.safestorage;

import com.example.safestorage.models.Note;
import com.example.safestorage.models.User;
import com.example.safestorage.services.*;
import org.junit.After;
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

    private User entity;

    @Autowired
    MongoTests(NoteService noteService, UserService userService, MongoTemplate template) {
        this.noteService = noteService;
        this.userService = userService;
        this.template = template;
    }

    @Test
    void mongoSimpleTest() throws Exception {
        assert userService.getClass().equals( UserServiceImpl.class );
        var name = "dsad";
        var pass = "ewf";
        var user = new User(name, pass);
        userService.saveUser( user );
        entity = userService.findUserByName( name );
        assert name.equals( entity.getUsername() );
        assert pass.equals( entity.getPasswordHash() );
    }

    @Test
    void mongoExcludeTest() throws Exception {
        var name = "dsad";
        var pass = "ewf";
        var user = new User(name, pass);
        userService.saveUser( user );
        var query = new Query();
        entity = template.findOne( query, User.class );// userService.findUserByName( name );
        query.fields().exclude( "username" );
        assert entity.getUsername().equals( name );
        var entity2 = template.findOne( query, User.class );// userService.findUserByName( name );
        assert entity2.getUsername() == null;
    }

    @Test
    void getUserTest() {
        var entity = template.findById( "5f1ec5cdefd8ff5e799e6c94", User.class );
        System.out.println(entity);
        assert entity != null;
    }

    @Test
    void getNoteTest() {
        var entity = template.findById( "5f1ec8acf87dff6bc91bad21", Note.class );
        System.out.println(entity);
        assert entity != null;
    }

    @After
    public void cleaning() {
        if(entity != null)
            userService.removeUser( entity.getId() );
    }
}