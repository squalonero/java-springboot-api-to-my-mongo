package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController

public class BookController {
    @Autowired
    private BookService service;

    ObjectMapper obj = new ObjectMapper();

    @RequestMapping("/findAll")
    public Object saluta() {
        return service.showAllBooks();
        // return "Welcome";
    }

    @PostMapping("/create") // path endpoint
    public Map<String, Object> list(@RequestBody Book book) {

        Book newBook = new Book(book.getTitle(), book.getAuthor(), book.getPages(), book.getGenres(), book.getRating());

        Map<String, Object> res = obj.convertValue(service.createBook(newBook), Map.class);

        return res;

    }

    // @RequestMapping("/object")
    // public Map<String, Object> obj() throws JsonProcessingException {
    // Persona persona = new Persona("Gesù", "Cristo", 33, "Gesù@PadreTerno.inf",
    // "Paradiso",
    // "/Users/p.piras/Documents/progetti/spring-demo/src/image/maxresdefault.jpeg");

    // ObjectMapper ow = new ObjectMapper();

    // Map<String, Object> obj = ow.convertValue(persona, Map.class);

    // return obj;
    // }
}
