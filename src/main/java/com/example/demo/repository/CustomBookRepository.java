package com.example.demo.repository;

public interface CustomBookRepository {

    public void partialUpdate(final String bookId, final String fieldName, final Object fieldValue);
}
