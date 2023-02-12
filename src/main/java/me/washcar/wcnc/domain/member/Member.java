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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.washcar.wcnc.domain.reservation.Reservation;
import me.washcar.wcnc.domain.store.Store;
import me.washcar.wcnc.global.entity.UuidEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SQLDelete(sql = "UPDATE member SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Table(indexes = @Index(name = "uuid_member_index", columnList = "uuid"))
public class Member extends UuidEntity implements UserDetails {

	@Column(unique = true)
	private String memberId;    // 로그인에 필요한 로그인 아이디

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

	private String password;

	@Column(nullable = false, unique = true)
	private String telephone;

	@Builder.Default
	@Column(nullable = false)
	private Boolean deleted = Boolean.FALSE;

	@Builder.Default
	@OneToMany(mappedBy = "owner")
	private List<Store> stores = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "member")
	private List<Reservation> reservations = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "member")
	private List<OAuth> oAuths = new ArrayList<>();

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
		return this.memberId;
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

	////////////// Implements OAuth2User //////////////
	//
	// // Member 자체만으로는 attibutes를 받아올 수 없다.
	// @Override
	// public abstract Map<String, Object> getAttributes();
	//
	// @Override
	// public String getName() {
	// 	return this.getUuid();
	// }
	//
	// ////////////// Required for OAuth //////////////
	// public abstract String getProvider();
	//
	// public abstract String getProviderId();
	//
	// public abstract OAuth createOAuth();
}
