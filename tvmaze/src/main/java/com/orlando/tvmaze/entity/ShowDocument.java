package com.orlando.tvmaze.entity;

import java.util.Map;

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
@Document(collection = "shows")
public class ShowDocument {

	@Id
	private Long id;

	// Almacena dinámicamente TVMaze
	private Map<String, Object> data;

}
