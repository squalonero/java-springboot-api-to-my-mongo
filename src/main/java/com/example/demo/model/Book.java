package com.example.demo.model;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Document("books")
@Getter // Create all getters from lombok
@Setter // Create all setters from lombok
@NoArgsConstructor // needed if you have to create a DTO for handling convertion

public class Book {
    @Id
    private String id;
    private String title;
    private String author;
    private Integer pages;
    private ArrayList<String> genres;
    private Integer rating;

    public Book(String title, String author, Integer pages, ArrayList<String> genres, Integer rating) {
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.genres = genres;
        this.rating = rating;
    }

}
