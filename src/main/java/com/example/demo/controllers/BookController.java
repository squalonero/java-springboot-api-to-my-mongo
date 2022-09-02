package com.example.demo.controllers;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BookDto;
import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CustomBookRepository;
import com.example.demo.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.model.Field;

@RestController

public class BookController {
    @Autowired
    private BookService service;

    @Autowired
    private CustomBookRepository customBookRepository;

    @Autowired
    private ModelMapper modelMapper;

    ObjectMapper obj = new ObjectMapper();

    // Request for all books in collection
    @RequestMapping("/findAll")
    public Object findAll() {
        return service.showAllBooks();
    }

    // filter list by key needs to specify the key and the filter value
    // now works only for strings values
    @RequestMapping("/filterByTitle")
    public List<Book> findByTitle(@RequestParam String key, String filter) {
        return service.filterByKey(key, filter);
    }

    @RequestMapping("/filterByKey")
    public List<Book> findByKey(@RequestParam String key, String filter) {
        return service.filterByKey(key, filter);
    }

    // Insert new Book in DB
    @PostMapping("/create") // path endpoint
    public BookDto create(@RequestBody Book book) {
        return convertToDto(service.createBook(book));
    }

    @PutMapping("/mod/{id}")
    public void mod(@PathVariable final String id, @RequestBody final Book book) {

    }

    @PatchMapping("/modify/{bookId}")
    public void modify(@PathVariable final String bookId, @RequestBody final Book book) throws Exception {

        for (final java.lang.reflect.Field field : Book.class.getDeclaredFields()) {
            final String fieldName = field.getName();

            if (fieldName.equals("id")) {
                continue;
            }
            final Method getter = Book.class.getDeclaredMethod("get" + StringUtils.capitalize(fieldName));
            final Object fieldValue = getter.invoke(book);

            if (Objects.nonNull(fieldValue)) {
                customBookRepository.partialUpdate(bookId, fieldName, fieldValue);
            }
        }

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

    private BookDto convertToDto(Book book) {
        BookDto bookDto = modelMapper.map(book, BookDto.class);
        return bookDto;
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
