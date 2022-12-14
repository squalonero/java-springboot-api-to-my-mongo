package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
// @important MongoRepository includes standard CRUD operations
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Book;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

    @Query("{title:'?0'}") // query annotation marks this method as a query method with required parameter
    public Book findByTitle(String title);

    @Query("{author:'?0'}")
    public List<Book> findByAuthor(String author);

    public long count();

    @Query("{genres:{$in:['?0']}}")
    public List<Book> findAll(String genre);

    Page<Book> findAll(Pageable pageable);

}
