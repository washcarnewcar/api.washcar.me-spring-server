package me.washcar.wcnc.domain.search.slug.dto.response;

import java.util.List;

import lombok.Getter;

@Getter
public class SlugsDto {

	private final List<SlugResponseDto> slugs;

	public SlugsDto(List<SlugResponseDto> slugs) {
		this.slugs = slugs;
	}

}
