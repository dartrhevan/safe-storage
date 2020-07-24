package com.example.safestorage.controllers;

import com.example.safestorage.models.Note;
import com.example.safestorage.models.NoteDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/api/note")
@RestController
public class NoteController {

    @GetMapping
    public void getNotes(Principal user) {
        //TODO: enqueue get message
    }

    @PostMapping
    public void addNote(NoteDTO note, Principal user) {
        //TODO: enqueue post message
    }

    @PutMapping
    public void editNote(Note note, Principal user) {
        //TODO: enqueue edit note
    }

    @DeleteMapping
    public void removeNote(@RequestParam String id, Principal user) {
        //TODO: enqueue remove note
    }
}
