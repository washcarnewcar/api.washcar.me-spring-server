package me.washcar.wcnc.global.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2FailureHandler implements AuthenticationFailureHandler {

	@Value("${front-server.domain}")
	private String frontServer;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException, ServletException {

		String url = UriComponentsBuilder.fromUriString(frontServer)
			.path("/error")
			.build()
			.toUriString();
		response.sendRedirect(url);
	}
}
