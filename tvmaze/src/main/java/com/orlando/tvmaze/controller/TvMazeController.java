package com.orlando.tvmaze.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orlando.tvmaze.dto.CommentRequestDto;
import com.orlando.tvmaze.dto.ShowResponseDto;
import com.orlando.tvmaze.service.TvMazeService;

@RestController
@RequestMapping("/api/shows")
public class TvMazeController {
	private final TvMazeService tvMazeService;

	public TvMazeController(TvMazeService tvMazeService) {
		this.tvMazeService = tvMazeService;
	}

	@GetMapping("/search")
	public ResponseEntity<List<ShowResponseDto>> search(@RequestParam(name = "search_query") String searchQuery) {
		List<ShowResponseDto> shows = tvMazeService.searchShows(searchQuery);
		return ResponseEntity.ok(shows);
	}

	@GetMapping("/{show_id}")
	public ResponseEntity<Map<String, Object>> getShowById(@PathVariable(name = "show_id") Long showId) {
		Map<String, Object> show = tvMazeService.getShowById(showId);
		return ResponseEntity.ok(show);
	}

	@PostMapping("/{show_id}/comments")
	public ResponseEntity<Map<String, String>> addComment(@PathVariable(name = "show_id") Long showId,
			@RequestBody CommentRequestDto commentRequest) {
		tvMazeService.saveComment(showId, commentRequest);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(Map.of("status", "Comentario y calificacion registrados exitosamente"));
	}
}
