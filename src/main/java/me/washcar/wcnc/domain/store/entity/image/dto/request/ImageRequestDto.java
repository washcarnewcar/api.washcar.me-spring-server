package me.washcar.wcnc.domain.store.entity.image.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("unused")
public class ImageRequestDto {

	@NotBlank
	private String imageUrl;

}
