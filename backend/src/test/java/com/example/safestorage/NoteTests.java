package com.example.safestorage;

import com.example.safestorage.models.Note;
import com.example.safestorage.services.NoteService;
import com.example.safestorage.services.NoteServiceImpl;
import com.example.safestorage.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@SpringBootTest
public class NoteTests {

    private final NoteService noteService;

    private final UserService userService;

    private final MongoTemplate template;

    @Autowired
    NoteTests(NoteService noteService, UserService userService, MongoTemplate template) {
        this.noteService = noteService;
        this.userService = userService;
        this.template = template;
    }

    @Test
    void getDetailsTest() {
        var note = new Note(new byte[] {4}, new byte[] {4, 5}, "123", null);
        noteService.saveNote( note );
        var entity = template.findOne( Query.query( Criteria.where( "ownerId" ).is(note.getOwnerId()) ), Note.class );
        var entity2 = noteService.getNoteDetails( entity.getId() );
        assert entity.equals( entity2 );
        noteService.removeNote( entity.getId() );
    }
}
