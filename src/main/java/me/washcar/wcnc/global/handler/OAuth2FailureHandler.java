package me.washcar.wcnc.global.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OAuth2FailureHandler implements AuthenticationFailureHandler {

	@Value("${front-server.domain}")
	private String frontServer;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException {

		log.error(exception.getMessage(), exception);

		String url = UriComponentsBuilder.fromUriString(frontServer)
			.path("/error")
			.build()
			.toUriString();
		response.sendRedirect(url);
	}
}
