package com.example.safestorage.messaging;

import com.example.safestorage.configurations.QueuesRoutes;
import com.example.safestorage.models.*;
import com.example.safestorage.services.NoteEncoder;
import com.example.safestorage.services.NoteService;
import com.example.safestorage.services.UserService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


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

        @RabbitListener(queues = QueuesRoutes.DECODE_QUEUE)
    public void handleDecodeQueueMessage(EncodeMessage message) {
        switch (message.getDataType()) {
            case List -> {
                var noteList = (List<Note>) message.getData();
                var result = noteList.stream().map(noteEncoder::decode);
                //TODO: send somehow somewhere
            }
            case Note -> {
                var note = (Note) message.getData();
                var result = noteEncoder.decode( note );
                //TODO: send somehow somewhere
            }
            default -> throw new IllegalArgumentException( "Illegal action type");
        }
    }


    @RabbitListener(queues = QueuesRoutes.DECODE_QUEUE)
    public void handleEncodeQueueMessage(DecodeMessage message) {
        var noteDTO = message.getNote();
        var userId = (String) template.convertSendAndReceive( "getIdByUsername", message.getUsername() );
        var code = noteEncoder.encode( noteDTO, userId );
        template.convertAndSend( "saveOrUpdateNote",  code );// saveOrUpdate???
    }

    @RabbitListener(queues = QueuesRoutes.GET_NOTE_QUEUE)
    public void handleGetNoteQueueMessage(GetNoteMessage message) {
        switch (message.getDataType()) {
            case List -> {
                var userId = (String) template.convertSendAndReceive( "getIdByUsername", message.getData() );
                var result = noteService.listNotes( userId );
                template.convertAndSend( "decode", new EncodeMessage( DataType.List, result));// saveOrUpdate???
            }
            case Note -> {
                var result = noteService.getNoteDetails( message.getData() );
                template.convertAndSend( "decode", new EncodeMessage( DataType.Note, result));// saveOrUpdate???
            }
            default -> throw new IllegalArgumentException( "Illegal action type");
        }
    }

    @RabbitListener(queues = QueuesRoutes.SAVE_OR_UPDATE_QUEUE)
    public void handleSaveOrUpdateNoteQueueMessage(Note newNote) {
        noteService.saveNote( newNote );
        //TODO: send somehow somewhere
    }

    @RabbitListener(queues = QueuesRoutes.REMOVE_NOTE_QUEUE)
    public void handleRemoveNoteQueueMessage(String id) {
        noteService.removeNote( id );
        //TODO: send somehow somewhere
    }

    @RabbitListener(queues = QueuesRoutes.GET_ID_BY_USERNAME_QUEUE)
    public String handleUserQueueMessage(String username) {
        return userService.getIdByUsername( username );
    }

}
