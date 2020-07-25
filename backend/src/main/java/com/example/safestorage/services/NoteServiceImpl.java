package com.example.safestorage.services;

import com.example.safestorage.models.Note;
import com.example.safestorage.models.NoteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService {

    private final MongoTemplate template;
    private final EncodingService encodingService;

    @Autowired
    public NoteServiceImpl(MongoTemplate template, EncodingService encodingService) {
        this.template = template;
        this.encodingService = encodingService;
    }

    @Override
    public void saveNote(NoteDTO note, String ownerId) {
        template.save( new Note(encodingService.encode( note.getHead() ),
                                    encodingService.encode( note.getText() ), ownerId, note.getDate()) );
    }

    @Override
    public void removeNote(String noteId) {
        //user.getNotes().remove( note );
        template.findAllAndRemove( Query.query( Criteria.where( "id" ).is( noteId ) ), Note.class );///remove( note );
        //template.remove( user );
    }

    @Override
    public void editNote(NoteDTO newNote) {
        var originalNote = template.findById( newNote.getId(), Note.class );
        var note = new Note(encodingService.encode( newNote.getHead() ), encodingService.encode( newNote.getText()),
                            originalNote.getOwnerId(), newNote.getDate());
        note.setId( originalNote.getId() );
        template.save( note );
    }

    @Override
    public List<NoteDTO> listNotes(String userId) {
        var query = Query.query( Criteria.where( "ownerId" ).is(userId));

        query.fields().exclude( "text" ).exclude( "addingDate" );
        return template.find( query, Note.class ).stream().map( n ->
               new NoteDTO(encodingService.decode( n.getEncodedHeader() ),
                  null, n.getId(), null) ).collect( Collectors.toList());
    }

    @Override
    public NoteDTO getNoteDetails(String noteId) {
        return null;//template.findOne( Query.query( Criteria.where( "id" ).is( noteId ) ), Note.class );
    }
}
