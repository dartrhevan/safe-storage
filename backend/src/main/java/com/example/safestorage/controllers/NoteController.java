package com.example.safestorage.controllers;

import com.example.safestorage.models.Note;
import com.example.safestorage.models.NoteDTO;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequestMapping("/api/note")
@RestController
public class NoteController {

    @Autowired
    private AmqpTemplate template;

    @GetMapping("/list")
    public List<Note> getNotes(Principal user) {
        //TODO: enqueue get message
        //template.
        return null;
    }

    @GetMapping("/{id}")
    public Note getNote(@PathVariable("id") String id, Principal user) {
        //TODO: enqueue get message

        return null;
    }

    @PostMapping
    public void addNote(NoteDTO note, Principal user) {
        //TODO: enqueue post message

    }

    @PutMapping
    public void editNote(Note note, Principal user) {
        //TODO: enqueue edit note

    }

    @DeleteMapping("/{id}")
    public void removeNote(@PathVariable("id") String id, Principal user) {
        //TODO: enqueue remove note

    }
}
