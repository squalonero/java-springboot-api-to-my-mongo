package com.example.demo.model;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor // <--- THIS is it
public class BookDto {
    @Id
    private String id;
    private String title;
    private String author;
    private Integer pages;
    private ArrayList<String> genres;
    private int rating;

}
