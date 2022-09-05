package com.example.demo.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BookDto;
import com.example.demo.model.Book;

import com.example.demo.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController

public class BookController {
    @Autowired
    private BookService service;

    ObjectMapper obj = new ObjectMapper();

    // Request for all books in collection
    @RequestMapping("/findAll")
    public Object findAll() {
        return service.showAllBooks();
    }

    // filter list by key needs to specify the key and the filter value
    // now works only for strings values
    // @RequestMapping("/filterByTitle")
    // public List<Book> findByTitle(@RequestParam String key, String filter) {
    // return service.filterByKey(key, filter);
    // }

    @RequestMapping("/filterByKey")
    public List<Book> findByKey(@RequestBody BookDto filter) throws Exception {
        return service.filterByKey(filter);
    }

    // Insert new Book in DB
    @PostMapping("/create") // path endpoint
    public Book create(@RequestBody BookDto book) {
        return service.createBook(book);
    }

    // update one or more fields of an object in collection specified by id ()
    @PutMapping("/mod/{id}")
    public void mod(@PathVariable final String id, @RequestBody final Book book)
            throws Exception {
        service.updatePartial(id, book);
    }

    // Find all books that matches a specific genre - needs query parameter
    // ../search?genre=yourValue
    @RequestMapping("/search")
    public List<Book> findByGenre(@RequestParam String genre) {
        return service.getItemsByGenre(genre);
    }

    @PostMapping("/delete")
    public void delete(@RequestParam String id) {
        service.deleteBook(id);
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
