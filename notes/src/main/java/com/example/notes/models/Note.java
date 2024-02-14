package com.example.notes.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Table("NOTES")
public class Note { 

    @Id
    private Long id;


    private Long userId;

    @NotBlank(message="Title must not be blank.")
    @Size(max=32, message="Title may not exceed 32 characters.")
    private String title;

    @NotBlank(message="Content must not be blank.")
    @Size(max=256, message="Content may not exceed 256 characters.")
    private String content;

    public Note(Long userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Note() {}

}
