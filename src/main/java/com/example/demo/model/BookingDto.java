package com.example.demo.model;


import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.annotation.Id;

import com.example.demo.model.types.BookingStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookingDto {
    @Id
    public String id;
    public User user; // todo
    public BookingStatus status;
    public Date date;
    public int numPeople;
    public ArrayList<Passenger> passengers; // todo

}
