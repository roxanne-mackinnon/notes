package com.example.notes.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("USERS")
//@AllArgsConstructor
public class User {

    @Id
    private Long id;

    private String username;

    private String password;


    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
