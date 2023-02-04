package me.washcar.wcnc.domain.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.auth.dto.request.LoginDto;
import me.washcar.wcnc.domain.member.Member;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthenticationManager authenticationManager;

	/**
	 *	userId와 password를 입력받아 인증 후 해당 id의 Member객체를 반환한다.
	 */
	public Member authenticate(LoginDto loginDto) {
		String userId = loginDto.getUserId();
		String password = loginDto.getPassword();
		// telephone과 password로 인증을 수행한다. => 성공하면 계속 진행, 실패하면 403 응답
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(userId, password));
		return (Member)authentication.getPrincipal();
	}
}
