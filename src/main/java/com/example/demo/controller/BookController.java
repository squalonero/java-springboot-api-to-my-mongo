package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import com.example.demo.utils.Response;

@RestController
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @RequestMapping("/")
    public String helloWorld() {
        return "Hello World from Spring Boot";
    }

    @RequestMapping("/books")
    public List<Book> list() {
        return bookRepository.findAll();
    }

    @RequestMapping("/book")
    public Optional<Book> getItem(@RequestParam(value = "id") String id) {
        return bookRepository.findById(id);
    }

    @PostMapping("/book")
    public Book createItem(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "author") String author,
            @RequestParam(value = "pages") Integer pages,
            @RequestParam(value = "genres") ArrayList<String> genres,
            @RequestParam(value = "rating") Integer rating) {
        Book book = new Book(title, author, pages, genres, rating);
        return bookRepository.save(book);
    }

    @DeleteMapping("/book")
    public Response deleteItem(@RequestParam(value = "id") String id) {
        try {
            bookRepository.deleteById(id);
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
        return new Response(true, "Book deleted successfully");
    }

    @PatchMapping("/book")
    public Response updateItem(
            @RequestParam(value = "id") String id,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "pages", required = false) Integer pages,
            @RequestParam(value = "genres", required = false) ArrayList<String> genres,
            @RequestParam(value = "rating", required = false) Integer rating) {
        try {
            Optional<Book> book = bookRepository.findById(id);
            if (book.isPresent()) {
                if (title != null) {
                    book.get().setTitle(title);
                }
                if (author != null) {
                    book.get().setAuthor(author);
                }
                if (pages != null) {
                    book.get().setPages(pages);
                }
                if (genres != null) {
                    book.get().setGenres(genres);
                }
                if (rating != null) {
                    book.get().setRating(rating);
                }
                bookRepository.save(book.get());
            } else {
                return new Response(false, "Book not found");
            }
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
        return new Response(true, "Book updated successfully");
    }
}