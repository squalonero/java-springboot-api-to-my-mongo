package com.example.demo.model;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document("books")
@Getter
@Setter
public class Book {
    @Id
    private String id;
    private String title;
    private String author;
    private Integer pages;
    private ArrayList<String> genres;
    private int rating;

    public Book(String title, String author, Integer pages, ArrayList<String> genres, int rating) {
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.genres = genres;
        this.rating = rating;
    }

}
