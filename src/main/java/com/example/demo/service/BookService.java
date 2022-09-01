package com.example.demo.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Book;
import com.example.demo.model.Generic;
import com.example.demo.repository.BookRepository;

@Service
public class BookService {

    @Autowired // Allows Spring to resolve and inject this class automatically
    private BookRepository repository;

    // CREATE
    public Book createBook(Book newBook) {
        System.out.println("Data creation started...");
        return repository
                .save(new Book(
                        newBook.getTitle(),
                        newBook.getAuthor(),
                        newBook.getPages(),
                        newBook.getGenres(),
                        newBook.getRating()));
    }

    // DESTROY ALL THE DOCUMENTS
    public void deleteAllBooks() {
        System.out.println("Data deletion started...");
        repository.deleteAll();
        System.out.println("Data deletion complete...");
    }

    // READ
    // 1. Show all the data
    public Object showAllBooks() {
        return repository.findAll();
    }

    // 2. Get item by title
    public Book getBookByTitle(String title) {
        System.out.println("Getting item by title: " + title);
        try {
            Book item = repository.findByTitle(title);
            return item;
        } catch (Exception e) {
            System.out.println("Item not found");
            return null;
        }
    }

    // 3. Get title and quantity of a all items of a particular genre
    public List<Book> getItemsByGenre(String genre) {
        System.out.println("Getting items for the genre " + genre);
        try {
            List<Book> list = repository.findAll(genre);
            return list;
            // list.forEach(item -> System.out.println("Title: " + item.getTitle() + ",
            // Author: " + item.getAuthor()));
        } catch (Exception e) {
            System.out.println("No items found for the genre " + genre);
            return null;
        }

    }

    // 4. Get count of documents in the collection
    public void findCountOfBooks() {
        long count = repository.count();
        System.out.println("Number of documents in the collection: " + count);
    }

    public void updateGenreTitle(String genre) {

        // Change to this new value
        String newGenre = "teletubbies";

        // Find all the items with the genre snacks
        List<Book> list = repository.findAll(genre);

        list.forEach(item -> {
            // Update the genre in each document
            int k = item.getGenres().indexOf(genre);
            item.getGenres().set(k, newGenre);
            System.out.println("Updating genre of " + item.getTitle() + " to " + newGenre);
        });

        // Save all the items in database
        List<Book> itemsUpdated = repository.saveAll(list);

        if (itemsUpdated != null)
            System.out.println("Successfully updated " + itemsUpdated.size() + " items.");
    }

    public List<Book> filterByKey(String key, String filter) {
        if (key.equals("genres"))
            return repository.filterByGenre(filter);

        Integer num;
        try {
            num = Integer.parseInt(filter);
        } catch (NumberFormatException nfe) {
            return repository.filterByKey(key, filter);
        }
        return repository.filterByKey(key, num);
    }

    // We can
    // create a
    // helper method
    // to display
    // the output
    // of read
    // operations in
    // a readable format:

    // Print details in readable form

    public String getItemDetails(Book item) {

        System.out.println(
                "Item Title: " + item.getTitle() +
                        ", \nAuthor: " + item.getAuthor() +
                        ", \nGenres: " + item.getGenres());

        return "";
    }

    // DELETE
    public void deleteBook(String id) {
        // this comes from MongoRepository interface
        Optional<Book> bookToDelete = repository.findById(id);
        if (!bookToDelete.isEmpty()) {
            repository.delete(bookToDelete.get());
            System.out.println("Item deleted successfully");
        } else {
            System.out.println("Could not delete item: Item not found");
        }
    }
}
