package com.example.notes.controllers;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.notes.models.Note;
import com.example.notes.models.User;
import com.example.notes.repositories.NoteRepository;
import com.example.notes.repositories.UserRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(path = "/api/notes",
                produces = "application/json")
@CrossOrigin(origins="http://localhost:8080")
public class NoteController {

    @Autowired
    private NoteRepository noteRepo;

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/all")
    public ResponseEntity<Iterable<Note>> getAllNotes(@AuthenticationPrincipal User user) {
        Iterable<Note> notes = noteRepo.findByUserId(user.getId());
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<Note> getNote(@AuthenticationPrincipal User user,
                                        @PathVariable("noteId") Long noteId) {
        Optional<Note> optNote = noteRepo.findByIdAndUserId(noteId, user.getId());

        // note with given id does not exist, or does not belong to user
        if (optNote.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Note note = optNote.get();
        return new ResponseEntity<>(note, HttpStatus.OK);
    }


    @PostMapping(consumes = "application/json")
    public ResponseEntity<Note> postNote(@AuthenticationPrincipal User user,
                                         @RequestBody @Valid Note note,
                                         BindingResult result) {
        if (result.hasErrors() || note == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        
        // ensure we can only save notes for the authenticated user
        // TODO: make sure notes received from client do not come with an id
        note.setUserId(user.getId());
        note.setId(null);
        Note added = noteRepo.save(note);
        return new ResponseEntity<>(added, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{noteId}")
    public ResponseEntity<Note> deleteNote(@AuthenticationPrincipal User user,
                                           @PathVariable("noteId") Long noteId) {
        Optional<Note> optNote = noteRepo.findByIdAndUserId(noteId, user.getId());

        // note with given id does not exist, or does not belong to user
        if (optNote.isEmpty() || noteId == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        noteRepo.deleteById(noteId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping(path = "/{noteId}")
    public ResponseEntity<Note> putNote(@AuthenticationPrincipal User user,
                                        @PathVariable("noteId") Long noteId,
                                        @Valid @RequestBody Note newNote,
                                        BindingResult result) {
        if (result.hasErrors() || noteId == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        Optional<Note> optNote = noteRepo.findByIdAndUserId(noteId, user.getId());
        
        if (optNote.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Note originalNote = optNote.get();
        newNote.setId(originalNote.getId());
        newNote.setUserId(originalNote.getUserId());
        newNote = noteRepo.save(newNote);

        return new ResponseEntity<>(newNote, HttpStatus.OK);
    }
}
