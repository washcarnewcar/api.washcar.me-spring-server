package me.washcar.wcnc.domain.member.controller;

import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.member.service.MemberService;
import me.washcar.wcnc.domain.member.dto.response.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v2/member")
@RequiredArgsConstructor
public class MemberController {

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
            @RequestParam("size") int size){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.getMemberList(page, size));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<MemberDto> getMemberByUuid(@PathVariable String uuid) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.getMemberByUuid(uuid));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<MemberDto> putMemberByUuid(@PathVariable String uuid) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.putMemberByUuid(uuid));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<MemberDto> deleteMemberByUuid(@PathVariable String uuid) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.deleteMemberByUuid(uuid));
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<MemberDto> patchMemberByUuid(@PathVariable String uuid) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.patchMemberByUuid(uuid));
    }

    @GetMapping("/me")
    public ResponseEntity<MemberDto> getMemberByJwt() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.getMemberByJwt());
    }

}
