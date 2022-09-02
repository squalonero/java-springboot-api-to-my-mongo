package com.example.demo.mapper;


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.demo.model.Book;
import com.example.demo.model.BookDto;

@Mapper(componentModel = "spring")
public interface BookMapper {
    // Mapping between Model and ModelDto
    // Skip null values when updating
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public Book toEntity(BookDto dto, @MappingTarget Book entity);
    public BookDto toDto(Book entity, @MappingTarget BookDto dto);

}
