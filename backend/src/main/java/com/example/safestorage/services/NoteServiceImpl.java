package com.example.safestorage.services;

import com.example.safestorage.models.Note;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    private final MongoTemplate template;
    //private final NoteEncoder encodingService;

    @Autowired
    public NoteServiceImpl(MongoTemplate template) {
        this.template = template;
        //this.encodingService = encodingService;
    }

    @Override
    public void saveNote(Note note) {
        template.save( note );
    }

    @Override//fine!!!
    public void removeNote(String noteId) {
        template.findAllAndRemove( Query.query( Criteria.where( "id" ).is( noteId ) ), Note.class );
    }

    @Override
    public void editNote(Note newNote) {//change somehow??
        //var note = template.findById( newNote.getId(), Note.class );
        template.save( newNote/*encodingService.encode( newNote, note.getOwnerId() )*/ );
    }

    @Override
    public List<Note> listNotes(String userId) {
        var query = Query.query( Criteria.where( "ownerId" ).is(userId));
        //encodingService.setKey( userId );
        query.fields().exclude( "encodedText" ).exclude( "addingDate" );
        var result = template.find( query, Note.class );
        System.out.println(result);
        return result;
    }

    @Override
    public Note getNoteDetails(String noteId) {
        var note = template.findById(noteId, Note.class );//findOne( Query.query( Criteria.where( "id" ).is( new ObjectId(noteId) ) ), Note.class );
        System.out.println(note);//Here is null....
        return note;
    }
}
