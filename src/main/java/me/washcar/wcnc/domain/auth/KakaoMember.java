package me.washcar.wcnc.domain.auth;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import me.washcar.wcnc.domain.member.Member;
import me.washcar.wcnc.domain.member.MemberAuthenticationType;
import me.washcar.wcnc.domain.member.MemberRole;
import me.washcar.wcnc.domain.member.MemberStatus;
import me.washcar.wcnc.domain.member.OAuth;

@Getter
public class KakaoMember implements OAuth2Member {
	private final Map<String, Object> attributes;
	private final Map<String, Object> kakaoAccount;
	private final Map<String, Object> profile;
	private final String provider = "kakao";
	private final String providerId;
	private final String nickname;
	private final String telephone;
	private Member member;
	private OAuth oAuth;

	@SuppressWarnings("unchecked")
	public KakaoMember(Map<String, Object> attributes) {
		this.attributes = attributes;
		this.kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
		this.profile = (Map<String, Object>)kakaoAccount.get("profile");
		this.providerId = attributes.get("id").toString();
		this.nickname = profile.get("nickname").toString();
		this.telephone = kakaoAccount.get("phone_number").toString();
		
		this.member = Member.builder()
			.memberStatus(MemberStatus.ACTIVE)
			.memberRole(MemberRole.ROLE_USER)
			.memberAuthenticationType(MemberAuthenticationType.OAUTH)
			.nickname(this.nickname)
			.telephone(this.telephone)
			.build();
		this.oAuth = OAuth.builder()
			.member(this.member)
			.providerId(this.providerId)
			.provider(this.provider)
			.build();
	}

	@Override
	public void setOAuth(OAuth oAuth) {
		this.oAuth = oAuth;
		this.member = oAuth.getMember();
	}

	@Override
	public Map<String, Object> getAttributes() {
		return this.attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.member.getAuthorities();
	}

	@Override
	public String getName() {
		return this.member.getUuid();
	}
}
