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
    
}
