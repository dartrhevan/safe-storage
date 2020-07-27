package com.example.safestorage.controllers;

import com.example.safestorage.configurations.QueuesRoutes;
import com.example.safestorage.models.DataType;
import com.example.safestorage.models.GetNoteMessage;
import com.example.safestorage.models.Note;
import com.example.safestorage.models.NoteDTO;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;
import java.security.Principal;
import java.util.List;

@RequestMapping("/api/note")
@RestController
public class NoteController {

    private final AmqpTemplate template;

    @Autowired
    public NoteController(AmqpTemplate template) {
        this.template = template;
    }

    @GetMapping("/list")
    public List<NoteDTO> getNotes(Principal user) {
        return (List<NoteDTO>) template.convertSendAndReceive( QueuesRoutes.GET_NOTE_QUEUE, new GetNoteMessage( DataType.List, user.getName()) );
    }

    @GetMapping("/{id}")
    public NoteDTO getNote(@PathVariable("id") String id, Principal user) {
        return (NoteDTO) template.convertSendAndReceive( QueuesRoutes.GET_NOTE_QUEUE, new GetNoteMessage( DataType.Note, id) );
    }

    @PostMapping
    public void addNote(NoteDTO note, Principal user) {
        template.convertAndSend( QueuesRoutes.SAVE_OR_UPDATE_QUEUE, note);
    }

    @PutMapping
    public void editNote(NoteDTO note, Principal user) {
        template.convertAndSend( QueuesRoutes.SAVE_OR_UPDATE_QUEUE, note);
    }

    @DeleteMapping("/{id}")
    public void removeNote(@PathVariable("id") String id, Principal user, HttpStatus status/*,  HttpServletResponse response*/) {
        /*if(user == null) {
            response.setStatus( 401 );
            return;
        }*/
        template.convertAndSend( QueuesRoutes.REMOVE_NOTE_QUEUE, id );//TODO: send username
    }
}
