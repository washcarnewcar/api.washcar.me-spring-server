package me.washcar.wcnc.domain.auth.adapter;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import me.washcar.wcnc.domain.member.MemberRole;
import me.washcar.wcnc.domain.member.MemberStatus;
import me.washcar.wcnc.domain.member.entity.Member;
import me.washcar.wcnc.global.error.BusinessError;
import me.washcar.wcnc.global.error.BusinessException;

/**
 *  카카오 로그인으로 인증을 시도할 때 사용하는 클래스
 */
public class KakaoAdapter implements OAuth2Adapter {
	private final Map<String, Object> attributes;
	private final String providerId;
	private String uuid;
	private MemberRole memberRole;
	private MemberStatus memberStatus;
	private final String nickname;
	private final String telephone;

	@SuppressWarnings("unchecked")
	public KakaoAdapter(Map<String, Object> attributes) {
		this.attributes = attributes;
		Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
		Map<String, Object> profile = (Map<String, Object>)kakaoAccount.get("profile");
		this.providerId = attributes.get("id").toString();
		this.nickname = profile.get("nickname").toString();
		this.telephone = this.parseInternationalTelephone(kakaoAccount.get("phone_number").toString());
	}

	@Override
	public void setMemberField(Member member) {
		this.uuid = member.getUuid();
		this.memberRole = member.getMemberRole();
		this.memberStatus = member.getMemberStatus();
	}

	@Override
	public String getProvider() {
		return "kakao";
	}

	@Override
	public String getProviderId() {
		return this.providerId;
	}

	@Override
	public String getNickname() {
		return this.nickname;
	}

	@Override
	public String getTelephone() {
		return this.telephone;
	}

	@Override
	public boolean isEnabled() {
		return this.memberStatus.equals(MemberStatus.ACTIVE);
	}

	@Override
	public String getUuid() {
		return this.uuid;
	}

	@Override
	public MemberRole getMemberRole() {
		return this.memberRole;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return this.attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(this.memberRole.name()));
	}

	@Override
	public String getName() {
		return this.uuid;
	}

	private String parseInternationalTelephone(String internationalTelephone) {
		if (!internationalTelephone.startsWith("+82")) {
			throw new BusinessException(BusinessError.NOT_KOREAN_TELEPHONE);
		}

		// +82 자르고 - 제거한 뒤 0을 맨 앞에 붙힘
		return "0".concat(internationalTelephone.substring(4).replaceAll("-", ""));
	}
}
