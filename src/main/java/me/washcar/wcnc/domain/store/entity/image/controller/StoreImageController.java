package me.washcar.wcnc.domain.store.entity.image.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.store.entity.image.dto.request.ImageRequestDto;
import me.washcar.wcnc.domain.store.entity.image.dto.response.ImageResponseDto;
import me.washcar.wcnc.domain.store.entity.image.dto.response.ImagesDto;
import me.washcar.wcnc.domain.store.entity.image.service.StoreImageService;
import me.washcar.wcnc.global.definition.Regex;
import me.washcar.wcnc.global.definition.RegexMessage;

@RestController
@RequestMapping("/v2")
@RequiredArgsConstructor
public class StoreImageController {

	private final StoreImageService storeImageService;

	@PostMapping("/store/{slug}/image")
	public ResponseEntity<Void> create(
		@PathVariable @Pattern(regexp = Regex.SLUG, message = RegexMessage.SLUG) String slug, @RequestBody @Valid
	ImageRequestDto requestDto) {
		storeImageService.create(slug, requestDto.getImageUrl());
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.build();
	}

	@GetMapping("/store/{slug}/image")
	public ResponseEntity<ImagesDto> getImagesBySlug(
		@PathVariable @Pattern(regexp = Regex.SLUG, message = RegexMessage.SLUG) String slug) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(storeImageService.getImagesBySlug(slug));
	}

	@GetMapping("/image/{uuid}")
	public ResponseEntity<ImageResponseDto> getImageByUuid(
		@PathVariable @Pattern(regexp = Regex.UUID_V4, message = RegexMessage.UUID_V4) String uuid) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(storeImageService.getImageByUuid(uuid));
	}

	@DeleteMapping("/image/{uuid}")
	public ResponseEntity<Void> deleteImageByUuid(
		@PathVariable @Pattern(regexp = Regex.UUID_V4, message = RegexMessage.UUID_V4) String uuid) {
		storeImageService.deleteImageByUuid(uuid);
		return ResponseEntity
			.status(HttpStatus.NO_CONTENT)
			.build();
	}

}
