package me.washcar.wcnc.domain.search.slug.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.member.dao.MemberRepository;
import me.washcar.wcnc.domain.member.entity.Member;
import me.washcar.wcnc.domain.search.slug.dto.response.SlugResponseDto;
import me.washcar.wcnc.domain.search.slug.dto.response.SlugsDto;
import me.washcar.wcnc.domain.store.entity.Store;
import me.washcar.wcnc.global.error.BusinessError;
import me.washcar.wcnc.global.error.BusinessException;

@Service
@RequiredArgsConstructor
public class SearchSlugService {

	private final MemberRepository memberRepository;

	private final ModelMapper modelMapper;

	public SlugsDto getSlugsByOwnerUuid(String uuid) {
		Member member = memberRepository.findByUuid(uuid)
			.orElseThrow(() -> new BusinessException(BusinessError.MEMBER_NOT_FOUND));
		List<Store> stores = member.getStores();
		List<SlugResponseDto> slugResponseDtos = stores.stream()
			.map(s -> modelMapper.map(s, SlugResponseDto.class))
			.toList();
		return new SlugsDto(slugResponseDtos);
	}

}
