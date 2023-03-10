package me.washcar.wcnc.domain.store.entity.operation.controller;

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
import me.washcar.wcnc.domain.store.entity.operation.dto.request.HolidayRequestDto;
import me.washcar.wcnc.domain.store.entity.operation.dto.response.HolidayDto;
import me.washcar.wcnc.domain.store.entity.operation.dto.response.HolidayListDto;
import me.washcar.wcnc.domain.store.entity.operation.service.StoreOperationHolidayService;
import me.washcar.wcnc.global.definition.Regex;
import me.washcar.wcnc.global.definition.RegexMessage;

@RestController
@RequestMapping("/v2")
@RequiredArgsConstructor
public class StoreOperationHolidayController {

	private final StoreOperationHolidayService storeOperationHolidayService;

	@PostMapping("/store/{slug}/holiday")
	public ResponseEntity<Void> create(
		@PathVariable @Pattern(regexp = Regex.SLUG, message = RegexMessage.SLUG) String slug,
		@RequestBody @Valid HolidayRequestDto requestDto) {
		storeOperationHolidayService.create(slug, requestDto);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.build();
	}

	@GetMapping("/store/{slug}/holiday")
	public ResponseEntity<HolidayListDto> getHolidaysBySlug(
		@PathVariable @Pattern(regexp = Regex.SLUG, message = RegexMessage.SLUG) String slug) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(storeOperationHolidayService.getEveryHolidayBySlug(slug));
	}

	@GetMapping("/holiday/{uuid}")
	public ResponseEntity<HolidayDto> getHolidayByUuid(
		@PathVariable @Pattern(regexp = Regex.UUID_V4, message = RegexMessage.UUID_V4) String uuid) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(storeOperationHolidayService.getHolidayByUuid(uuid));
	}

	@PutMapping("/holiday/{uuid}")
	public ResponseEntity<Void> putHolidayByUuid(
		@PathVariable @Pattern(regexp = Regex.UUID_V4, message = RegexMessage.UUID_V4) String uuid,
		@RequestBody @Valid HolidayRequestDto requestDto) {
		storeOperationHolidayService.putHolidayByUuid(uuid, requestDto);
		return ResponseEntity
			.status(HttpStatus.OK)
			.build();
	}

	@DeleteMapping("/holiday/{uuid}")
	public ResponseEntity<Void> deleteHolidayByUuid(
		@PathVariable @Pattern(regexp = Regex.UUID_V4, message = RegexMessage.UUID_V4) String uuid) {
		storeOperationHolidayService.deleteHolidayByUuid(uuid);
		return ResponseEntity
			.status(HttpStatus.NO_CONTENT)
			.build();
	}

}
