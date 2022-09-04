package com.sqlnr.app.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sqlnr.app.model.Booking;
import com.sqlnr.app.model.BookingDto;
import com.sqlnr.app.repository.BookingRepository;

@Service
public class BookingService {

    @Autowired // Allows Spring to resolve and inject this class automatically
    private BookingRepository repo;

    final int PAGE_SIZE = 10;

    public Page<Booking> findBy(int page, String by, String value) {
        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE);

        if (!StringUtils.hasLength(by) || !StringUtils.hasLength(value)) {
            return repo.findAll(pageable);
        }

        return repo.findBy(pageable, by, value);
    }

    public Booking findById(String id)
    {
        return repo.findById(id).orElse(null);
    }

    public Booking save(Booking booking)
    {
        /**
         * @todo: server side validation
         */
        return repo.save(booking);
    }

    public void deleteById(String id)
    {
        repo.deleteById(id);
    }

    public void deleteAll()
    {
        repo.deleteAll();
    }

    public Booking updateBooking(String id, BookingDto bookingDto) throws Exception{

        //@todo: server side validation

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
