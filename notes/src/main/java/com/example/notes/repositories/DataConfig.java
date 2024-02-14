package com.example.notes.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.notes.models.Note;
import com.example.notes.models.User;

@Configuration
public class DataConfig {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private NoteRepository noteRepo;
    
    @Bean
    public CommandLineRunner userLoader() {
        return args -> {
            noteRepo.deleteAll();            
            userRepo.deleteAll();
            User roxanne = userRepo.save(new User("roxanne", "Horsecredentials5!69420"));
            User amanda = userRepo.save(new User("amanda", "Biteme!"));
            User manny = userRepo.save(new User("manny", ""));
            noteRepo.save(new Note(roxanne.getId(), "my first note'", "I love my girlfriend amanda!"));
            noteRepo.save(new Note(amanda.getId(), "im cute", "im the cutest cutie who ever cutied"));
            noteRepo.save(new Note(manny.getId(), "my hobbies", "taking my couple friends to strip clubs"));
        };
    }   
    
}
