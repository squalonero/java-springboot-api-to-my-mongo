package com.example.demo.service;

import java.text.ParseException;
import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.BookMapper;
import com.example.demo.model.Book;
import com.example.demo.model.BookDto;
import com.example.demo.repository.BookRepository;

@Service
public class BookService {

    @Autowired // Allows Spring to resolve and inject this class automatically
    private BookRepository repository;

    @Autowired
    private ModelMapper mapper;

    // @Autowired
    // private BookMapper bookMapper;

    // public Book updateBook(BookDto dto){
    //     try {
    //         Book dbBook = repository.findById(dto.getId()).get();
    //         return repository.save(dto);
    //     } catch (Exception e) {
    //         throw new RuntimeException("Book not found");
    //     }
    // }

}
