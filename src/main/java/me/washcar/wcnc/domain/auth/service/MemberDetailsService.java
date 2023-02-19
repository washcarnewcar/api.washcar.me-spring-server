package me.washcar.wcnc.domain.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.auth.adapter.LoginIdAdapter;
import me.washcar.wcnc.domain.member.dao.MemberRepository;
import me.washcar.wcnc.domain.member.entity.Member;
import me.washcar.wcnc.global.error.BusinessError;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
		Member member = memberRepository.findByLoginId(loginId)
			.orElseThrow(() -> new UsernameNotFoundException(BusinessError.MEMBER_NOT_FOUND.getMessage()));

		return new LoginIdAdapter(member);
	}
}
