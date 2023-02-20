package me.washcar.wcnc.domain.auth.adapter;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import me.washcar.wcnc.domain.member.MemberRole;
import me.washcar.wcnc.domain.member.MemberStatus;
import me.washcar.wcnc.domain.member.entity.Member;

/**
 * 아이디와 패스워드로 인증을 시도할 때 사용하는 클래스
 */
public class LoginIdAdapterImpl implements UserDetails, AuthenticationAdapter {
	private final String uuid;
	private final String loginId;
	private final String loginPassword;
	private final MemberStatus memberStatus;
	private final MemberRole memberRole;

	public LoginIdAdapterImpl(Member member) {
		this.uuid = member.getUuid();
		this.loginId = member.getLoginId();
		this.loginPassword = member.getLoginPassword();
		this.memberStatus = member.getMemberStatus();
		this.memberRole = member.getMemberRole();
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
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(this.memberRole.name()));
	}

	@Override
	public String getPassword() {
		return this.loginPassword;
	}

	@Override
	public String getUsername() {
		return this.loginId;
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
