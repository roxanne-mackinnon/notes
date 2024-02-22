package com.example.notes.controllers;

import java.util.List;
import java.util.Vector;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.notes.models.Note;
import com.example.notes.models.User;
import com.example.notes.repositories.NoteRepository;
import com.example.notes.repositories.UserRepository;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {

    private NoteRepository noteRepository;

    private List<Note> notes = new Vector<>();

    private User user;

    public HomeController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @GetMapping("/home")
    public String getAltHome(@AuthenticationPrincipal User user, Model model) {
        return home(user, model);
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal User user, Model model) {
        this.user = user;
        model.addAttribute("user", user);
        fetchNotes(user.getId());
        model.addAttribute("notes",notes);
        return "home";
    }

    private void fetchNotes(Long userId) {
        notes.clear();
        for (Note note : noteRepository.findByUserId(userId)) {
            notes.add(note);
        }
    }
}
