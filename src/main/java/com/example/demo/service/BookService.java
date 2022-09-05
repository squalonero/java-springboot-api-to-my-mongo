package com.example.demo.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.lang.reflect.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;

@Service
public class BookService {

    @Autowired // Allows Spring to resolve and inject this class automatically
    private BookRepository repository;

    // CREATE
    public Book createBook(Book newBook) {

        return repository
                .save(newBook);
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

    public void updatePartial(String bookId, Book book) throws Exception {

        for (final java.lang.reflect.Field field : Book.class.getDeclaredFields()) {
            final String fieldName = field.getName();

            if (fieldName.equals("id")) {
                continue;
            }
            final Method getter = Book.class.getDeclaredMethod("get" +
                    StringUtils.capitalize(fieldName));
            final Object fieldValue = getter.invoke(book);

            if (Objects.nonNull(fieldValue)) {
                repository.updateByKey(bookId, fieldName, fieldValue);
            }
        }
    }

    // 4. Get count of documents in the collection
    public void findCountOfBooks() {
        long count = repository.count();
        System.out.println("Number of documents in the collection: " + count);
    }

    // public void updateGenreTitle(String genre) {

    // // Change to this new value
    // String newGenre = "teletubbies";

    // // Find all the items with the genre snacks
    // List<Book> list = repository.findAll(genre);

    // list.forEach(item -> {
    // // Update the genre in each document
    // int k = item.getGenres().indexOf(genre);
    // item.getGenres().set(k, newGenre);
    // System.out.println("Updating genre of " + item.getTitle() + " to " +
    // newGenre);
    // });

    // // Save all the items in database
    // List<Book> itemsUpdated = repository.saveAll(list);

    // if (itemsUpdated != null)
    // System.out.println("Successfully updated " + itemsUpdated.size() + "
    // items.");
    // }

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

    // public String getItemDetails(Book item) {

    // System.out.println(
    // "Item Title: " + item.getTitle() +
    // ", \nAuthor: " + item.getAuthor() +
    // ", \nGenres: " + item.getGenres());

    // return "";
    // }

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

    // public void updateItemByKey(String _id, String key, String value)
    // throws IllegalArgumentException, IllegalAccessException,
    // NoSuchFieldException, SecurityException {
    // Query query = new Query();
    // query.addCriteria(Criteria.where("_id").is(_id));
    // Book bookToUpdate = mongoTemplate.findAndModify(query,
    // BasicUpdate.update(key, value),
    // FindAndModifyOptions.none(), Book.class);
    // // Class cls = bookToUpdate.getClass();
    // bookToUpdate.getClass().getField(key).set(bookToUpdate, value);
    // mongoTemplate.save(bookToUpdate);
    // // bookToUpdate.get
    // // mongoTemplate.findOne(query, Book.class);

    // }
}
