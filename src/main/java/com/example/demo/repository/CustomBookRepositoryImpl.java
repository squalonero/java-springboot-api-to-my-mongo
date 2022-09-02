package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.BasicUpdate;
import org.springframework.data.mongodb.core.query.Criteria;

import com.example.demo.model.Book;

public class CustomBookRepositoryImpl implements CustomBookRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void partialUpdate(String bookId, String fieldName, Object fieldValue) {
        mongoTemplate.findAndModify(BasicQuery.query(Criteria.where("id").is(bookId)),
                BasicUpdate.update(fieldName, fieldValue), FindAndModifyOptions.none(), Book.class);

    }

}
