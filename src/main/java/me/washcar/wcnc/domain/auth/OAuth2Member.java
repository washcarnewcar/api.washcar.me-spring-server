package me.washcar.wcnc.domain.auth;

import org.springframework.security.oauth2.core.user.OAuth2User;

import me.washcar.wcnc.domain.member.OAuth;
import me.washcar.wcnc.domain.member.entity.Member;

public interface OAuth2Member extends OAuth2User {
	OAuth getOAuth();

	Member getMember();

	String getProviderId();

	String getProvider();

	void setOAuth(OAuth oAuth);
}
