package com.example.demo.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Book;
import com.example.demo.model.BookDto;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;
import com.example.demo.utils.Response;

@RestController
@CrossOrigin("*")
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService service;

    @RequestMapping("/list")
    public List<Book> list() {
        return bookRepository.findAll();
    }

    @RequestMapping("/{id}")
    public Optional<Book> getItem(@PathVariable(value = "id") String id) {
        return bookRepository.findById(id);
    }

    @PostMapping("/create")
    public Book createItem(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @DeleteMapping("/{id}")
    public Response deleteItem(@PathVariable(value = "id") String id) {
        try {
            bookRepository.deleteById(id);
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
        return new Response(true, "Book deleted successfully");
    }

    @PutMapping("/{id}")
    public Book updateItem(@PathVariable("id") String id, @RequestBody BookDto bookDto) {
        if (!Objects.equals(id, bookDto.getId())) {
            throw new IllegalArgumentException("IDs don't match");
        }
        Book dbBook = bookRepository.findById(id).get();
        dbBook.setTitle(bookDto.getTitle());
        dbBook.setAuthor(bookDto.getAuthor());
        dbBook.setPages(bookDto.getPages());
        dbBook.setGenres(bookDto.getGenres());
        dbBook.setRating(bookDto.getRating());

        return bookRepository.save(dbBook);
    }

}