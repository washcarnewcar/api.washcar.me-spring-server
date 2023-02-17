package me.washcar.wcnc.domain.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.member.dao.MemberRepository;
import me.washcar.wcnc.global.error.BusinessError;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
		return memberRepository.findByLoginId(memberId)
			.orElseThrow(() -> new UsernameNotFoundException(BusinessError.MEMBER_NOT_FOUND.getMessage()));
	}
}
