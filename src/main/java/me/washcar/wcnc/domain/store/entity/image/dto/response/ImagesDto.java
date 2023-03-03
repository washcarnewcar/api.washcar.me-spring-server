package me.washcar.wcnc.domain.store.entity.image.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ImagesDto {

	private List<ImageResponseDto> images;
	
}
