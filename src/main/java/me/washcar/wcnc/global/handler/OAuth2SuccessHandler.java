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
import me.washcar.wcnc.domain.auth.adapter.AuthenticationAdapter;
import me.washcar.wcnc.domain.auth.service.CookieService;
import me.washcar.wcnc.domain.member.MemberRole;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
	@Value("${front-server.domain}")
	private String frontServer;
	private final CookieService cookieService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {

		AuthenticationAdapter authenticationAdapter = (AuthenticationAdapter)authentication.getPrincipal();

		String uuid = authenticationAdapter.getUuid();

		MemberRole memberRole = authenticationAdapter.getMemberRole();

		cookieService.authenticate(uuid, memberRole, response);

		String url = UriComponentsBuilder.fromUriString(frontServer)
			.path("/")
			.build()
			.toUriString();
		response.sendRedirect(url);
	}
}
