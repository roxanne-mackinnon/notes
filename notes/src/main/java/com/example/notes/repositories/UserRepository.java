package com.example.notes.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.notes.models.User;


public interface UserRepository 
        extends CrudRepository<User, Long> {
        User findByUsername(String username);
}
