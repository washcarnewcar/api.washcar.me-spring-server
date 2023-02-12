package me.washcar.wcnc.domain.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.member.Member;

@Service
@RequiredArgsConstructor
public class CookieService {
	@Value("${cookie.domain}")
	private String domain;
	@Value("${cookie.max-age}")
	private int maxAge;
	private final JwtService jwtService;

	public Cookie makeAccessTokenCookie(String token) {
		Cookie cookie = new Cookie("access_token", token);
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		cookie.setDomain(domain);
		cookie.setMaxAge(maxAge);
		return cookie;
	}

	public Cookie makeRefreshTokenCookie(String token) {
		Cookie cookie = new Cookie("refresh_token", token);
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		cookie.setDomain(domain);
		cookie.setMaxAge(maxAge);
		return cookie;
	}

	public Cookie deleteAccessTokenCookie() {
		Cookie cookie = new Cookie("access_token", null);
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		cookie.setDomain(domain);
		cookie.setMaxAge(0);
		return cookie;
	}

	public Cookie deleteRefreshTokenCookie() {
		Cookie cookie = new Cookie("refresh_token", null);
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		cookie.setDomain(domain);
		cookie.setMaxAge(0);
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
