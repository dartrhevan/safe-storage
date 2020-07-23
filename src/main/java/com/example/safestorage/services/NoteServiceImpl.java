package com.example.safestorage.services;

import com.example.safestorage.models.Note;
import com.example.safestorage.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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
    public void saveNote(Note note, User user) {
        user.getNotes().add( note );
        template.save( note );
        template.save( user );
    }
}
