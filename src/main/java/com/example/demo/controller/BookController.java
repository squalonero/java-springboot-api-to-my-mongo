package com.example.demo.controller;

import java.util.List;
import java.util.Objects;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
// import com.example.demo.service.BookService;
import com.example.demo.utils.Response;

@RestController
@CrossOrigin("*")
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    // @Autowired
    // private BookService service;

    @RequestMapping("/list")
    public List<Book> list() {
        return bookRepository.findAll();
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
    public Book updateItem(@PathVariable("id") String id, @RequestBody BookDto bookDto) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        if (!Objects.equals(id, bookDto.getId())) {
            throw new IllegalArgumentException("IDs don't match");
        }

        Book dbBook = bookRepository.findById(id).get();

        for (final java.lang.reflect.Field field : Book.class.getDeclaredFields()) {
            final String fieldName = field.getName();

            if (fieldName.equals("id")) {
                continue;
            }
            final Method getter = bookDto.getClass().getDeclaredMethod("get" + StringUtils.capitalize(fieldName));
            final Object fieldValue = getter.invoke(bookDto);

            if (Objects.nonNull(fieldValue) && !fieldValue.equals("") && !fieldValue.equals(0)) {
                BeanUtils.setProperty(dbBook, fieldName, fieldValue);
            }
        }
        return bookRepository.save(dbBook);

    }

}