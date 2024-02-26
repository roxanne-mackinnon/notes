package com.example.notes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.notes.models.Note;
import com.example.notes.models.User;
import com.example.notes.repositories.NoteRepository;
import com.example.notes.repositories.UserRepository;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    @ModelAttribute("noteCount")
    public Long noteCount() {
        return noteRepository.count();
    }

    @ModelAttribute("userCount")
    public Long userCount() {
        return userRepository.count();
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String getHome(@AuthenticationPrincipal User user,
                          Model model) {

        Iterable<Note> notes = noteRepository.findByUserId(user.getId());
        model.addAttribute("notes", notes);                            
        model.addAttribute("user", user);
        

        return "home";
    }

}
