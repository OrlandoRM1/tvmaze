package com.orlando.tvmaze.service;

import java.util.Collections;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.orlando.tvmaze.dto.ShowResponseDto;
import com.orlando.tvmaze.dto.TvMazeSearchResponse;

@Service
public class TvMazeService {
	private final RestClient restClient;

	public TvMazeService(RestClient restClient) {
		this.restClient = restClient;
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
