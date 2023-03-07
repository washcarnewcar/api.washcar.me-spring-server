package me.washcar.wcnc.domain.store.entity.operation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.store.entity.operation.dto.request.OperationHourRequestDto;
import me.washcar.wcnc.domain.store.entity.operation.dto.response.OperationHourDto;
import me.washcar.wcnc.domain.store.entity.operation.service.StoreOperationHourService;
import me.washcar.wcnc.global.definition.Regex;
import me.washcar.wcnc.global.definition.RegexMessage;

@RestController
@RequestMapping("/v2")
@RequiredArgsConstructor
public class StoreOperationHourController {

	private final StoreOperationHourService storeOperationHourService;

	@GetMapping("/store/{slug}/time")
	public ResponseEntity<OperationHourDto> getOperationHourBySlug(
		@PathVariable @Pattern(regexp = Regex.SLUG, message = RegexMessage.SLUG) String slug) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(storeOperationHourService.getOperationHourBySlug(slug));
	}

	@PutMapping("/store/{slug}/time")
	public ResponseEntity<Void> putOperationHourBySlug(
		@PathVariable @Pattern(regexp = Regex.SLUG, message = RegexMessage.SLUG) String slug,
		@RequestBody @Valid OperationHourRequestDto requestDto) {
		storeOperationHourService.putOperationHourBySlug(slug, requestDto);
		return ResponseEntity
			.status(HttpStatus.OK)
			.build();
	}

}
