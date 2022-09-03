package com.example.demo.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    final int PAGE_SIZE = 10;

    @RequestMapping("/list")
    public Response list(@RequestParam(required = false, defaultValue = "0") int page) {
        Pageable paging = PageRequest.of(page, PAGE_SIZE);
        Page<Book> pagedResult;
        try {
            pagedResult = bookRepository.findAll(paging);
        } catch (Exception e) {
            return new Response(false, "Error", null);
        }

        return new Response(true, "Success", pagedResult);
    }

    @RequestMapping("/{id}")
    public Response getItem(@PathVariable(value = "id") String id) {
        Book book;
        try {
            book = bookRepository.findById(id).get();
        } catch (Exception e) {
            return new Response(false, "Book not found", null);
        }
        return new Response(true, "", book);
    }

    @PostMapping("/create")
    public Response createItem(@RequestBody Book book) {
        Book saved;
        try {
            saved = bookRepository.save(book);
        } catch (Exception e) {
            return new Response(false, e.getMessage(), null);
        }

        return new Response(true, "Book created", saved);
    }

    @DeleteMapping("/{id}")
    public Response deleteItem(@PathVariable(value = "id") String id) {
        try {
            bookRepository.deleteById(id);
        } catch (Exception e) {
            return new Response(false, e.getMessage(), null);
        }
        return new Response(true, "Book deleted successfully", null);
    }

    @PutMapping("/{id}")
    public Response updateItem(@PathVariable("id") String id, @RequestBody BookDto bookDto) {
        Book dbBook;
        try {
            if (!Objects.equals(id, bookDto.getId())) {
                throw new IllegalArgumentException("Url Id and body Id must be the same");
            }
            dbBook = service.updateBook(id, bookDto);
        } catch (Exception e) {
            return new Response(false, "An error occurred during the update.", null);
        }

        return new Response(true, "", dbBook);

    }

}