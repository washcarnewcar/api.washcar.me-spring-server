package me.washcar.wcnc.domain.store.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.washcar.wcnc.domain.member.MemberRole;
import me.washcar.wcnc.domain.member.dao.MemberRepository;
import me.washcar.wcnc.domain.member.entity.Member;
import me.washcar.wcnc.domain.store.StoreStatus;
import me.washcar.wcnc.domain.store.dao.StoreRepository;
import me.washcar.wcnc.domain.store.dto.request.StoreRequestDto;
import me.washcar.wcnc.domain.store.dto.response.StoreDto;
import me.washcar.wcnc.domain.store.entity.Store;
import me.washcar.wcnc.global.error.BusinessError;
import me.washcar.wcnc.global.error.BusinessException;
import me.washcar.wcnc.global.utility.AuthorizationHelper;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreService {

	private final StoreRepository storeRepository;

	private final MemberRepository memberRepository;

	private final AuthorizationHelper authorizationHelper;

	private final ModelMapper modelMapper;

	private void checkNewSlugSafety(StoreRequestDto storeRequestDto) {
		if (storeRepository.existsBySlug(storeRequestDto.getSlug())) {
			throw new BusinessException(BusinessError.STORE_ALREADY_EXIST);
		}
	}

	private void checkChangeSlugSafety(String slug, StoreRequestDto storeRequestDto) {
		if (!storeRequestDto.getSlug().equals(slug)) {
			checkNewSlugSafety(storeRequestDto);
		}
	}

	private void refreshStoreStatus(Store store) {
		if (store.getStatus().equals(StoreStatus.REJECTED)) {
			store.updateStatus(StoreStatus.PENDING);
		}
	}

	private Store getStoreBySlugUnconditional(String slug) {
		return storeRepository.findBySlug(slug)
			.orElseThrow(() -> new BusinessException(BusinessError.STORE_NOT_FOUND));
	}

	public boolean amIOwner(Store store) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String uuid = authentication.getPrincipal().toString();
		return uuid.equals(store.getOwner().getUuid());
	}

	public boolean isOwner(String uuid, Store store) {
		return uuid.equals(store.getOwner().getUuid());
	}

	public void postStore(StoreRequestDto storeRequestDto, String ownerUuid) {
		checkNewSlugSafety(storeRequestDto);
		Store store = Store.builder()
			.slug(storeRequestDto.getSlug())
			.location(storeRequestDto.getLocation())
			.name(storeRequestDto.getName())
			.tel(storeRequestDto.getTel())
			.description(storeRequestDto.getDescription())
			.previewImage(storeRequestDto.getPreviewImage())
			.build();
		Member owner = memberRepository.findByUuid(ownerUuid)
			.orElseThrow(() -> new BusinessException(BusinessError.MEMBER_NOT_FOUND));
		store.assignOwner(owner);
		storeRepository.save(store);
	}

	@Transactional(readOnly = true)
	public Page<StoreDto> getStoreList(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Store> storePage = storeRepository.findAll(pageable);
		return storePage.map(s -> modelMapper.map(s, StoreDto.class));
	}

	@Transactional(readOnly = true)
	public StoreDto getStoreBySlug(String slug) {
		Store store = getStoreBySlugUnconditional(slug);
		boolean isManager = authorizationHelper.isManager();
		boolean isOwner = amIOwner(store);
		boolean isOperate = store.getStatus().equals(StoreStatus.RUNNING);
		if (isManager || isOwner || isOperate) {
			return modelMapper.map(store, StoreDto.class);
		} else {
			throw new BusinessException(BusinessError.STORE_NOT_FOUND);
		}
	}

	public StoreDto putStoreBySlug(String slug, StoreRequestDto requestDto) {
		Store store = getStoreBySlugUnconditional(slug);
		boolean isManager = authorizationHelper.isManager();
		boolean isOwner = amIOwner(store);
		if (isManager || isOwner) {
			checkChangeSlugSafety(slug, requestDto);
			store.updateStore(requestDto.getSlug(), requestDto.getLocation(), requestDto.getName(),
				requestDto.getTel(), requestDto.getDescription(), requestDto.getPreviewImage());
			refreshStoreStatus(store);
			return modelMapper.map(store, StoreDto.class);
		} else {
			throw new BusinessException(BusinessError.FORBIDDEN_STORE_CHANGE);
		}
	}

	public void deleteStoreBySlug(String slug) {
		Store store = getStoreBySlugUnconditional(slug);
		storeRepository.delete(store);
	}

	public void changeStoreStatusBySlug(String slug, StoreStatus storeStatus) {
		Store store = getStoreBySlugUnconditional(slug);
		store.updateStatus(storeStatus);
		if (storeStatus.equals(StoreStatus.RUNNING)) {
			store.getOwner().promote(MemberRole.ROLE_OWNER);
		}
	}

}
