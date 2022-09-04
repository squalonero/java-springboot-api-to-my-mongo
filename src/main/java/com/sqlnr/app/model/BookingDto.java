package com.sqlnr.app.model;


import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.annotation.Id;

import com.sqlnr.app.model.types.BookingStatus;

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

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BookingStatus getStatus() {
        return this.status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNumPeople() {
        return this.numPeople;
    }

    public void setNumPeople(int numPeople) {
        this.numPeople = numPeople;
    }

    public ArrayList<Passenger> getPassengers() {
        return this.passengers;
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }

}
