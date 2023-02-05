package me.washcar.wcnc.domain.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.auth.dto.request.LoginDto;
import me.washcar.wcnc.domain.auth.service.AuthService;
import me.washcar.wcnc.domain.auth.service.CookieService;
import me.washcar.wcnc.domain.auth.service.JwtService;
import me.washcar.wcnc.domain.member.Member;
import me.washcar.wcnc.domain.member.dto.response.MemberDto;

@Controller
@RequestMapping("/v2")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;
	private final CookieService cookieService;
	private final JwtService jwtService;

	@PostMapping("/login")
	public ResponseEntity<MemberDto> login(HttpServletResponse response, @RequestBody LoginDto loginDto) {
		try {
			Member loginMember = authService.authenticate(loginDto);
			MemberDto memberDto = new MemberDto(loginMember);
			String accessToken = jwtService.generateAccessToken(loginMember);
			response.addCookie(cookieService.makeAccessTokenCookie(accessToken));

			// 당장은 refresh token이 필요없다고 판단됨. 일단 access token만 구현
			// String refreshToken = jwtService.generateRefreshToken(loginMember);
			// response.addCookie(cookieService.makeRefreshTokenCookie(refreshToken));

			return ResponseEntity.status(HttpStatus.OK).body(memberDto);
		} catch (AuthenticationException e) {
			// 비밀번호가 틀릴 시 401 반환
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletResponse response) {
		response.addCookie(cookieService.deleteAccessTokenCookie());

		// response.addCookie(cookieService.deleteRefreshTokenCookie());

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping("/pin")
	public void pin() {
	}

	@PostMapping("/telephone-login")
	public void telephoneLogin() {
	}
}
