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
import me.washcar.wcnc.domain.store.entity.menu.dto.response.MenuDto;
import me.washcar.wcnc.domain.store.entity.menu.dto.response.MenuListDto;
import me.washcar.wcnc.domain.store.entity.menu.service.MenuService;
import me.washcar.wcnc.global.definition.Regex;
import me.washcar.wcnc.global.definition.RegexMessage;

@RestController
@RequestMapping("/v2")
@RequiredArgsConstructor
public class MenuController {

	private final MenuService menuService;

	@PostMapping("/store/{slug}/menu")
	public ResponseEntity<Void> create(
		@PathVariable @Pattern(regexp = Regex.SLUG, message = RegexMessage.SLUG) String slug,
		@RequestBody @Valid MenuRequestDto requestDto) {
		menuService.create(slug, requestDto);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.build();
	}

	@GetMapping("/store/{slug}/menu")
	public ResponseEntity<MenuListDto> getMenusBySlug(
		@PathVariable @Pattern(regexp = Regex.SLUG, message = RegexMessage.SLUG) String slug) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(menuService.getMenusBySlug(slug));
	}

	@GetMapping("/menu/{uuid}")
	public ResponseEntity<MenuDto> getMenuByUuid(
		@PathVariable @Pattern(regexp = Regex.UUID_V4, message = RegexMessage.UUID_V4) String uuid) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(menuService.getMenuByUuid(uuid));
	}

	@PutMapping("/menu/{uuid}")
	public ResponseEntity<Void> putMenuByUuid(
		@PathVariable @Pattern(regexp = Regex.UUID_V4, message = RegexMessage.UUID_V4) String uuid,
		@RequestBody @Valid MenuRequestDto requestDto) {
		menuService.putMenuByUuid(uuid, requestDto);
		return ResponseEntity
			.status(HttpStatus.OK)
			.build();
	}

	@DeleteMapping("/menu/{uuid}")
	public ResponseEntity<Void> deleteMenuByUuid(
		@PathVariable @Pattern(regexp = Regex.UUID_V4, message = RegexMessage.UUID_V4) String uuid) {
		menuService.deleteMenuByUuid(uuid);
		return ResponseEntity
			.status(HttpStatus.NO_CONTENT)
			.build();
	}

}
