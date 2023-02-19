package me.washcar.wcnc.domain.auth.adapter;

import me.washcar.wcnc.domain.member.MemberRole;

/**
 * JWT를 만들기 위해 필요한 uuid와 memberRole을 반환하는 인터페이스를 정의
 */
public interface AuthenticationAdapter {

	String getUuid();

	MemberRole getMemberRole();

}
