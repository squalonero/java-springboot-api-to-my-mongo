package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;

@Service
public class BookService {

    @Autowired // Allows Spring to resolve and inject this class automatically
    private BookRepository repository;

    // CREATE
    public void createBook() {
        System.out.println("Data creation started...");
        // we have to create the ArrayList first
        ArrayList<String> genres = new ArrayList<String>() {
            {
                add("horror");
                add("fantasy");
            }
        };
        ArrayList<String> genres2 = new ArrayList<String>() {
            {
                add("thriller");
                add("drama");
            }
        };
        repository.save(new Book("Added from spring", "sqlnr", 125, genres, 1));
        repository.save(new Book("Added from spring 2", "sqlnr", 300, genres, 8));
        repository.save(new Book("Added from spring 3", "sqlnr", 345, genres, 5));
        repository.save(new Book("Added from spring: Resurrection", "sqlnr", 257, genres2, 9));
        repository.save(new Book("Added from spring: Origin", "sqlnr", 590, genres2, 9));
        System.out.println("Data creation complete...");
    }

    // DESTROY ALL THE DOCUMENTS
    public void deleteAllBooks() {
        System.out.println("Data deletion started...");
        repository.deleteAll();
        System.out.println("Data deletion complete...");
    }

    // READ
    // 1. Show all the data
    public void showAllBooks() {

        repository.findAll().forEach(item -> System.out.println(getItemDetails(item)));
    }

    // 2. Get item by title
    public void getBookByTitle(String title) {
        System.out.println("Getting item by title: " + title);
        try {
            Book item = repository.findByTitle(title);
            System.out.println(getItemDetails(item));
        } catch (Exception e) {
            System.out.println("Item not found");
        }
    }

    // 3. Get title and quantity of a all items of a particular genre
    public void getItemsByGenre(String genre) {
        System.out.println("Getting items for the genre " + genre);
        try {
            List<Book> list = repository.findAll(genre);
            list.forEach(item -> System.out.println("Title: " + item.getTitle() + ", Author: " + item.getAuthor()));
        } catch (Exception e) {
            System.out.println("No items found for the genre " + genre);
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

        System.out.println(list);

        list.forEach(item -> {
            // Update the genre in each document
            int k = item.getGenres().indexOf(genre);
            item.getGenres().set(k, newGenre);
        });

        System.out.println(list);

        // Save all the items in database
        List<Book> itemsUpdated = repository.saveAll(list);

        if (itemsUpdated != null)
            System.out.println("Successfully updated " + itemsUpdated.size() + " items.");
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
