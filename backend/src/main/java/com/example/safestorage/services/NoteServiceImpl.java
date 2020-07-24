package com.example.safestorage.services;

import com.example.safestorage.models.Note;
import com.example.safestorage.models.NoteDTO;
import com.example.safestorage.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class NoteServiceImpl implements NoteService {
    /*
    @Override
    public Collection<Note> getNotesByOwnerId(String id) {
        return null;
    }*/

    private final MongoTemplate template;

    @Autowired
    public NoteServiceImpl(MongoTemplate template) {
        this.template = template;
    }

    @Override
    public void saveNote(Note note) {
        //user.getNotes().add( note );
        template.save( note );
        //template.save( user );
    }

    @Override
    public void removeNote(Note note) {
        //user.getNotes().remove( note );
        template.remove( note );
        //template.remove( user );
    }

    @Override
    public void editNote(Note newNote) {
        template.save( newNote );//findAndModify( Query.query( Criteria.where( note. ) ) )
    }
}
