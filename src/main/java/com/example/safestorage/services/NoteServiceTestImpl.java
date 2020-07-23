package com.example.safestorage.services;

import com.example.safestorage.models.Note;
import com.example.safestorage.models.User;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class NoteServiceTestImpl implements NoteService {
    @Override
    public Collection<Note> getNotesByOwnerId(String id) {
        return null;
    }

    @Override
    public void saveNote(Note note, User user) {

    }
}
