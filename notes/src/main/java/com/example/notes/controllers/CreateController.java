package com.example.notes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.notes.models.Note;
import com.example.notes.models.User;
import com.example.notes.repositories.NoteRepository;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/create")
public class CreateController {

    @Autowired
    private NoteRepository notes;
    
    @ModelAttribute(name = "note")
    public Note newNote() {
        return new Note();
    }
    
    @GetMapping
    public String create() {
        return "create";
    }

    @PostMapping
    public String postNote(Model model, @AuthenticationPrincipal User user,
                       @Valid Note note, Errors errors) {
        if (errors.hasErrors()) {
            return "create";
        }

        notes.save(new Note(user.getId(), note.getTitle(), note.getContent()));
        return "redirect:/home";
    }
    
}
