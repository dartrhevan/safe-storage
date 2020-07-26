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
    private final NoteEncoderService encodingService;

    @Autowired
    public NoteServiceImpl(MongoTemplate template, NoteEncoderService encodingService) {
        this.template = template;
        this.encodingService = encodingService;
    }

    @Override
    public void saveNote(NoteDTO noteDTO, String ownerId) {
        var note = encodingService.encode( noteDTO, ownerId );
        template.save( note );
    }

    @Override//fine!!!
    public void removeNote(String noteId) {
        //user.getNotes().remove( note );
        template.findAllAndRemove( Query.query( Criteria.where( "id" ).is( noteId ) ), Note.class );///remove( note );
        //template.remove( user );
    }

    @Override
    public void editNote(NoteDTO newNote) {//change somehow??
        var note = template.findById( newNote.getId(), Note.class );
        /*encodingService.setKey( note.getOwnerId() );
        note.setEncodedHeader( encodingService.encode( newNote.getHead() ) );
        note.setEncodedText( encodingService.encode( newNote.getText()) );*/
        //var note = new Note(encodingService.encode( newNote.getHead() ), encodingService.encode( newNote.getText()),
        //                   originalNote.getOwnerId(), newNote.getDate());
        //note.setId( originalNote.getId() );
        template.save( encodingService.encode( newNote, note.getOwnerId() ) );
    }

    @Override
    public List<NoteDTO> listNotes(String userId) {
        var query = Query.query( Criteria.where( "ownerId" ).is(userId));
        //encodingService.setKey( userId );
        query.fields().exclude( "text" ).exclude( "addingDate" );
        return template.find( query, Note.class ).stream().map( encodingService::decode//extract
               /*new NoteDTO(encodingService.decode( n.getEncodedHeader() ),
                  null, n.getId(), null)*/ ).collect( Collectors.toList());
    }

    @Override
    public NoteDTO getNoteDetails(String noteId) {
        var note = template.findOne( Query.query( Criteria.where( "id" ).is( noteId ) ), Note.class );
        return encodingService.decode(note);
    }
}
