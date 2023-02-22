package me.washcar.wcnc.domain.member.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.member.MemberStatus;
import me.washcar.wcnc.domain.member.dao.MemberRepository;
import me.washcar.wcnc.domain.member.dto.request.MemberPutRequestDto;
import me.washcar.wcnc.domain.member.dto.response.MemberDto;
import me.washcar.wcnc.domain.member.entity.Member;
import me.washcar.wcnc.global.error.BusinessError;
import me.washcar.wcnc.global.error.BusinessException;
import me.washcar.wcnc.global.utility.AuthorizationHelper;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

	private final MemberRepository memberRepository;
	private final ModelMapper modelMapper;

	private final AuthorizationHelper authorizationHelper;

	@Transactional(readOnly = true)
	public Page<MemberDto> getMemberList(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Member> memberPage = memberRepository.findAll(pageable);
		return memberPage.map(m -> modelMapper.map(m, MemberDto.class));
	}

	@Transactional(readOnly = true)
	public MemberDto getMemberByUuid(String uuid) {
		Member member = memberRepository.findByUuid(uuid)
			.orElseThrow(() -> new BusinessException(BusinessError.MEMBER_NOT_FOUND));
		return modelMapper.map(member, MemberDto.class);
	}

	public void changeNicknameByUuid(String uuid, MemberPutRequestDto memberPutRequestDto) {
		boolean isMe = authorizationHelper.isMe(uuid);
		boolean isManager = authorizationHelper.isManager();
		if (isMe || isManager) {
			Member member = memberRepository.findByUuid(uuid)
				.orElseThrow(() -> new BusinessException(BusinessError.MEMBER_NOT_FOUND));
			member.changeNickname(memberPutRequestDto.getNickname());
		} else {
			throw new BusinessException(BusinessError.FORBIDDEN_MEMBER_CHANGE);
		}
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
