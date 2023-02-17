package me.washcar.wcnc.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.washcar.wcnc.domain.member.entity.Member;
import me.washcar.wcnc.global.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth extends BaseEntity {

	// member 안에 있는 getAuthorization을 바로 사용하기 위해 의도적으로 EAGER로 설정했습니다.
	// LAZY로 설정하면 OAuth 인증 과정에서 오류 납니다.
	@ManyToOne(fetch = FetchType.EAGER)
	private Member member;

	@Column(nullable = false, unique = true)
	private String providerId;

	@Column(nullable = false)
	private String provider;

	@Builder
	@SuppressWarnings("unused")
	private OAuth(Member member, String providerId, String provider) {
		this.member = member;
		this.providerId = providerId;
		this.provider = provider;
	}
}
