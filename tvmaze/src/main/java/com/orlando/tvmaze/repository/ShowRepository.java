package com.orlando.tvmaze.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.orlando.tvmaze.entity.ShowDocument;

@Repository
public interface ShowRepository extends MongoRepository<ShowDocument, Long> {

}
