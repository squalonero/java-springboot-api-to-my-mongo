package com.sqlnr.app.mapper;

/**
 * This file is not working atm.
 * I still have to figure out how to make it work.
 */


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.sqlnr.app.model.Booking;
import com.sqlnr.app.model.BookingDto;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    // Mapping between Model and ModelDto
    // Skip null values when updating
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public Booking toEntity(BookingDto dto, @MappingTarget Booking entity);
    public BookingDto toDto(Booking entity, @MappingTarget BookingDto dto);

}
