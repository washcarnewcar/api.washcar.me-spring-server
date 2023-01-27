package me.washcar.wcnc.domain.member.controller;

import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.member.MemberStatus;
import me.washcar.wcnc.domain.member.dto.request.MemberPatchRequestDto;
import me.washcar.wcnc.domain.member.service.MemberService;
import me.washcar.wcnc.domain.member.dto.response.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v2/member")
@RequiredArgsConstructor
@Validated
public class MemberController {

    private static final String REGEXP_UUID_V4 = "^[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$";

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Void> postMember(){
        memberService.postMember();
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping
    public ResponseEntity<Page<MemberDto>> getMemberList(
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.getMemberList(page, size));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<MemberDto> getMemberByUuid(@PathVariable @Pattern(regexp = REGEXP_UUID_V4) String uuid) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.getMemberByUuid(uuid));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<MemberDto> putMemberByUuid(@PathVariable @Pattern(regexp = REGEXP_UUID_V4) String uuid) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.putMemberByUuid(uuid));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteMemberByUuid(@PathVariable @Pattern(regexp = REGEXP_UUID_V4) String uuid) {
        memberService.deleteMemberByUuid(uuid);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<Void> patchMemberByUuid(
            @PathVariable @Pattern(regexp = REGEXP_UUID_V4) String uuid,
            @RequestBody MemberPatchRequestDto memberPatchRequestDto) {
        MemberStatus memberStatus = memberPatchRequestDto.getMemberStatus();
        memberService.changeMemberStatusByUuid(uuid, memberStatus);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/me")
    public ResponseEntity<MemberDto> getMemberByJwt() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.getMemberByJwt());
    }

}
