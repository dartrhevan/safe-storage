package com.example.safestorage.services;

//import com.example.safestorage.models.Note;

import com.example.safestorage.models.NoteDTO;

import java.util.List;

public interface NoteService {
    //Collection<Note> getNotesByOwnerId(String id);

    void saveNote(NoteDTO note, String ownerId);

    void removeNote(String noteId);

    void editNote(NoteDTO newNote);

    List<NoteDTO> listNotes(String userId);

    NoteDTO getNoteDetails(String noteId);
}
