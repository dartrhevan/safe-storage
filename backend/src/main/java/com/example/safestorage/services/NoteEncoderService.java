package com.example.safestorage.services;

import com.example.safestorage.models.Note;
import com.example.safestorage.models.NoteDTO;

public interface NoteEncoderService {
    Note encode(NoteDTO noteDTO, String ownerId);

    NoteDTO decode(Note coddedNote);
}
