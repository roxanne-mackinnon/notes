package com.example.notes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.example.notes.models.Note;
import com.example.notes.repositories.NoteRepository;
import com.example.notes.repositories.UserRepository;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/create")
public class CreateController {

    @Autowired
    private NoteRepository notes;

    @Autowired
    private UserRepository users;

    
    @GetMapping
    public String createNote() {
        return "create";
    }

    @PostMapping
    public String postNote(Model model,
                       @Valid Note note, Errors errors,
                       SessionStatus sessionStatus) {

        if (errors.hasErrors()) {
            return "create";
        }
        notes.save(new Note(note.getTitle(), note.getContent()));
        sessionStatus.setComplete();
        return "redirect:/home";
    }

    @ModelAttribute(name = "note")
    public Note newNote() {
        return new Note();
    }
}
