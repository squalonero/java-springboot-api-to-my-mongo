package com.example.demo.service;

import java.lang.reflect.Field;
// import java.text.ParseException;
// import java.util.Arrays;

// import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// import com.example.demo.mapper.BookMapper;
import com.example.demo.model.Book;
import com.example.demo.model.BookDto;
import com.example.demo.repository.BookRepository;

@Service
public class BookService {

    @Autowired // Allows Spring to resolve and inject this class automatically
    private BookRepository repository;

    // @Autowired
    // private ModelMapper mapper;

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

    public Book updateBook(BookDto dto) throws Exception{
        try {
            Book dbBook = repository.findById(dto.getId()).get();
            Field[] fields = Book.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if(field.get(dto) != null)
                    field.set(dbBook, field.get(dto));
            }
            return repository.save(dbBook);
        } catch (Exception e) {
             throw new RuntimeException(e.getMessage() + "\n\n\n");
        }
    }

}
