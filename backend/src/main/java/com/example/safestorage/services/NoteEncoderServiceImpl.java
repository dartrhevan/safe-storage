package com.example.safestorage.services;

import com.example.safestorage.models.Note;
import com.example.safestorage.models.NoteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


@Service
public class NoteEncoderServiceImpl implements NoteEncoderService {

    private final EncodingServiceImpl encodingService;

    @Autowired
    public NoteEncoderServiceImpl(EncodingServiceImpl encodingService) {
        this.encodingService = encodingService;
    }

    @Override
    public Note encode(NoteDTO noteDTO, String ownerId) {
        encodingService.setKey( ownerId );
        return new Note( encodingService.encode( noteDTO.getHead() ),
                         encodingService.encode( noteDTO.getText() ), ownerId, noteDTO.getDate());
    }

    @Override
    public NoteDTO decode(Note coddedNote) {
        encodingService.setKey( coddedNote.getOwnerId() );
        return new NoteDTO( encodingService.decode( coddedNote.getEncodedHeader() ), encodingService.decode( coddedNote.getEncodedText() ),
                            coddedNote.getId(), coddedNote.getAddingDate() );
    }
}
