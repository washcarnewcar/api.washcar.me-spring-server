package me.washcar.wcnc.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MemberRole {
	ROLE_USER("USER"), ROLE_OWNER("OWNER"), ROLE_ADMIN("ADMIN"), ROLE_SUPERMAN("SUPERMAN");

	private final String name;

}