package com.example.demo.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
// @important MongoRepository includes standard CRUD operations
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Booking;
import com.example.demo.model.types.BookingStatus;


@Repository
public interface BookingRepository extends MongoRepository<Booking, String> {

    @Query("{?0; ?1}")
    public Page<Booking> findBy(Pageable pageable, String by, String value);

    @Query("{date:'?0'}") // query annotation marks this method as a query method with required parameter
    public Booking findByDate(Date date);

    @Query("{status:'?0'}")
    public List<Booking> findByStatus(BookingStatus status);

    public long count();

    // @Query("{genres:{$in:['?0']}}")
    // public List<Booking> findAll(String genre);

    //todo: findbyuser, findby people name, findby people id, findby people email, findby people phone

}
