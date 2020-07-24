package com.example.safestorage.services;

//import com.example.safestorage.models.Note;

import com.example.safestorage.models.Note;
import com.example.safestorage.models.NoteDTO;
import com.example.safestorage.models.User;

import java.util.Collection;

public interface NoteService {
    //Collection<Note> getNotesByOwnerId(String id);
    void saveNote(Note note, User user);
    void removeNote(Note note, User user);
    void editNote(Note newNote);
}
