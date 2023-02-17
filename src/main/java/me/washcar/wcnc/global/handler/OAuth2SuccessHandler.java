package me.washcar.wcnc.global.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.auth.OAuth2Member;
import me.washcar.wcnc.domain.auth.service.CookieService;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
	@Value("${front-server.domain}")
	private String frontServer;
	private final CookieService cookieService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {

		OAuth2Member oAuth2Member = (OAuth2Member)authentication.getPrincipal();

		cookieService.authenticate(oAuth2Member.getMember(), response);

		String url = UriComponentsBuilder.fromUriString(frontServer)
			.path("/")
			.build()
			.toUriString();
		response.sendRedirect(url);
	}
}
