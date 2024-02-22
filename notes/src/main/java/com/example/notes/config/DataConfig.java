package com.example.notes.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.notes.models.Note;
import com.example.notes.models.User;
import com.example.notes.repositories.NoteRepository;
import com.example.notes.repositories.UserRepository;

@Configuration
public class DataConfig {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private NoteRepository noteRepo;

    @Autowired
    private PasswordEncoder encoder;
    
    @Bean
    public CommandLineRunner userLoader() {
        return args -> {
            User user1 = new User("exampleUser1", encoder.encode("p@ssw0rd"));
            User user2 = new User("admin", encoder.encode("admin"));
            userRepo.save(user1);
            userRepo.save(user2);

            noteRepo.save(new Note(user1.getId(), "title", "personal note from exampleUser1"));
            noteRepo.save(new Note(user2.getId(), "admin post", "content of admin post"));
        };
    }   
    
}
