package com.orlando.tvmaze.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowResponseDto {
	private Long id;
	private String name;
	private String channel;
	private String summary;
	private List<String> genres;

}
