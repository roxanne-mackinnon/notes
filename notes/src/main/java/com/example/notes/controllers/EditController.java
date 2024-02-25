package com.example.notes.controllers;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.notes.models.Note;
import com.example.notes.models.User;
import com.example.notes.repositories.NoteRepository;

import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;

@Controller
public class EditController {
    
    @Autowired
    NoteRepository noteRepo;

    @ModelAttribute("currentNote") 
    public Note currentNote(@PathVariable("noteId") Long noteId) {
        return noteRepo.findById(noteId).orElseThrow();
    }
   
    @GetMapping("/edit/{noteId}")
    public String viewEdit(@ModelAttribute("currentNote") Note note,
                           @AuthenticationPrincipal User user,
                            Model model) throws AuthException {

        // ensure note belongs to user
        if (! note.getUserId().equals(user.getId())) {
            throw new AuthException("Not authorized to edit note " + note.getId() + ".");
        }
        return "edit";
    }

    @PostMapping("/edit/{noteId}")
    public String processEdit(@ModelAttribute("currentNote") Note note,
                              @Valid Note newNote,
                              @AuthenticationPrincipal User user,
                               Model model) throws AuthException {
        // only og user should be able to edit notes
        if (! note.getUserId().equals(user.getId())) {
            throw new AuthException("Not authorized to edit note " + note.getId() + ".");
        }

        note.setTitle(newNote.getTitle());
        note.setContent(newNote.getContent());
        noteRepo.save(note);
        
        return "redirect:/home";
    }
    
}
