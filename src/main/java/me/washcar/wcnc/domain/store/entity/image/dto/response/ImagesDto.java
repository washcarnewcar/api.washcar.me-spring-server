package me.washcar.wcnc.domain.store.entity.image.dto.response;

import java.util.List;

import lombok.Getter;

@Getter
public class ImagesDto {

	private final List<ImageResponseDto> images;

	public ImagesDto(List<ImageResponseDto> images) {
		this.images = images;
	}

}
