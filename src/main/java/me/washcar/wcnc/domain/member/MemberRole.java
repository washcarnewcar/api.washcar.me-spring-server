package me.washcar.wcnc.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MemberRole {
	ROLE_USER, ROLE_OWNER, ROLE_ADMIN, ROLE_SUPERMAN
}
