package me.washcar.wcnc.global.utility;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import me.washcar.wcnc.domain.member.MemberRole;

@Component
public class AuthorizationHelper {

	public boolean isAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority authority : authorities) {
			if (authority.getAuthority().equals(MemberRole.ROLE_SUPERMAN.name()) ||
				authority.getAuthority().equals(MemberRole.ROLE_ADMIN.name())) {
				return true;
			}
		}
		return false;
	}

}
