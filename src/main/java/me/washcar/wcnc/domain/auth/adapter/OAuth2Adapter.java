package me.washcar.wcnc.domain.auth.adapter;

import org.springframework.security.oauth2.core.user.OAuth2User;

import me.washcar.wcnc.domain.member.entity.Member;

public interface OAuth2Adapter extends OAuth2User, AuthenticationAdapter {
	/** 로그인 된 유저가 가입되어있는지 확인하기 위해 필요 */
	String getProvider();

	/** 로그인 된 유저가 가입되어있는지 확인하기 위해 필요 */
	String getProviderId();

	/** 회원가입 과정에 member를 생성하기 위해 필요 */
	String getNickname();

	/** 회원가입 과정에 member를 생성하기 위해 필요 */
	String getTelephone();

	boolean isEnabled();

	/** member 객체로 uuid, memberRole, memberStatus 등의 정보를 가져오는 메서드 */
	void setMemberField(Member member);
}
