package com.example.safestorage.messaging;

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

    //TODO: extract all keys to constants!
    @RabbitListener(queues = "encoding")
    public void handleEncodingQueueMessage(EncodingMessage message) {
        switch (message.getAction()) {
            case Encode -> {
                var noteDTO = (NoteDTO)message.getData();
                var userId = (String) template.convertSendAndReceive( "getIdByUsername", message.getUsername() );
                var code = noteEncoder.encode( noteDTO, userId );
                template.convertAndSend( "note", new NoteMessage( NoteAction.Save, code ));// saveOrUpdate???
            }
            case DecodeList -> {
                var noteList = (List<Note>) message.getData();

                var result = noteList.stream().map(noteEncoder::decode);
                //TODO: send somehow somewhere
            }
            case DecodeNote -> {
                var note = (Note) message.getData();
                var result = noteEncoder.decode( note );
                //TODO: send somehow somewhere
            }
            default -> throw new IllegalArgumentException( "Illegal action type");
        }
    }

    @RabbitListener(queues = "note")
    public void handleNoteQueueMessage(NoteMessage message) {
        switch (message.getAction()) {
            case List -> {
                var userId = (String) template.convertSendAndReceive( "getIdByUsername", message.getData() );
                var result = noteService.listNotes( userId );
                template.convertAndSend( "encoding", new EncodingMessage(EncodingAction.DecodeList, result, (String) message.getData()));// saveOrUpdate???
            }
            case GetDetails -> {
                var result = noteService.getNoteDetails( (String) message.getData() );
                template.convertAndSend( "encoding", new EncodingMessage(EncodingAction.DecodeNote, result, null));// saveOrUpdate???
            }
            case Save -> {
                noteService.saveNote( (Note) message.getData() );
                //TODO: send somehow somewhere
            }
            case Edit -> //noinspection DuplicateBranchesInSwitch
                    {
                noteService.saveNote( (Note) message.getData() );
                //TODO: send somehow somewhere
            }
            case Remove -> {
                noteService.removeNote( (String) message.getData() );
                //TODO: send somehow somewhere
            }
            default -> throw new IllegalArgumentException( "Illegal action type");
        }
    }


    @RabbitListener(queues = "getIdByUsername")
    public String handleUserQueueMessage(String username) {
        return userService.getIdByUsername( username );
    }

}
