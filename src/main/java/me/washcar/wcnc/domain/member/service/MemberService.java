package me.washcar.wcnc.domain.member.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.member.MemberStatus;
import me.washcar.wcnc.domain.member.dao.MemberRepository;
import me.washcar.wcnc.domain.member.dto.response.MemberDto;
import me.washcar.wcnc.domain.member.entity.Member;
import me.washcar.wcnc.global.error.BusinessError;
import me.washcar.wcnc.global.error.BusinessException;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final ModelMapper modelMapper;

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

}
