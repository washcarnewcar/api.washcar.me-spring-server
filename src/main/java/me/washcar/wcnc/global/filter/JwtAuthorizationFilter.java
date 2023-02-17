package me.washcar.wcnc.global.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.washcar.wcnc.domain.auth.service.JwtService;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		// 쿠키에서 토큰 파싱
		String token = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("access_token")) {
					token = cookie.getValue();
					break;
				}
			}
		}

		try {
			// 토큰 있을 때 인증 수행
			if (token != null) {
				Claims claims = jwtService.extractClaims(token);
				String uuid = claims.getSubject();
				String role = (String)claims.get("role");
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					uuid, null, List.of(new SimpleGrantedAuthority(role)));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		} catch (RuntimeException e) {
			log.warn(e.getMessage());
		} finally {
			filterChain.doFilter(request, response);
		}
	}
}
