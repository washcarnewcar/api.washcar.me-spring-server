package me.washcar.wcnc.global.configuration.security;

import static me.washcar.wcnc.domain.member.MemberRole.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.auth.service.OAuth2MemberService;
import me.washcar.wcnc.global.filter.JwtAuthorizationFilter;
import me.washcar.wcnc.global.handler.JwtAccessDeniedHandler;
import me.washcar.wcnc.global.handler.JwtAuthenticationEntryPoint;
import me.washcar.wcnc.global.handler.OAuth2FailureHandler;
import me.washcar.wcnc.global.handler.OAuth2SuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final JwtAuthorizationFilter jwtAuthorizationFilter;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final OAuth2MemberService oAuth2MemberService;
	private final OAuth2SuccessHandler oAuth2SuccessHandler;
	private final OAuth2FailureHandler oAuth2FailureHandler;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf().disable()
			.cors();

		http.formLogin().disable().logout().disable();

		http.oauth2Login()
			.successHandler(oAuth2SuccessHandler).failureHandler(oAuth2FailureHandler)
			.authorizationEndpoint().baseUri("/v2/oauth2/login/**")
			.and()
			.redirectionEndpoint().baseUri("/v2/oauth2/code/**")
			.and()
			.userInfoEndpoint()
			.userService(oAuth2MemberService);

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
		http.exceptionHandling()
			.accessDeniedHandler(jwtAccessDeniedHandler)
			.authenticationEntryPoint(jwtAuthenticationEntryPoint);

		http.authorizeHttpRequests()

			// AuthController
			.requestMatchers("/v2/login", "/v2/logout", "/v2/members/login-id/*", "/v2/members/telephone/*",
				"/v2/members/me", "/v2/signup", "/v2/pin", "/v2/telephone-login")
			.permitAll()

			// OAuth2
			.requestMatchers("/v2/oauth2/**")
			.permitAll()

			// MemberController
			.requestMatchers("/v2/member")
			.hasAnyAuthority(ROLE_ADMIN.name(), ROLE_SUPERMAN.name())

			.requestMatchers(HttpMethod.GET, "/v2/member/*")
			.hasAnyAuthority(ROLE_ADMIN.name(), ROLE_SUPERMAN.name())

			.requestMatchers(HttpMethod.PUT, "/v2/member/*")
			.permitAll()

			.requestMatchers(HttpMethod.DELETE, "/v2/member/*")
			.hasAnyAuthority(ROLE_SUPERMAN.name())

			.requestMatchers(HttpMethod.PATCH, "/v2/member/*")
			.hasAnyAuthority(ROLE_ADMIN.name(), ROLE_SUPERMAN.name())

			// StoreController
			.requestMatchers(HttpMethod.POST, "/v2/store")
			.permitAll()

			.requestMatchers(HttpMethod.GET, "/v2/store")
			.hasAnyAuthority(ROLE_ADMIN.name(), ROLE_SUPERMAN.name())

			.requestMatchers(HttpMethod.GET, "/v2/store/*")
			.permitAll()

			.requestMatchers(HttpMethod.PUT, "/v2/store/*")
			.hasAnyAuthority(ROLE_OWNER.name(), ROLE_ADMIN.name(), ROLE_SUPERMAN.name())

			.requestMatchers(HttpMethod.DELETE, "/v2/store/*")
			.hasAnyAuthority(ROLE_SUPERMAN.name())

			.requestMatchers(HttpMethod.PATCH, "/v2/store/*")
			.hasAnyAuthority(ROLE_ADMIN.name(), ROLE_SUPERMAN.name())

			// StoreImageController
			.requestMatchers(HttpMethod.POST, "/v2/store/*/image")
			.permitAll()

			.requestMatchers(HttpMethod.GET, "/v2/store/*/image")
			.permitAll()

			.requestMatchers(HttpMethod.GET, "/v2/image/*")
			.permitAll()

			.requestMatchers(HttpMethod.DELETE, "/v2/image/*")
			.permitAll()

			// SearchSlugController
			.requestMatchers(HttpMethod.GET, "/v2/search/slugs/me")
			.permitAll()

			// Any Request
			.anyRequest()
			.permitAll();

		http.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
