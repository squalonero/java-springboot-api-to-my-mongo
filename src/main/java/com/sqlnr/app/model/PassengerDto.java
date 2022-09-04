package com.sqlnr.app.model;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PassengerDto {
    public String name;
    public String lastName;
    public Date birthDate;
}
