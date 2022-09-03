package com.example.demo.model;

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
public class User {
    public String email;
    public String phone;
}
