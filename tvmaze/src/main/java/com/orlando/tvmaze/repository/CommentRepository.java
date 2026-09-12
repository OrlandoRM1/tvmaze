package com.orlando.tvmaze.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.orlando.tvmaze.entity.CommentDocument;

@Repository
public interface CommentRepository extends MongoRepository<CommentDocument, String> {

	List<CommentDocument> findByShowId(Long showId);

}
