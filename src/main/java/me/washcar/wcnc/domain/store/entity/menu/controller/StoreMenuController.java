package me.washcar.wcnc.domain.store.entity.menu.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.store.entity.menu.dto.request.MenuRequestDto;
import me.washcar.wcnc.domain.store.entity.menu.dto.response.MenuResponseDto;
import me.washcar.wcnc.domain.store.entity.menu.dto.response.MenusDto;
import me.washcar.wcnc.domain.store.entity.menu.service.StoreMenuService;
import me.washcar.wcnc.global.definition.Regex;
import me.washcar.wcnc.global.definition.RegexMessage;

@RestController
@RequestMapping("/v2")
@RequiredArgsConstructor
public class StoreMenuController {

	private final StoreMenuService storeMenuService;

	@PostMapping("/store/{slug}/menu")
	public ResponseEntity<Void> create(
		@PathVariable @Pattern(regexp = Regex.SLUG, message = RegexMessage.SLUG) String slug,
		@RequestBody @Valid MenuRequestDto requestDto) {
		storeMenuService.create(slug, requestDto);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.build();
	}

	@GetMapping("/store/{slug}/menu")
	public ResponseEntity<MenusDto> getMenusBySlug(
		@PathVariable @Pattern(regexp = Regex.SLUG, message = RegexMessage.SLUG) String slug) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(storeMenuService.getMenusBySlug(slug));
	}

	@GetMapping("/menu/{uuid}")
	public ResponseEntity<MenuResponseDto> getMenuByUuid(
		@PathVariable @Pattern(regexp = Regex.UUID_V4, message = RegexMessage.UUID_V4) String uuid) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(storeMenuService.getMenuByUuid(uuid));
	}

	@PutMapping("/menu/{uuid}")
	public ResponseEntity<Void> putMenuByUuid(
		@PathVariable @Pattern(regexp = Regex.UUID_V4, message = RegexMessage.UUID_V4) String uuid,
		@RequestBody @Valid MenuRequestDto requestDto) {
		storeMenuService.putMenuByUuid(uuid, requestDto);
		return ResponseEntity
			.status(HttpStatus.OK)
			.build();
	}

	@DeleteMapping("/menu/{uuid}")
	public ResponseEntity<Void> deleteMenuByUuid(
		@PathVariable @Pattern(regexp = Regex.UUID_V4, message = RegexMessage.UUID_V4) String uuid) {
		storeMenuService.deleteMenuByUuid(uuid);
		return ResponseEntity
			.status(HttpStatus.NO_CONTENT)
			.build();
	}

}
