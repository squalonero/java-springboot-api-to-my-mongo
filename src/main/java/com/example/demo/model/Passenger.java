package com.example.demo.model;


import java.util.Date;

import javax.persistence.Entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Document("user")
@Getter
@Setter
@NoArgsConstructor
public class Passenger {
    public String name;
    public String lastName;
    public Date birthDate;
}
