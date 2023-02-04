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

	@Column(nullable = false)
	private String userId;    // 로그인에 필요한 로그인 아이디

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MemberStatus memberStatus;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MemberRole memberRole;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MemberAuthenticationType memberAuthenticationType;

	private String name;

	private String password;

	private String telephone;

	@Column(nullable = false)
	private Boolean deleted = Boolean.FALSE;

	@OneToMany(mappedBy = "owner")
	private List<Store> stores = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private List<Reservation> reservations = new ArrayList<>();

	@Builder
	private Member(String userId, String name, MemberStatus memberStatus, MemberRole memberRole,
		MemberAuthenticationType memberAuthenticationType, String password, String telephone, List<Store> stores,
		List<Reservation> reservations) {
		this.userId = userId;
		this.name = name;
		this.memberStatus = memberStatus;
		this.memberRole = memberRole;
		this.memberAuthenticationType = memberAuthenticationType;
		this.password = password;
		this.telephone = telephone;
		this.stores = stores;
		this.reservations = reservations;
	}

	public void changeStatus(MemberStatus status) {
		this.memberStatus = status;
	}

	////////////// Implements UserDetails //////////////
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(this.memberRole.name()));
	}

	@Override
	public String getUsername() {
		return this.userId;
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
