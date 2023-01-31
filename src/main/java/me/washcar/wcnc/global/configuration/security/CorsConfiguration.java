package me.washcar.wcnc.global.configuration.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsConfiguration implements WebMvcConfigurer {
	@Value("${cors.origin}")
	private String origin;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins(origin)
			.allowedMethods("*")
			.allowCredentials(true);
	}
}
