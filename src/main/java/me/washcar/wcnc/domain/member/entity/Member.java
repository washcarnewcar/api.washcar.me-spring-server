package me.washcar.wcnc.domain.member.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.washcar.wcnc.domain.member.MemberAuthenticationType;
import me.washcar.wcnc.domain.member.MemberRole;
import me.washcar.wcnc.domain.member.MemberStatus;
import me.washcar.wcnc.domain.reservation.entity.Reservation;
import me.washcar.wcnc.domain.store.entity.Store;
import me.washcar.wcnc.global.entity.UuidEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE member SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Table(indexes = @Index(name = "uuid_member_index", columnList = "uuid"))
public class Member extends UuidEntity {

	@Column(unique = true)
	private String loginId;    // 로그인에 필요한 로그인 아이디

	private String loginPassword;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MemberStatus memberStatus;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MemberRole memberRole;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MemberAuthenticationType memberAuthenticationType;

	private String nickname;

	@Column(nullable = false, unique = true)
	private String telephone;

	@Column(nullable = false)
	private boolean deleted;

	@OneToMany(mappedBy = "owner")
	private final List<Store> stores = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private final List<Reservation> reservations = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private final List<OAuth> oAuths = new ArrayList<>();

	public void changeStatus(MemberStatus status) {
		this.memberStatus = status;
	}

	public void changeNickname(String nickname) {
		this.nickname = nickname;
	}

	@Builder
	@SuppressWarnings("unused")
	private Member(String loginId, MemberStatus memberStatus, MemberRole memberRole,
		MemberAuthenticationType memberAuthenticationType, String nickname, String loginPassword, String telephone) {
		this.deleted = false;
		this.loginId = loginId;
		this.memberStatus = memberStatus;
		this.memberRole = memberRole;
		this.memberAuthenticationType = memberAuthenticationType;
		this.nickname = nickname;
		this.loginPassword = loginPassword;
		this.telephone = telephone;
	}

	public void promote(MemberRole memberRole) {
		if (this.memberRole.ordinal() < memberRole.ordinal()) {
			this.memberRole = memberRole;
		}
	}

	public void demote(MemberRole memberRole) {
		if (this.memberRole.ordinal() > memberRole.ordinal()) {
			this.memberRole = memberRole;
		}
	}

}

