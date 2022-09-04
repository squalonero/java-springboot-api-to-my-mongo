package com.sqlnr.app.mapper;

/**
 * This file is not working atm.
 * I still have to figure out how to make it work.
 */


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.sqlnr.app.model.Book;
import com.sqlnr.app.model.BookDto;

@Mapper(componentModel = "spring")
public interface BookMapper {
    // Mapping between Model and ModelDto
    // Skip null values when updating
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public Book toEntity(BookDto dto, @MappingTarget Book entity);
    public BookDto toDto(Book entity, @MappingTarget BookDto dto);

}
