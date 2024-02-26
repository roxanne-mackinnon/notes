package com.example.notes.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.notes.models.Note;


public interface NoteRepository 
        extends CrudRepository<Note,Long> {

        Iterable<Note> findByUserId(Long userId);

        Optional<Note> findByIdAndUserId(Long id, Long userId);
}
