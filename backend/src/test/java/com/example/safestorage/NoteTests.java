package com.example.safestorage;

import com.example.safestorage.models.Note;
import com.example.safestorage.services.NoteService;
import com.example.safestorage.services.NoteServiceImpl;
import com.example.safestorage.services.UserService;
import com.example.safestorage.services.UserServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

//@SpringBootTest
public class NoteTests {


    private static MongoTemplate mockMongo(Note answer) {
        var mongo = Mockito.mock(MongoTemplate.class);
        Mockito.doReturn(answer).when(mongo).findOne(Mockito.any(), Mockito.any());
        Mockito.doReturn(answer).when(mongo).findById(Mockito.any(), Mockito.any());
        return mongo;
    }

    @Test
    public void getDetailsTest() {
        var note = new Note(new byte[] {4}, new byte[] {4, 5}, "123", null);
        note.setId("123");
        var template = mockMongo(note);
        final NoteService noteService = new NoteServiceImpl(template);
        noteService.saveNote( note );
        var entity = template.findOne( Query.query( Criteria.where( "ownerId" ).is(note.getOwnerId()) ), Note.class );
        System.out.println(entity.getId());
        var entity2 = noteService.getNoteDetails( entity.getId() );
        assert entity2.equals( entity );
    }
}
