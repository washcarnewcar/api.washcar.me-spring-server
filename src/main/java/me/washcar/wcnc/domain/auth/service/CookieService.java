package me.washcar.wcnc.domain.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.member.entity.Member;

@Service
@RequiredArgsConstructor
public class CookieService {
	@Value("${cookie.domain}")
	private String domain;
	@Value("${cookie.max-age}")
	private int maxAge;
	private final JwtService jwtService;

	public Cookie makeAccessTokenCookie(String token) {
		return makeCookie("access_token", token, maxAge);
	}

	public Cookie makeRefreshTokenCookie(String token) {
		return makeCookie("refresh_token", token, maxAge);
	}

	public Cookie deleteAccessTokenCookie() {
		return makeCookie("access_token", null, 0);
	}

	public Cookie deleteRefreshTokenCookie() {
		return makeCookie("refresh_token", null, 0);
	}

	private Cookie makeCookie(String key, String value, int maxAge) {
		Cookie cookie = new Cookie(key, value);
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		cookie.setDomain(domain);
		cookie.setMaxAge(maxAge);
		return cookie;

	}

	public void authenticate(Member member, HttpServletResponse response) {
		String accessToken = jwtService.generateAccessToken(member);
		response.addCookie(this.makeAccessTokenCookie(accessToken));
		// 당장은 refresh token이 필요없다고 판단됨. 일단 access token만 구현
		// String refreshToken = jwtService.generateRefreshToken(loginMember);
		// response.addCookie(cookieService.makeRefreshTokenCookie(refreshToken));
	}
}
