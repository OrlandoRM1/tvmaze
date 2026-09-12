package com.orlando.tvmaze.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TvMazeSearchResponse {
	private ShowDto show;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class ShowDto {
		private Long id;
		private String name;
		private String summary;
		private List<String> genres;
		private NetworkDto network;

		@JsonProperty("webChannel")
		private WebChannelDto webChannel;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class NetworkDto {
		private String name;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class WebChannelDto {
		private String name;
	}
}
