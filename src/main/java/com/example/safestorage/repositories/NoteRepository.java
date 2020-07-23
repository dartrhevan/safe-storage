package com.example.safestorage.repositories;

import com.example.safestorage.models.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NoteRepository extends MongoRepository<Note, String> {
}
