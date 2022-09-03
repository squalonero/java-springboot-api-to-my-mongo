package com.example.demo.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

// import com.example.demo.mapper.BookingMapper;
import com.example.demo.model.Booking;
import com.example.demo.model.BookingDto;
import com.example.demo.repository.BookingRepository;

@Service
public class BookingService {

    @Autowired // Allows Spring to resolve and inject this class automatically
    private BookingRepository repo;

    public Booking updateBooking(String id, BookingDto bookingDto) throws Exception{

        Booking dbBooking = repo.findById(bookingDto.getId()).get();
        //iterate over the object and get the properties
        for (final Field field : Booking.class.getDeclaredFields()) {
            //get each propeerty name
            final String fieldName = field.getName();

            if (fieldName.equals("id")) {
                continue; // skip id updates
            }
            //assemble the getter method name
            final Method getter = bookingDto.getClass().getDeclaredMethod("get" + StringUtils.capitalize(fieldName));
            final Object fieldValue = getter.invoke(bookingDto); // invoke the method for the given instance

            if (Objects.nonNull(fieldValue) && !fieldValue.equals("") && !fieldValue.equals(0)) {
                BeanUtils.setProperty(dbBooking, fieldName, fieldValue);
            }
        }
        repo.save(dbBooking);
        return dbBooking;
    }

}
