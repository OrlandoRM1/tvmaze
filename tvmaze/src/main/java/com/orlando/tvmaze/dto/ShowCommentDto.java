package com.orlando.tvmaze.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowCommentDto {
	private String comment;
	private Integer rating;
}