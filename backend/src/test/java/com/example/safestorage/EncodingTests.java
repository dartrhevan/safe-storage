package com.example.safestorage;

import com.example.safestorage.models.NoteDTO;
import com.example.safestorage.services.*;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class EncodingTests {

    private final EncodingService encodingService = new EncodingServiceImpl( new EncryptionServiceImpl(), new CompressionServiceImpl() );

    private final NoteEncoder noteEncoder = new NoteEncoderImpl( encodingService );

    @Test
    void simpleEncodingTest() {
        var source = "dwevoidsjvwe9";
        var key = "yhugr";
        encodingService.setKey( key );
        var code = encodingService.encode( source );
        //assert !source.equals( code );
        var result = encodingService.decode( code );
        assert result.equals( source );
    }

    @Test
    void noteEncodeTest() {
        var ownerId = "123";
        var noteDTO = new NoteDTO( "head", "text", null, null );
        var note = noteEncoder.encode( noteDTO, ownerId );
        var decodedNoteDTO = noteEncoder.decode( note );
        System.out.println(noteDTO);
        System.out.println(decodedNoteDTO);
        assert decodedNoteDTO.equals( noteDTO );
        assert ownerId.equals( note.getOwnerId() );
    }


    @Test
    void listItemEncodeTest() {
        var ownerId = "123";
        var noteDTO = new NoteDTO( "head", null, null, null );
        var note = noteEncoder.encode( noteDTO, ownerId );
        var decodedNoteDTO = noteEncoder.decode( note );
        System.out.println(noteDTO);
        System.out.println(decodedNoteDTO);
        assert decodedNoteDTO.equals( noteDTO );
        assert ownerId.equals( note.getOwnerId() );
    }
}
