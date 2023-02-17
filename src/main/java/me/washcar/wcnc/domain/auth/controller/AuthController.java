package me.washcar.wcnc.domain.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.auth.dto.request.LoginDto;
import me.washcar.wcnc.domain.auth.dto.request.SignupDto;
import me.washcar.wcnc.domain.auth.service.AuthService;
import me.washcar.wcnc.domain.auth.service.CookieService;
import me.washcar.wcnc.domain.member.Member;
import me.washcar.wcnc.domain.member.dto.response.MemberDto;
import me.washcar.wcnc.domain.member.service.MemberService;
import me.washcar.wcnc.global.definition.Regex;
import me.washcar.wcnc.global.definition.RegexMessage;
import me.washcar.wcnc.global.error.BusinessError;
import me.washcar.wcnc.global.error.BusinessException;

@Controller
@RequestMapping("/v2")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;
	private final CookieService cookieService;
	private final MemberService memberService;

	@PostMapping("/login")
	public ResponseEntity<MemberDto> login(HttpServletResponse response, @Valid @RequestBody LoginDto loginDto) {
		try {
			Member loginMember = authService.login(loginDto, response);
			MemberDto memberDto = new MemberDto(loginMember);
			return ResponseEntity.status(HttpStatus.OK).body(memberDto);
		} catch (AuthenticationException e) {
			// 비밀번호가 틀릴 시 401 반환
			throw new BusinessException(BusinessError.ID_PASSWORD_AUTH_FAILED);
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletResponse response) {
		response.addCookie(cookieService.deleteAccessTokenCookie());

		// 추후 refresh token을 사용할 때 활성화 할 것
		// response.addCookie(cookieService.deleteRefreshTokenCookie());

		return ResponseEntity.ok().build();
	}

	@GetMapping("/members/login-id")
	public ResponseEntity<Void> checkMemberId(
		@Valid @RequestParam("login-id") @Pattern(regexp = Regex.MEMBER_ID, message = RegexMessage.MEMBER_ID) String loginId) {
		authService.checkMemberId(loginId);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/members/telephone")
	public ResponseEntity<Void> checkTel(
		@Valid @RequestParam @NotNull @Pattern(regexp = Regex.TELEPHONE, message = RegexMessage.TELEPHONE) String telephone) {
		authService.checkTelephone(telephone);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/members/me")
	public ResponseEntity<MemberDto> getMemberByJwt(@AuthenticationPrincipal String uuid) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(memberService.getMemberByUuid(uuid));
	}

	@PostMapping("/signup")
	public ResponseEntity<Void> signup(@Valid @RequestBody SignupDto signupDto) {
		authService.signup(signupDto);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/pin")
	public void pin() {
	}

	@PostMapping("/telephone-login")
	public void telephoneLogin() {
	}
}
