package me.washcar.wcnc.domain.store.entity.menu.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuRequestDto {

	@NotBlank
	private int price;

	@NotBlank
	private int expectedMinute;

	private String description;

	private String image;

}
