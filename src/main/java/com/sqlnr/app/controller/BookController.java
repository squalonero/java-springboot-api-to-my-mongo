package com.sqlnr.app.controller;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.modelmapper.ModelMapper;
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

import com.sqlnr.app.model.Book;
import com.sqlnr.app.model.BookDto;
import com.sqlnr.app.repository.BookRepository;
import com.sqlnr.app.service.BookService;
import com.sqlnr.app.utils.Response;

@RestController
@CrossOrigin("*")
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService service;

    @Autowired
    private ModelMapper mapper;

    final int PAGE_SIZE = 10;

    final boolean DEBUG = true;

    // get list
    @RequestMapping("/list")
    public Response list(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false) String by,
            @RequestParam(required = false) String value) {

        Pageable paging = PageRequest.of(page, PAGE_SIZE);
        Page<Book> pagedResult;
        try {
            pagedResult = bookRepository.findAll(paging);
        } catch (Exception e) {
            if (DEBUG)
                return new Response(false, e.getMessage(), null);
            return new Response(false, "Error", null);
        }

        return new Response(true, "Success", pagedResult);
    }

    // get item
    @RequestMapping("/{id}")
    public Response getItem(@PathVariable(value = "id") String id) {
        Book book;
        try {
            book = bookRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            return new Response(false, "Book not found", null);
        } catch (Exception e) {
            if (DEBUG)
                return new Response(false, e.getMessage(), null);
            return new Response(false, "Book not found", null);
        }
        return new Response(true, "", book);
    }

    // add
    @PostMapping("/create")
    public Response createItem(@RequestBody BookDto book) {
        Book saved;
        try {
            Book bookEntity = mapper.map(book, Book.class);
            saved = bookRepository.save(bookEntity);
        } catch (NoSuchElementException e) {
            return new Response(false, "Book not found", null);
        } catch (Exception e) {
            if (DEBUG)
                return new Response(false, e.getMessage(), null);
            return new Response(false, e.getMessage(), null);
        }

        return new Response(true, "Book created", saved);
    }

    // delete
    @DeleteMapping("/{id}")
    public Response deleteItem(@PathVariable(value = "id") String id) {
        try {
            bookRepository.deleteById(id);
        } catch (NoSuchElementException e) {
            return new Response(false, "Book not found", null);
        } catch (Exception e) {
            if (DEBUG)
                return new Response(false, e.getMessage(), null);
            return new Response(false, e.getMessage(), null);
        }
        return new Response(true, "Book deleted successfully", null);
    }

    // edit
    @PutMapping("/{id}")
    public Response updateItem(@PathVariable("id") String id, @RequestBody BookDto bookDto) {
        Book dbBook;
        try {
            if (!Objects.equals(id, bookDto.getId()))
                throw new IllegalArgumentException("Url Id and body Id must be the same");

            dbBook = service.updateBook(id, bookDto);

        } catch (NoSuchElementException e) {
            return new Response(false, "Book not found", null);
        } catch (IllegalArgumentException e) {
            return new Response(false, e.getMessage(), null);
        } catch (Exception e) {
            if (DEBUG)
                return new Response(false, e.toString(), null);
            return new Response(false, "An error occurred during the update.", null);
        }

        return new Response(true, "", dbBook);

    }

}