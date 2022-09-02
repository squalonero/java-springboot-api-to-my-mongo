package com.example.demo.model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
// import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Document("books")
@Getter @Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String id;
    public String title;
    public String author;
    public Integer pages;
    public ArrayList<String> genres;
    public int rating;

    public Book(String title, String author, Integer pages, ArrayList<String> genres, int rating) {
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.genres = genres;
        this.rating = rating;
    }
}
