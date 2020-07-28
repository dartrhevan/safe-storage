package com.example.safestorage.messaging;

import com.example.safestorage.configurations.QueuesNames;
import com.example.safestorage.models.*;
import com.example.safestorage.services.NoteEncoder;
import com.example.safestorage.services.NoteService;
import com.example.safestorage.services.UserService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@EnableRabbit //нужно для активации обработки аннотаций @RabbitListener
@Component
public class Listeners {

    private final NoteEncoder noteEncoder;

    private final RabbitTemplate template;

    private final UserService userService;

    private final NoteService noteService;

    @Autowired
    public Listeners(NoteEncoder noteEncoder, RabbitTemplate template, UserService userService, NoteService noteService) {
        this.noteEncoder = noteEncoder;
        this.template = template;
        this.userService = userService;
        this.noteService = noteService;
    }

    @RabbitListener(queues = QueuesNames.DECODE_QUEUE)//MUST BE QUEUE NOT ROUTE!!!!
    public Object handleDecodeQueueMessage(DecodeMessage message) {
        switch (message.getDataType()) {
            case List -> {
                var noteList = (List<Note>) message.getData();
                return noteList.stream().map(noteEncoder::decode).collect( Collectors.toList() );
                //TODO: send somehow somewhere
            }
            case Note -> {
                var note = (Note) message.getData();
                return noteEncoder.decode( note );
                //TODO: send somehow somewhere
            }
            default -> throw new IllegalArgumentException( "Illegal action type");
        }
    }


    @RabbitListener(queues = QueuesNames.ENCODE_QUEUE)
    public void handleEncodeQueueMessage(EncodeMessage message) {
        var noteDTO = message.getNote();
        var userId = (String) template.convertSendAndReceive( "getIdByUsername", message.getUsername() );
        var code = noteEncoder.encode( noteDTO, userId );
        template.convertAndSend( "saveOrUpdateNote",  code );// saveOrUpdate???
    }

    @RabbitListener(queues = QueuesNames.GET_NOTE_QUEUE)
    public Object handleGetNoteQueueMessage(GetNoteMessage message) {
        switch (message.getDataType()) {
            case List -> {
                var userId = (String) template.convertSendAndReceive( "getIdByUsername", message.getData() );
                var result = noteService.listNotes( userId );
                return template.convertSendAndReceive( "decode", new DecodeMessage( DataType.List, result));// saveOrUpdate???
            }
            case Note -> {
                var result = noteService.getNoteDetails( message.getData() );
                return template.convertSendAndReceive( "decode", new DecodeMessage( DataType.Note, result));// saveOrUpdate???
            }
            default -> throw new IllegalArgumentException( "Illegal action type");
        }
    }

    @RabbitListener(queues = QueuesNames.SAVE_OR_UPDATE_QUEUE)
    public void handleSaveOrUpdateNoteQueueMessage(Note newNote) {
        noteService.saveNote( newNote );
        //TODO: send somehow somewhere
    }

    @RabbitListener(queues = QueuesNames.REMOVE_NOTE_QUEUE)
    public void handleRemoveNoteQueueMessage(String id) {//TODO: check user
        noteService.removeNote( id );
        //TODO: send somehow somewhere
    }

    @RabbitListener(queues = QueuesNames.GET_ID_BY_USERNAME_QUEUE)
    public String handleUserQueueMessage(String username) {
        return userService.getIdByUsername( username );
    }

}
