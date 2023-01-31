package me.washcar.wcnc.global.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	private static final String[] WHITELIST = {
		"/v2/**" //TODO 배포 전 화이트리스트 재설정 필요
	};

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable();

		http.authorizeHttpRequests()
			.requestMatchers(WHITELIST)
			.permitAll()
			.anyRequest()
			.authenticated();

		http.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		return http.build();
	}

}
