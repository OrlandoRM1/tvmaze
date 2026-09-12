package com.orlando.tvmaze.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "comments")
public class CommentDocument {

	@Id
	private String id;
	private Long showId;
	private String comment;
	private Integer rating;
	private LocalDateTime createdAt;

}
