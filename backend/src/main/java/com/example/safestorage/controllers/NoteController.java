package com.example.safestorage.controllers;

import com.example.safestorage.configurations.QueuesRoutes;
import com.example.safestorage.models.DataType;
import com.example.safestorage.models.EncodeMessage;
import com.example.safestorage.models.GetNoteMessage;
import com.example.safestorage.models.NoteDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        return (List<NoteDTO>) template.convertSendAndReceive( QueuesRoutes.GET_NOTE, new GetNoteMessage( DataType.List, user.getName()) );
    }

    @GetMapping("/{id}")
    public NoteDTO getNote(@PathVariable("id") String id, Principal user) {
        return (NoteDTO) template.convertSendAndReceive( QueuesRoutes.GET_NOTE, new GetNoteMessage( DataType.Note, id) );
    }

    @PostMapping
    public void addNote(NoteDTO note, Principal user) {
        template.convertAndSend( QueuesRoutes.ENCODE, new EncodeMessage( note, user.getName() ) );
        // WRONG QUEUE & MESSAGE FORMAT!
    }

    @PutMapping
    public void editNote(NoteDTO note, Principal user) {
        template.convertAndSend( QueuesRoutes.ENCODE, new EncodeMessage( note, user.getName() ) );

        //template.convertAndSend( QueuesRoutes.SAVE_OR_UPDATE_QUEUE, note);
        //WRONG QUEUE & MESSAGE FORMAT!
    }

    @DeleteMapping("/{id}")
    public void removeNote(@PathVariable("id") String id, Principal user, HttpStatus status/*,  HttpServletResponse response*/) {
        /*if(user == null) {
            response.setStatus( 401 );
            return;
        }*/
        template.convertAndSend( QueuesRoutes.REMOVE_NOTE, id );//TODO: send username to check
    }
}
