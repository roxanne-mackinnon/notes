package com.example.notes.controllers;

import org.springframework.stereotype.Controller;
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

    private UserRepository userRepository;

    public HomeController(UserRepository userRepository, NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    @ModelAttribute("notes")
    public Iterable<Note> getNotes() {
        return noteRepository.findAll();
    }

    @ModelAttribute("users")
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/home")
    public String getAltHome() {
        return "home";
    }

    @GetMapping("/")
    public String getHome() {
        return "home";
    }
}
