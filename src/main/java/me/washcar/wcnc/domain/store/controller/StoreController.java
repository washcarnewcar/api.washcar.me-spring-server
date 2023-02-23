package me.washcar.wcnc.domain.store.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.store.StoreStatus;
import me.washcar.wcnc.domain.store.dto.request.StorePatchRequestDto;
import me.washcar.wcnc.domain.store.dto.request.StoreRequestDto;
import me.washcar.wcnc.domain.store.dto.response.StoreDto;
import me.washcar.wcnc.domain.store.service.StoreService;
import me.washcar.wcnc.global.definition.Regex;
import me.washcar.wcnc.global.definition.RegexMessage;

@RestController
@RequestMapping("/v2/store")
@RequiredArgsConstructor
@Validated
public class StoreController {

	private final StoreService storeService;

	@PostMapping
	public ResponseEntity<Void> postStore(
		@RequestBody @Valid StoreRequestDto storeRequestDto, @AuthenticationPrincipal String uuid) {
		storeService.postStore(storeRequestDto, uuid);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.build();
	}

	@GetMapping
	public ResponseEntity<Page<StoreDto>> getStoreList(
		@RequestParam("page") int page,
		@RequestParam("size") int size) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(storeService.getStoreList(page, size));
	}

	@GetMapping("/{slug}")
	public ResponseEntity<StoreDto> getStoreBySlug(
		@PathVariable @Pattern(regexp = Regex.SLUG, message = RegexMessage.SLUG) String slug) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(storeService.getStoreBySlug(slug));
	}

	@PutMapping("/{slug}")
	public ResponseEntity<StoreDto> putStoreBySlug(
		@PathVariable @Pattern(regexp = Regex.SLUG, message = RegexMessage.SLUG) String slug,
		@RequestBody StoreRequestDto storeRequestDto) {
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(storeService.putStoreBySlug(slug, storeRequestDto));
	}

	@DeleteMapping("/{slug}")
	public ResponseEntity<Void> deleteStoreBySlug(
		@PathVariable @Pattern(regexp = Regex.SLUG, message = RegexMessage.SLUG) String slug) {
		storeService.deleteStoreBySlug(slug);
		return ResponseEntity
			.status(HttpStatus.NO_CONTENT)
			.build();
	}

	@PatchMapping("/{slug}")
	public ResponseEntity<Void> patchStoreBySlug(
		@PathVariable @Pattern(regexp = Regex.SLUG, message = RegexMessage.SLUG) String slug,
		@RequestBody StorePatchRequestDto storePatchRequestDto) {
		StoreStatus storeStatus = storePatchRequestDto.getStoreStatus();
		storeService.changeStoreStatusBySlug(slug, storeStatus);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.build();
	}

}
