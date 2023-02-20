package me.washcar.wcnc.global.utility;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.member.MemberRole;
import me.washcar.wcnc.global.error.ApplicationError;
import me.washcar.wcnc.global.error.ApplicationException;

@Component
@RequiredArgsConstructor
public class AuthorizationHelper {

	public boolean isManager() {
		MemberRole role = getMyRole();
		return role.equals(MemberRole.ROLE_SUPERMAN) || role.equals(MemberRole.ROLE_ADMIN);
	}

	public boolean isSuperman() {
		MemberRole role = getMyRole();
		return role.equals(MemberRole.ROLE_SUPERMAN);
	}

	public MemberRole getMyRole() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		if (authorities.isEmpty()) {
			throw new ApplicationException(ApplicationError.ROLE_NOT_FOUND);
		} else if (authorities.size() > 1) {
			throw new ApplicationException(ApplicationError.MULTIPLE_ROLE_FOUND);
		} else {
			String myRole = authorities.iterator().next().getAuthority();
			if (myRole.equals(MemberRole.ROLE_USER.name())) {
				return MemberRole.ROLE_USER;
			}
			if (myRole.equals(MemberRole.ROLE_OWNER.name())) {
				return MemberRole.ROLE_OWNER;
			}
			if (myRole.equals(MemberRole.ROLE_ADMIN.name())) {
				return MemberRole.ROLE_ADMIN;
			}
			if (myRole.equals(MemberRole.ROLE_SUPERMAN.name())) {
				return MemberRole.ROLE_SUPERMAN;
			}
			throw new ApplicationException(ApplicationError.ROLE_TYPE_ERROR);
		}
	}

}
