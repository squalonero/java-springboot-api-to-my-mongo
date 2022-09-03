package com.example.demo.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

// import com.example.demo.mapper.BookMapper;
import com.example.demo.model.Book;
import com.example.demo.model.BookDto;
import com.example.demo.repository.BookRepository;

@Service
public class BookService {

    @Autowired // Allows Spring to resolve and inject this class automatically
    private BookRepository repo;

    public Book updateBook(String id, BookDto bookDto) throws Exception{

        Book dbBook = repo.findById(bookDto.getId()).get();

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
        repo.save(dbBook);
        return dbBook;
    }

}
