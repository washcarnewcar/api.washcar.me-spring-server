package me.washcar.wcnc.domain.member.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.member.MemberStatus;
import me.washcar.wcnc.domain.member.dto.request.MemberPatchRequestDto;
import me.washcar.wcnc.domain.member.dto.response.MemberDto;
import me.washcar.wcnc.domain.member.service.MemberService;
import me.washcar.wcnc.global.definition.Regex;
import me.washcar.wcnc.global.definition.RegexMessage;

@RestController
@RequestMapping("/v2/member")
@RequiredArgsConstructor
@Validated
public class MemberController {

	private final MemberService memberService;

	@GetMapping
	public ResponseEntity<Page<MemberDto>> getMemberList(
		@RequestParam("page") int page,
		@RequestParam("size") int size) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(memberService.getMemberList(page, size));
	}

	@GetMapping("/{uuid}")
	public ResponseEntity<MemberDto> getMemberByUuid(
		@PathVariable @Pattern(regexp = Regex.UUID_V4, message = RegexMessage.UUID_V4) String uuid) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(memberService.getMemberByUuid(uuid));
	}

	@PutMapping("/{uuid}")
	public ResponseEntity<MemberDto> putMemberByUuid(
		@PathVariable @Pattern(regexp = Regex.UUID_V4, message = RegexMessage.UUID_V4) String uuid) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(memberService.putMemberByUuid(uuid));
	}

	@DeleteMapping("/{uuid}")
	public ResponseEntity<Void> deleteMemberByUuid(
		@PathVariable @Pattern(regexp = Regex.UUID_V4, message = RegexMessage.UUID_V4) String uuid) {
		memberService.deleteMemberByUuid(uuid);
		return ResponseEntity
			.status(HttpStatus.NO_CONTENT)
			.build();
	}

	@PatchMapping("/{uuid}")
	public ResponseEntity<Void> patchMemberByUuid(
		@PathVariable @Pattern(regexp = Regex.UUID_V4, message = RegexMessage.UUID_V4) String uuid,
		@RequestBody MemberPatchRequestDto memberPatchRequestDto) {
		MemberStatus memberStatus = memberPatchRequestDto.getMemberStatus();
		memberService.changeMemberStatusByUuid(uuid, memberStatus);
		return ResponseEntity
			.status(HttpStatus.NO_CONTENT)
			.build();
	}
}
