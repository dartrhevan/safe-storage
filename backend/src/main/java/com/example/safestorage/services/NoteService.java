package com.example.safestorage.services;

//import com.example.safestorage.models.Note;

import com.example.safestorage.models.Note;
import com.example.safestorage.models.NoteDTO;

import java.util.List;

public interface NoteService {

    void saveNote(Note note);

    void removeNote(String noteId, String ownerId);

    void editNote(Note newNote);

    List<Note> listNotes(String userId);

    Note getNoteDetails(String noteId);
}
