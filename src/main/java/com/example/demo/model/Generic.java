package com.example.demo.model;

public class Generic<T> {

    // An object of type T is declared
    T obj;

    public Generic(T obj) {
        this.obj = obj;
    } // constructor

    public T getObject() {
        return this.obj;
    }

}
