package com.orlando.tvmaze.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.orlando.tvmaze.dto.CommentRequestDto;
import com.orlando.tvmaze.dto.ShowResponseDto;
import com.orlando.tvmaze.dto.TvMazeSearchResponse;
import com.orlando.tvmaze.entity.CommentDocument;
import com.orlando.tvmaze.entity.ShowDocument;
import com.orlando.tvmaze.repository.ShowRepository;
import com.orlando.tvmaze.repository.CommentRepository;

@Service
public class TvMazeService {

	private final RestClient restClient;
	private final ShowRepository showRepository;
	private final CommentRepository commentRepository;

	public TvMazeService(RestClient restClient, ShowRepository showRepository, CommentRepository commentRepository) {
		this.restClient = restClient;
		this.showRepository = showRepository;
		this.commentRepository = commentRepository;
	}

	public List<ShowResponseDto> searchShows(String query) {
		List<TvMazeSearchResponse> rawResponse = restClient.get().uri("/search/shows?q={query}", query).retrieve()
				.body(new ParameterizedTypeReference<List<TvMazeSearchResponse>>() {
				});

		if (rawResponse == null) {
			return Collections.emptyList();
		}

		return rawResponse.stream().map(TvMazeSearchResponse::getShow).filter(show -> show != null)
				.map(show -> new ShowResponseDto(show.getId(), show.getName(), resolveChannelName(show),
						show.getSummary(), show.getGenres()))
				.toList();
	}

	public Map<String, Object> getShowById(Long showId) {
		Optional<ShowDocument> cachedShow = showRepository.findById(showId);
		if (cachedShow.isPresent()) {
			System.out.println("Show en Caché de MongoDB Atlas ID: " + showId);
			return cachedShow.get().getData();
		}

		System.out.println("No existe en caché. Consumiendo API TVMaze ID: " + showId);
		Map<String, Object> apiResponse = restClient.get().uri("/shows/{show_id}", showId).retrieve()
				.body(new ParameterizedTypeReference<Map<String, Object>>() {
				});

		if (apiResponse != null && !apiResponse.isEmpty()) {
			ShowDocument newShowDocument = ShowDocument.builder().id(showId).data(apiResponse).build();

			showRepository.save(newShowDocument);
			System.out.println("Show guardado en MongoDB Atlas con ID: " + showId);
		}

		return apiResponse;
	}

	public void saveComment(Long showId, CommentRequestDto dto) {
		if (dto.getRating() == null || dto.getRating() < 0 || dto.getRating() > 5) {
			throw new IllegalArgumentException("La calificacion (rating) debe ser un valor numérico entre 0 y 5.");
		}

		CommentDocument commentDocument = CommentDocument.builder().showId(showId).comment(dto.getComment())
				.rating(dto.getRating()).createdAt(LocalDateTime.now()).build();

		commentRepository.save(commentDocument);
	}

	private String resolveChannelName(TvMazeSearchResponse.ShowDto show) {
		if (show.getNetwork() != null && show.getNetwork().getName() != null) {
			return show.getNetwork().getName();
		}
		if (show.getWebChannel() != null && show.getWebChannel().getName() != null) {
			return show.getWebChannel().getName();
		}
		return "N/A";
	}
}
