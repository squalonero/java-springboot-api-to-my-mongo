package com.example.demo.repository;

import java.util.List;

// @important MongoRepository includes standard CRUD operations
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.BookDto;
import com.example.demo.model.Book;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

    @Query("{title:?0}") // query annotation marks this method as a query method with required parameter
    public Book findByTitle(String title);

    @Query("{author:?0}")
    public List<Book> findByAuthor(String author);

    public long count();

    @Query("{genres:{$in:[?0]}}")
    public List<Book> findAll(String genre);

    @Query("{?0:/?1/}") // variables between backslashs triggers the LIKE operator
    public List<Book> filterByKey(String key, Object filter);

    @Query("{?0:?1}") // variables between backslashs triggers the LIKE operator
    public List<Book> filterByKey(String key, Integer filter);

    @Query("{genres:{$in:[/^?0/]}}")
    public List<Book> filterByGenre(Object filter);

    @Query("{id:?0}")
    @Update("{$set:{?1:?2}}")
    public void updateByKey(String id, String key, Object value);

}
