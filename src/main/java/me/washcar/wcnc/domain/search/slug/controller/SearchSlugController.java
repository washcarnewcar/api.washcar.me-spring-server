package me.washcar.wcnc.domain.search.slug.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.search.slug.dto.response.SlugsDto;
import me.washcar.wcnc.domain.search.slug.service.SearchSlugService;

@Controller
@RequestMapping("/v2/search/slugs")
@RequiredArgsConstructor
public class SearchSlugController {

	private final SearchSlugService searchSlugService;

	@GetMapping("/me")
	public ResponseEntity<?> getMyStoreSlugs(@AuthenticationPrincipal String uuid) {
		SlugsDto slugsDto = searchSlugService.getSlugsByOwnerUuid(uuid);
		if (slugsDto.getSlugs().isEmpty()) {
			return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.build();
		}
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(slugsDto);
	}

}
