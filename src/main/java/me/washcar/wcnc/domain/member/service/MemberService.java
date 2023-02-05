package me.washcar.wcnc.domain.member.service;

import static me.washcar.wcnc.domain.member.MemberRole.*;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.member.Member;
import me.washcar.wcnc.domain.member.MemberAuthenticationType;
import me.washcar.wcnc.domain.member.MemberStatus;
import me.washcar.wcnc.domain.member.dao.MemberRepository;
import me.washcar.wcnc.domain.member.dto.response.MemberDto;
import me.washcar.wcnc.global.error.BusinessError;
import me.washcar.wcnc.global.error.BusinessException;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

	private final MemberRepository memberRepository;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;

	public void postMember() {
		//TODO 추후 회원가입 로직으로 변경 필요

		//멤버 더미를 DB에 추가하는 로직
		Member randomMember = Member.builder()
			.userId("admin")
			.name("Gilteun")
			.memberRole(ROLE_USER)
			.memberStatus(MemberStatus.ACTIVE)
			.memberAuthenticationType(MemberAuthenticationType.PASSWORD)
			.password(passwordEncoder.encode("password"))
			.telephone("01022223333")
			.stores(new ArrayList<>())
			.reservations(new ArrayList<>())
			.build();

		memberRepository.save(randomMember);
	}

	public Page<MemberDto> getMemberList(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Member> memberPage = memberRepository.findAll(pageable);
		return memberPage.map(m -> modelMapper.map(m, MemberDto.class));
	}

	public MemberDto getMemberByUuid(String uuid) {
		Member member = memberRepository.findByUuid(uuid)
			.orElseThrow(() -> new BusinessException(BusinessError.MEMBER_NOT_FOUND));
		return modelMapper.map(member, MemberDto.class);
	}

	public MemberDto putMemberByUuid(String uuid) {
		//TODO 미구현: 해당 유저 정보 수정
		Member member = memberRepository.findByUuid(uuid)
			.orElseThrow(() -> new BusinessException(BusinessError.MEMBER_NOT_FOUND));

		return null;
	}

	public void deleteMemberByUuid(String uuid) {
		Member member = memberRepository.findByUuid(uuid)
			.orElseThrow(() -> new BusinessException(BusinessError.MEMBER_NOT_FOUND));
		memberRepository.delete(member);
	}

	public void changeMemberStatusByUuid(String uuid, MemberStatus memberStatus) {
		Member member = memberRepository.findByUuid(uuid)
			.orElseThrow(() -> new BusinessException(BusinessError.MEMBER_NOT_FOUND));
		member.changeStatus(memberStatus);
		memberRepository.save(member);
	}

	public MemberDto getMemberByJwt() {
		//TODO 미구현: 내 쿠키(토큰) 기반 정보 조회
		return null;
	}

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		return memberRepository.findByUserId(userId)
			.orElseThrow(() -> new UsernameNotFoundException(BusinessError.MEMBER_NOT_FOUND.getMessage()));
	}
}
