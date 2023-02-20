package me.washcar.wcnc.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.washcar.wcnc.global.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	@Column(nullable = false)
	private String provider;

	@Column(nullable = false, unique = true)
	private String providerId;

	@Builder
	@SuppressWarnings("unused")
	private OAuth(Member member, String providerId, String provider) {
		this.member = member;
		this.providerId = providerId;
		this.provider = provider;
	}
}
