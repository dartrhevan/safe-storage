package com.example.safestorage.services;

//import com.example.safestorage.models.Note;

import com.example.safestorage.models.Note;
import com.example.safestorage.models.NoteDTO;
import com.example.safestorage.models.User;

import java.util.Collection;

public interface NoteService {
    //Collection<Note> getNotesByOwnerId(String id);

    void saveNote(Note note);

    void removeNote(Note note);

    void editNote(Note newNote);
}
