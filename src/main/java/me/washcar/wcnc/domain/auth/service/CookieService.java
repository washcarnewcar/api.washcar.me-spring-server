package me.washcar.wcnc.domain.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;

@Service
public class CookieService {
	@Value("${cookie.domain}")
	private String domain;
	@Value("${cookie.max-age}")
	private int maxAge;

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
}
