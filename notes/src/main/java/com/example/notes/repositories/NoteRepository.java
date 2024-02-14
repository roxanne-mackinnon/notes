package com.example.notes.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.notes.models.Note;

public interface NoteRepository 
        extends CrudRepository<Note,Long> {
}
