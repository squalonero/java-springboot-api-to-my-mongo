package com.example.demo.model;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("books")
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

    public String get_id() {
        return this.id;
    }

    public void set_id(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPages() {
        return this.pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public ArrayList<String> getGenres() {
        return this.genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public int getRating() {
        return this.rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}
