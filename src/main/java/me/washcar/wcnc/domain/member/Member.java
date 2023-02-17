package me.washcar.wcnc.domain.member;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
import me.washcar.wcnc.domain.reservation.Reservation;
import me.washcar.wcnc.domain.store.Store;
import me.washcar.wcnc.global.entity.UuidEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE member SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Table(indexes = @Index(name = "uuid_member_index", columnList = "uuid"))
public class Member extends UuidEntity implements UserDetails {

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
	private Boolean deleted = Boolean.FALSE;

	@OneToMany(mappedBy = "owner")
	private List<Store> stores = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private List<Reservation> reservations = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private List<OAuth> oAuths = new ArrayList<>();

	public void changeStatus(MemberStatus status) {
		this.memberStatus = status;
	}

	@Builder
	@SuppressWarnings("unused")
	private Member(String loginId, MemberStatus memberStatus, MemberRole memberRole,
		MemberAuthenticationType memberAuthenticationType, String nickname, String loginPassword, String telephone) {
		this.loginId = loginId;
		this.memberStatus = memberStatus;
		this.memberRole = memberRole;
		this.memberAuthenticationType = memberAuthenticationType;
		this.nickname = nickname;
		this.loginPassword = loginPassword;
		this.telephone = telephone;
	}

	////////////// Implements UserDetails //////////////
	@Override
	public String getUsername() {
		return this.loginId;
	}

	@Override
	public String getPassword() {
		return this.loginPassword;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(this.memberRole.name()));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.memberStatus.equals(MemberStatus.ACTIVE);
	}
}
