package com.example.demo.controller;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Booking;
import com.example.demo.model.BookingDto;
import com.example.demo.repository.BookingRepository;
import com.example.demo.service.BookingService;
import com.example.demo.utils.Response;

@RestController
@CrossOrigin("*")
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingService service;

    @Autowired
    private ModelMapper mapper;

    final int PAGE_SIZE = 10;

    final boolean DEBUG = true;

    // get list
    @RequestMapping("/list")
    public Response list(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false) String by,
            @RequestParam(required = false) String value) {
        Pageable paging = PageRequest.of(page, PAGE_SIZE);
        Page<Booking> pagedResult;
        try {
            pagedResult = bookingRepository.findBy(paging, by, value);
        } catch (Exception e) {
            if (DEBUG)
                return new Response(false, e.getMessage(), null);
            return new Response(false, "Error", null);
        }

        return new Response(true, "Success", pagedResult);
    }

    // get item
    @RequestMapping("/{id}")
    public Response getItem(@PathVariable(value = "id") String id) {
        Booking booking;
        try {
            booking = bookingRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            return new Response(false, "Booking not found", null);
        } catch (Exception e) {
            if (DEBUG)
                return new Response(false, e.getMessage(), null);
            return new Response(false, "Booking not found", null);
        }
        return new Response(true, "", booking);
    }

    // add
    @PostMapping("/create")
    public Response createItem(@RequestBody BookingDto booking) {
        Booking saved;
        try {
            Booking bookingEntity = mapper.map(booking, Booking.class);
            saved = bookingRepository.save(bookingEntity);
        } catch (NoSuchElementException e) {
            return new Response(false, "Booking not found", null);
        } catch (Exception e) {
            if (DEBUG)
                return new Response(false, e.getMessage(), null);
            return new Response(false, e.getMessage(), null);
        }

        return new Response(true, "Booking created", saved);
    }

    // delete
    @DeleteMapping("/{id}")
    public Response deleteItem(@PathVariable(value = "id") String id) {
        try {
            bookingRepository.deleteById(id);
        } catch (NoSuchElementException e) {
            return new Response(false, "Booking not found", null);
        } catch (Exception e) {
            if (DEBUG)
                return new Response(false, e.getMessage(), null);
            return new Response(false, e.getMessage(), null);
        }
        return new Response(true, "Booking deleted successfully", null);
    }

    // edit
    @PutMapping("/{id}")
    public Response updateItem(@PathVariable("id") String id, @RequestBody BookingDto bookingDto) {
        Booking dbBooking;
        try {
            if (!Objects.equals(id, bookingDto.getId()))
                throw new IllegalArgumentException("Url Id and body Id must be the same");

            dbBooking = service.updateBooking(id, bookingDto);

        } catch (NoSuchElementException e) {
            return new Response(false, "Booking not found", null);
        } catch (IllegalArgumentException e) {
            return new Response(false, e.getMessage(), null);
        } catch (Exception e) {
            if (DEBUG)
                return new Response(false, e.toString(), null);
            return new Response(false, "An error occurred during the update.", null);
        }

        return new Response(true, "", dbBooking);

    }

}