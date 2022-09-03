package com.example.demo.controller;

import java.util.List;
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
    public Page<Book> list(@RequestParam(required = false, defaultValue = "0") int page) {
         Pageable paging = PageRequest.of(page, PAGE_SIZE);

        return bookRepository.findAll(paging);
    }

    @RequestMapping("/{id}")
    public Book getItem(@PathVariable(value = "id") String id) {
        return bookRepository.findById(id).get();
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
    public Book updateItem(@PathVariable("id") String id, @RequestBody BookDto bookDto) throws Exception {

        if (!Objects.equals(id, bookDto.getId())) {
            throw new IllegalArgumentException("Url Id and body Id must be the same");
        }
        Book dbBook = service.updateBook(id, bookDto);

        return dbBook;

    }

}