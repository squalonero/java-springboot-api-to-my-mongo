package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.lang.reflect.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.demo.dto.BookDto;
import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;

@Service
public class BookService {

    @Autowired // Allows Spring to resolve and inject this class automatically
    private BookRepository repository;
    @Autowired
    private ModelMapper modelMapper;

    // Utils converting entities in dto and vice versa

    private BookDto convertToDto(Book book) {
        BookDto bookDto = modelMapper.map(book, BookDto.class);
        return bookDto;
    }

    private Book convertToEntity(BookDto book) {
        Book bookDto = modelMapper.map(book, Book.class);
        return bookDto;
    }

    // CREATE
    public Book createBook(BookDto newBook) {

        try {
            Book book = convertToEntity(newBook);
            return repository.save(book);
        } catch (Error e) {
            return null;
        }
    }

    // DESTROY ALL THE DOCUMENTS
    public void deleteAllBooks() {
        repository.deleteAll();
    }

    // READ
    // 1. Show all the data
    public Object showAllBooks() {
        List<Book> res = repository.findAll();
        List<BookDto> list = new ArrayList<BookDto>();

        for (Book book : res) {
            list.add(convertToDto(book));
        }
        return list;

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

    public List<Book> filterByKey(BookDto filter) throws Exception {

        for (final Field key : BookDto.class.getDeclaredFields()) {

            final String fieldName = key.getName();
            final Class<?> fieldType = key.getType();

            final Method getter = BookDto.class.getDeclaredMethod("get" +
                    StringUtils.capitalize(fieldName));

            final Object filterValue = getter.invoke(filter);

            if (Objects.nonNull(filterValue)) {

                if (fieldType.isArray())
                    return repository.filterByGenre(filterValue);

                if (fieldType.equals(Integer.TYPE))
                    return repository.filterByKey(fieldName, (Integer) filterValue);

                return repository.filterByKey(fieldName, filterValue);
            }

        }

        return null;
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
